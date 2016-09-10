package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import rs.ac.uns.ftn.chiefcook.ui.fragments.IngredientsDialogFragment;

public class RecipeDetailsActivity extends AppCompatActivity
        implements IngredientsDialogFragment.Listener {

    public static final String LOG_TAG = RecipeDetailsActivity.class.getSimpleName();
    public static final String RECIPE_ID_KEY = "recipe_key";

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) protected CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.ivRecipeImage) protected ImageView tvRecipeImage;
    @BindView(R.id.rvRecipeSteps) protected RecyclerView rvRecipeSteps;
    @BindView(R.id.fabFavorite) protected FloatingActionButton fabFavorite;

    private ShareActionProvider shareActionProvider;

    private RecipesService recipesService;
    private RecipeStepAdapter recipeStepAdapter;

    private Recipe recipe;
    private List<Step> recipeSteps;
    private boolean favoriteRecipe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final int recipeId = intent.getIntExtra(RECIPE_ID_KEY, 0);

        initToolbar();

        initRecipeStepsView();

        favoriteRecipe = isFavoriteRecipe(recipeId);

        setFavoriteIcon(favoriteRecipe);
        setFavoriteListener(recipeId);

        loadRecipeData(recipeId);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("");
    }

    private void initRecipeStepsView() {
        recipeSteps = new ArrayList<>();
        recipeStepAdapter = new RecipeStepAdapter(this, recipeSteps);

        rvRecipeSteps.setAdapter(recipeStepAdapter);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvRecipeSteps.setLayoutManager(linearLayoutManager);
    }

    private void setFavoriteIcon(boolean favorite) {
        if (favorite) {
            fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.ic_favorite));
        } else {
            fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
        }
    }

    private void setFavoriteListener(final int recipeId) {
        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoriteRecipe) {
                    deleteRecipe(recipeId);
                    favoriteRecipe = false;
                    setFavoriteIcon(false);
                } else {
                    saveRecipe();
                    favoriteRecipe = true;
                    setFavoriteIcon(true);
                }
            }
        });
    }

    private void loadRecipeData(int recipeId) {
        recipesService = SpoonacularApi.getRecipesService();
        loadRecipeInfo(recipeId);
        loadRecipeInstructions(recipeId);
    }

    private void loadRecipeInfo(int recipeId) {
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
            IngredientsDialogFragment ingredientsDialogFragment = IngredientsDialogFragment.newInstance(
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

        Uri insertedUri = getContentResolver().insert(ChiefCookContract.RecipeEntry.CONTENT_URI, recipeValues);

        Log.d(LOG_TAG, String.format("Inserted recipe URI: %s", insertedUri));

        Snackbar.make(
                rvRecipeSteps,
                R.string.snackbar_text_favorite_recipe,
                Snackbar.LENGTH_LONG
        ).setAction(R.string.snackbar_action_view_favorites, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(createFavoritesIntent());
            }
        }).show();
    }

    private void deleteRecipe(int recipeId) {
        boolean deleted;
        String whereClause = ChiefCookContract.RecipeEntry.COLUMN_API_ID + " = ? ";
        String[] selectionArgs = new String[] { String.valueOf(recipeId) };

        int deletedCount = getContentResolver().delete(
                ChiefCookContract.RecipeEntry.CONTENT_URI,
                whereClause,
                selectionArgs
        );

        deleted = deletedCount > 0;

        if (deleted) {
            Snackbar.make(
                    rvRecipeSteps,
                    R.string.snackbar_text_unfavorite_recipe,
                    Snackbar.LENGTH_LONG
            ).setAction(R.string.snackbar_action_view_favorites, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(createFavoritesIntent());
                }
            }).show();
        }

        Log.d(LOG_TAG, String.format("%d recipes deleted.", deletedCount));
    }

    private Intent createFavoritesIntent() {
        Intent favoritesIntent = new Intent(RecipeDetailsActivity.this, MainActivity.class);
        favoritesIntent.putExtra(MainActivity.CURRENT_ITEM_ID_KEY, R.id.nav_favorite_recipes);
        return favoritesIntent;
    }

    private boolean isFavoriteRecipe(Integer recipeId) {
        boolean isFavorite;

        ContentResolver contentResolver = getContentResolver();
        String[] projection = { ChiefCookContract.RecipeEntry.COLUMN_API_ID };
        String[] selectionArgs = { String.valueOf(recipeId) };

        Cursor query = contentResolver.query(
                ChiefCookContract.RecipeEntry.CONTENT_URI,
                projection,
                ChiefCookContract.RecipeEntry.COLUMN_API_ID + " = ? ",
                selectionArgs,
                null
        );

        isFavorite = query != null && query.getCount() > 0;
        query.close(); // free up the cursor

        Log.d(LOG_TAG, String.format("Recipe is favorite: %s", isFavorite));

        return isFavorite;
    }

    private int saveIngredients(List<ExtendedIngredient> ingredients) {
        int savedCount = 0;
        Vector<ContentValues> cVVector = new Vector<>();

        for (ExtendedIngredient ingredient :
                ingredients) {
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

        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);

            savedCount = getContentResolver().bulkInsert(ChiefCookContract.IngredientEntry.CONTENT_URI, cvArray);
        }

        Log.d(LOG_TAG, String.format("%d ingredients inserted.", savedCount));

        return savedCount;
    }

    @Override
    public void onAddToShoppingList(List<ExtendedIngredient> selectedIngredients) {
        int savedCount = saveIngredients(selectedIngredients);

        String snackBarText = String.format("%d %s",
                savedCount, getResources().getString(R.string.snackbar_text_add_to_shopping_list));

        Snackbar.make(rvRecipeSteps, null, Snackbar.LENGTH_LONG)
                .setText(snackBarText)
                .setAction(R.string.snackbar_action_view_favorites, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RecipeDetailsActivity.this, MainActivity.class);
                        intent.putExtra(MainActivity.CURRENT_ITEM_ID_KEY, R.id.nav_shopping_list);
                        startActivity(intent);
                    }
                })
                .show();
    }
}
