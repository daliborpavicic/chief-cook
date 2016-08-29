package rs.ac.uns.ftn.chiefcook.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.api.RecipesService;
import rs.ac.uns.ftn.chiefcook.api.SpoonacularApi;
import rs.ac.uns.ftn.chiefcook.model.Recipe;
import rs.ac.uns.ftn.chiefcook.model.RecipesListResponse;
import rs.ac.uns.ftn.chiefcook.ui.activities.RecipeDetailsActivity;
import rs.ac.uns.ftn.chiefcook.ui.adapters.RecipeAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
        implements SearchView.OnQueryTextListener, FilterDialogFragment.FilterListener {


    public static final String LOG_TAG = HomeFragment.class.getSimpleName();
    public static final String CUISINE_FILTER_KEY = "cuisine";
    public static final String DIET_FILTER_KEY = "diet";
    public static final String INTOLERANCES_FILTER_KEY = "intolerances";
    public static final String RECIPE_TYPES_FILTER_KEY = "recipeTypes";

    protected RecyclerView rvRecipes;

    private RecipeAdapter recipeAdapter;
    private RecipesService recipesService;
    private RecipesListResponse recipesListResponse;

    private String filterCuisine;
    private String filterDiet;
    private String filterIntolerance;
    private String filterRecipeType;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        recipesService = SpoonacularApi.getRecipesService();
        recipesListResponse = new RecipesListResponse();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rvRecipes = (RecyclerView) rootView.findViewById(R.id.rvRecipes);

        recipeAdapter = new RecipeAdapter(getActivity(), recipesListResponse.getResults());

        rvRecipes.setAdapter(recipeAdapter);
        rvRecipes.setHasFixedSize(true);

        int spanCount = 2;
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getActivity(), spanCount, GridLayoutManager.VERTICAL, false);
        rvRecipes.setLayoutManager(gridLayoutManager);

        recipeAdapter.setListener(new RecipeAdapter.Listener() {
            @Override
            public void onClick(int recipeId) {
                Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
                intent.putExtra(RecipeDetailsActivity.RECIPE_ID_KEY, recipeId);
                startActivity(intent);
            }
        });

        getRecipeMatches("egg");

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home_fragment, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                FilterDialogFragment filterDialogFragment = new FilterDialogFragment();
                filterDialogFragment.setTargetFragment(this, 0);
                filterDialogFragment.show(getActivity().getSupportFragmentManager(), null);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(LOG_TAG, query);

        recipesListResponse.getResults().clear();
        getRecipeMatches(query);

        return true;
    }

    private void getRecipeMatches(String query) {
        Call<RecipesListResponse> listCall = recipesService.searchRecipes(query,
                10, filterRecipeType, filterCuisine, filterDiet, null, filterIntolerance, false, null);

        listCall.enqueue(new Callback<RecipesListResponse>() {
            @Override
            public void onResponse(Call<RecipesListResponse> call, Response<RecipesListResponse> response) {
                List<Recipe> recipeResults = response.body().getResults();
                String baseUri = response.body().getBaseUri();

                recipeAdapter.setBaseImageUrl(baseUri);

                recipesListResponse.getResults().clear();
                recipesListResponse.getResults().addAll(recipeResults);

                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RecipesListResponse> call, Throwable t) {
                String localizedMessage = t.getLocalizedMessage();

                Log.d(LOG_TAG, localizedMessage);
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onFilter(HashMap<String, String> filters) {
        filterCuisine = filters.get(CUISINE_FILTER_KEY);
        filterDiet = filters.get(DIET_FILTER_KEY);
        filterIntolerance = filters.get(INTOLERANCES_FILTER_KEY);
        filterRecipeType = filters.get(RECIPE_TYPES_FILTER_KEY);
    }
}
