package rs.ac.uns.ftn.chiefcook.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.api.RecipesService;
import rs.ac.uns.ftn.chiefcook.api.SpoonacularApi;
import rs.ac.uns.ftn.chiefcook.model.Recipe;
import rs.ac.uns.ftn.chiefcook.model.RecipesListResponse;
import rs.ac.uns.ftn.chiefcook.ui.adapters.RecipeAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {


    public static final String LOG_TAG = HomeFragment.class.getSimpleName();

    protected RecyclerView rvRecipes;

    private RecyclerView.Adapter recipeAdapter;
    private List<Recipe> recipes;
    private RecipesService recipesService;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        recipesService = SpoonacularApi.getRecipesService();
        recipes = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rvRecipes = (RecyclerView) rootView.findViewById(R.id.rvRecipes);
        getActivity().setTitle(R.string.title_home);

        recipeAdapter = new RecipeAdapter(getActivity(), recipes);
        rvRecipes.setAdapter(recipeAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvRecipes.setLayoutManager(linearLayoutManager);

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(true);
        super.onPrepareOptionsMenu(menu);
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
    public boolean onQueryTextSubmit(String query) {
        Log.d(LOG_TAG, query);

        recipes.clear();
        getRecipeMatches(query);

        return true;
    }

    private void getRecipeMatches(String query) {
        Call<RecipesListResponse> listCall = recipesService.searchRecipes(query, 10, null, null, null, null, null, false, null);

        listCall.enqueue(new Callback<RecipesListResponse>() {
            @Override
            public void onResponse(Call<RecipesListResponse> call, Response<RecipesListResponse> response) {
                RecipesListResponse recipesListResponse = response.body();
                List<Recipe> recipeMatches = recipesListResponse.getResults();

                Log.d(LOG_TAG, "Number of matched recipes: " + recipeMatches.size());

                if (!recipeMatches.isEmpty()) {
                    for (Recipe recipe: recipeMatches) {
                        Log.d(LOG_TAG, recipe.getTitle());
                    }
                }

                recipes.addAll(recipeMatches);
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
}
