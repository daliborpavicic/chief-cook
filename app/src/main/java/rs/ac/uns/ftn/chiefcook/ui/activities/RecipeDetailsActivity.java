package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.api.RecipesService;
import rs.ac.uns.ftn.chiefcook.api.SpoonacularApi;
import rs.ac.uns.ftn.chiefcook.model.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String RECIPE_ID_KEY = "recipe_key";

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.tvRecipeTitle) protected TextView tvRecipeTitle;
    @BindView(R.id.ivRecipeImage) protected ImageView tvRecipeImage;

    private RecipesService recipesService;
    private ShareActionProvider shareActionProvider;

    private String recipeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        final int recipeId = intent.getIntExtra(RECIPE_ID_KEY, 0);

        Log.d("recipe details", "Recipe id: " + recipeId);

        recipesService = SpoonacularApi.getRecipesService();
        Call<Recipe> recipeInfoCall = recipesService.getRecipeInfo(recipeId);

        recipeInfoCall.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                Recipe recipe = response.body();

                tvRecipeTitle.setText(recipe.getTitle());
                Picasso.with(RecipeDetailsActivity.this)
                        .load(recipe.getImage())
                        .into(tvRecipeImage);

                recipeName = recipe.getTitle();
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