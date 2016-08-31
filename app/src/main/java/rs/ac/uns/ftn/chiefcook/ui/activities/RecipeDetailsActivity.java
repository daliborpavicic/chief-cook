package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.api.RecipesService;
import rs.ac.uns.ftn.chiefcook.api.SpoonacularApi;
import rs.ac.uns.ftn.chiefcook.model.Recipe;
import rs.ac.uns.ftn.chiefcook.model.RecipeInstructionsItem;
import rs.ac.uns.ftn.chiefcook.model.Step;
import rs.ac.uns.ftn.chiefcook.ui.adapters.RecipeStepAdapter;

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
    private List<Step> recipeSteps;
    private String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

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

    private void loadRecipe(int recipeId) {
        Call<Recipe> recipeInfoCall = recipesService.getRecipeInfo(recipeId);

        recipeInfoCall.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                Recipe recipe = response.body();

                Picasso.with(RecipeDetailsActivity.this)
                        .load(recipe.getImage())
                        .into(tvRecipeImage);

                recipeName = recipe.getTitle();
                collapsingToolbar.setTitle(recipeName);

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

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, recipeName);

        shareActionProvider.setShareIntent(intent);

        return true;
    }
}
