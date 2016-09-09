package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.api.RecipesService;
import rs.ac.uns.ftn.chiefcook.api.SpoonacularApi;
import rs.ac.uns.ftn.chiefcook.data.ChiefCookContract;
import rs.ac.uns.ftn.chiefcook.model.ExtendedIngredient;
import rs.ac.uns.ftn.chiefcook.model.Recipe;
import rs.ac.uns.ftn.chiefcook.model.RecipeInstructionsItem;
import rs.ac.uns.ftn.chiefcook.model.Step;
import rs.ac.uns.ftn.chiefcook.ui.adapters.RecipeStepAdapter;
import rs.ac.uns.ftn.chiefcook.ui.fragments.IngredientsFragment;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String LOG_TAG = RecipeDetailsActivity.class.getSimpleName();
    public static final String RECIPE_ID_KEY = "recipe_key";

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) protected CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.ivRecipeImage) protected ImageView tvRecipeImage;
    @BindView(R.id.rvRecipeSteps) protected RecyclerView rvRecipeSteps;

    private ShareActionProvider shareActionProvider;

    private RecipesService recipesService;
    private RecipeStepAdapter recipeStepAdapter;

    private Recipe recipe;
    private List<Step> recipeSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        setupToolbar();

        Intent intent = getIntent();
        final int recipeId = intent.getIntExtra(RECIPE_ID_KEY, 0);

        recipesService = SpoonacularApi.getRecipesService();
        recipeSteps = new ArrayList<>();
        recipeStepAdapter = new RecipeStepAdapter(this, recipeSteps);

        rvRecipeSteps.setAdapter(recipeStepAdapter);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvRecipeSteps.setLayoutManager(linearLayoutManager);

        loadRecipe(recipeId);
        loadRecipeInstructions(recipeId);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("");
    }

    private void loadRecipe(int recipeId) {
        Call<Recipe> recipeInfoCall = recipesService.getRecipeInfo(recipeId);

        recipeInfoCall.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                recipe = response.body();

                Picasso.with(RecipeDetailsActivity.this)
                        .load(recipe.getImage())
                        .into(tvRecipeImage);

                collapsingToolbar.setTitle(recipe.getTitle());

                invalidateOptionsMenu(); // necessary to update share intent data
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Toast failureToast = Toast.makeText(RecipeDetailsActivity.this,
                        t.getLocalizedMessage(),
                        Toast.LENGTH_SHORT);

                failureToast.show();
            }
        });
    }

    private void loadRecipeInstructions(final int recipeId) {
        Call<List<RecipeInstructionsItem>> call = recipesService.getAnalyzedRecipeInstructions(recipeId);
        call.enqueue(new Callback<List<RecipeInstructionsItem>>() {
            @Override
            public void onResponse(Call<List<RecipeInstructionsItem>> call, Response<List<RecipeInstructionsItem>> response) {
                List<RecipeInstructionsItem> recipeInstructionsItems = response.body();
                if (recipeInstructionsItems.isEmpty()) {
                    Log.d(LOG_TAG, String.format("Empty instructions list for recipe id= %d", recipeId));
                } else {
                    RecipeInstructionsItem recipeInstructionsItem = recipeInstructionsItems.get(0);
                    int currentItemCount = recipeStepAdapter.getItemCount();
                    recipeSteps.addAll(recipeInstructionsItem.getSteps());

                    recipeStepAdapter.notifyItemRangeInserted(currentItemCount,
                            recipeSteps.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<List<RecipeInstructionsItem>> call, Throwable t) {
                Toast toast = Toast.makeText(RecipeDetailsActivity.this,
                        t.getLocalizedMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe_details, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(shareItem);

        Intent shareIntent = createShareIntent();
        shareActionProvider.setShareIntent(shareIntent);

        return true;
    }

    @NonNull
    private Intent createShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        // recipe is loaded from an API
        if (recipe != null) {
            String subject = recipe.getTitle();
            String text = recipe.getSpoonacularSourceUrl();

            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
        }

        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_ingredients) {
            if (recipe != null) {
                showIngredientsDialog();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showIngredientsDialog() {
        if (recipe != null && recipe.getExtendedIngredients().size() > 0) {
            IngredientsFragment ingredientsDialogFragment = IngredientsFragment.newInstance(
                    (ArrayList<ExtendedIngredient>) recipe.getExtendedIngredients());

            ingredientsDialogFragment.show(getSupportFragmentManager(), null);
        }
    }

    private void saveRecipe() {
        ContentValues recipeValues = new ContentValues();
        recipeValues.put(ChiefCookContract.RecipeEntry.COLUMN_TITLE, recipe.getTitle());
        recipeValues.put(ChiefCookContract.RecipeEntry.COLUMN_READY_IN_MINUTES, recipe.getReadyInMinutes());
        recipeValues.put(ChiefCookContract.RecipeEntry.COLUMN_IMAGE_URL, recipe.getImage());
        recipeValues.put(ChiefCookContract.RecipeEntry.COLUMN_API_ID, recipe.getId());

        getContentResolver().insert(ChiefCookContract.RecipeEntry.CONTENT_URI, recipeValues);
    }

    private void saveIngredientsForRecipe() {
        Vector<ContentValues> cVVector = new Vector<>();
        List<ExtendedIngredient> extendedIngredients = recipe.getExtendedIngredients();

        for (ExtendedIngredient ingredient :
                extendedIngredients) {
            ContentValues ingredientValues = new ContentValues();

            ingredientValues.put(ChiefCookContract.IngredientEntry.COLUMN_NAME, ingredient.getName());
            ingredientValues.put(ChiefCookContract.IngredientEntry.COLUMN_AMOUNT, ingredient.getAmount());
            ingredientValues.put(ChiefCookContract.IngredientEntry.COLUMN_IMAGE_URL, ingredient.getImage());
            ingredientValues.put(ChiefCookContract.IngredientEntry.COLUMN_API_ID, ingredient.getId());
            ingredientValues.put(ChiefCookContract.IngredientEntry.COLUMN_ORIGINAL_STRING, ingredient.getOriginalString());
            ingredientValues.put(ChiefCookContract.IngredientEntry.COLUMN_UNIT, ingredient.getUnit());
            ingredientValues.put(ChiefCookContract.IngredientEntry.COLUMN_UNIT_SHORT, ingredient.getUnitShort());
            ingredientValues.put(ChiefCookContract.IngredientEntry.COLUMN_RECIPE_ID, recipe.getId());

            cVVector.add(ingredientValues);
        }

        int insertedCount = 0;

        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);

            insertedCount = getContentResolver().bulkInsert(ChiefCookContract.IngredientEntry.CONTENT_URI, cvArray);
        }

        Log.d(LOG_TAG, insertedCount + " ingredients inserted.");
    }
}
