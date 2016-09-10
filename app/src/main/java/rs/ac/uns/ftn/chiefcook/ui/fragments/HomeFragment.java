package rs.ac.uns.ftn.chiefcook.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.api.RecipesService;
import rs.ac.uns.ftn.chiefcook.api.SpoonacularApi;
import rs.ac.uns.ftn.chiefcook.model.AutocompleteRecipeSearchModel;
import rs.ac.uns.ftn.chiefcook.model.Recipe;
import rs.ac.uns.ftn.chiefcook.model.RecipesListResponse;
import rs.ac.uns.ftn.chiefcook.ui.activities.RecipeDetailsActivity;
import rs.ac.uns.ftn.chiefcook.ui.adapters.RecipeAdapter;
import rs.ac.uns.ftn.chiefcook.ui.adapters.SearchSuggestionAdapter;
import rs.ac.uns.ftn.chiefcook.util.EndlessRecyclerViewScrollListener;
import rs.ac.uns.ftn.chiefcook.util.SearchSuggestionsCursor;
import rs.ac.uns.ftn.chiefcook.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
        implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener, FilterDialogFragment.FilterListener {

    public static final String LOG_TAG = HomeFragment.class.getSimpleName();
    public static final String CUISINE_FILTER_KEY = "cuisine";
    public static final String DIET_FILTER_KEY = "diet";
    public static final String INTOLERANCES_FILTER_KEY = "intolerances";
    public static final String RECIPE_TYPES_FILTER_KEY = "recipeTypes";

    @BindView(R.id.rvRecipes) protected RecyclerView rvRecipes;
    private SearchView searchView;

    private RecipeAdapter recipeAdapter;
    private SearchSuggestionAdapter searchSuggestionAdapter;

    private RecipesService recipesService;
    private List<Recipe> recipeList;

    private List<String> searchSuggestions;
    private String query = "egg";
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
        recipeList = new ArrayList<>();
        searchSuggestions = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        setupRecipeRecycler();

        searchSuggestionAdapter = setupSuggestionAdapter();

        getRecipeMatches(0);

        return rootView;
    }

    @NonNull
    private SearchSuggestionAdapter setupSuggestionAdapter() {
        return new SearchSuggestionAdapter(getActivity(),
                new SearchSuggestionsCursor());
    }

    private void setupRecipeRecycler() {
        int spanCount = 2;
        recipeAdapter = new RecipeAdapter(getActivity(), recipeList);

        recipeAdapter.setListener(new RecipeAdapter.Listener() {
            @Override
            public void onClick(int recipeId) {
                Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
                intent.putExtra(RecipeDetailsActivity.RECIPE_ID_KEY, recipeId);
                startActivity(intent);
            }
        });

        rvRecipes.setAdapter(recipeAdapter);
        rvRecipes.setHasFixedSize(true); // performance optimization for smoother scrolling

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        rvRecipes.setLayoutManager(gridLayoutManager);

        rvRecipes.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d(LOG_TAG, "Current page = " + page);
                getRecipeMatches(page);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home_fragment, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);
        searchView.setQueryHint(getResources().getString(R.string.action_search_hint));
        searchView.setSuggestionsAdapter(searchSuggestionAdapter);

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
    public boolean onQueryTextSubmit(String queryText) {
        query = queryText;
        recipeList.clear();
        getRecipeMatches(0);
        searchView.clearFocus();

        return true;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        int minQueryLength = 2;
        boolean shouldShowSearchSuggestions = Utils.isSearchSuggestionsEnabled(getActivity())
                && newText.length() > minQueryLength;

        Log.d(LOG_TAG, "Show suggestions: " + shouldShowSearchSuggestions);

        if (shouldShowSearchSuggestions) {
            loadSearchSuggestions(newText);

            return true;
        }

        return false;
    }

    private void loadSearchSuggestions(final String queryText) {
        searchSuggestionAdapter.notifyDataSetInvalidated();

        Call<List<AutocompleteRecipeSearchModel>> listCall = recipesService.autocompleteRecipeSearch(queryText, 10);

        listCall.enqueue(new Callback<List<AutocompleteRecipeSearchModel>>() {
            @Override
            public void onResponse(Call<List<AutocompleteRecipeSearchModel>> call,
                                   Response<List<AutocompleteRecipeSearchModel>> response) {
                List<AutocompleteRecipeSearchModel> recipeAutocompletes = response.body();

                if (recipeAutocompletes.size() > 0) {
                    SearchSuggestionsCursor searchSuggestionsCursor = new SearchSuggestionsCursor();
                    searchSuggestions = Utils.mapToStringList(recipeAutocompletes);
                    searchSuggestionsCursor.addSearchSuggestions(searchSuggestions);

                    searchSuggestionAdapter.changeCursor(searchSuggestionsCursor);
                } else {
                    Log.d(LOG_TAG, String.format("No search suggestions available for: %s", queryText));
                }
            }

            @Override
            public void onFailure(Call<List<AutocompleteRecipeSearchModel>> call,
                                  Throwable t) {
                Log.e(LOG_TAG, t.getLocalizedMessage());
            }
        });
    }

    private void getRecipeMatches(int page) {
        int numberOfResults = 10;

        Call<RecipesListResponse> listCall = recipesService.searchRecipes(
                query, numberOfResults, numberOfResults * page,
                filterRecipeType, filterCuisine, filterDiet, null, filterIntolerance, false
        );

        listCall.enqueue(new Callback<RecipesListResponse>() {
            @Override
            public void onResponse(Call<RecipesListResponse> call, Response<RecipesListResponse> response) {
                List<Recipe> recipeResults = response.body().getResults();
                String baseUri = response.body().getBaseUri();

                recipeList.addAll(recipeResults);
                recipeAdapter.setBaseImageUrl(baseUri);

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
    public boolean onSuggestionSelect(int position) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        searchView.setQuery(searchSuggestions.get(position), true);
        searchView.clearFocus();
        return true;
    }

    @Override
    public void onFilter(HashMap<String, String> filters) {
        filterCuisine = filters.get(CUISINE_FILTER_KEY);
        filterDiet = filters.get(DIET_FILTER_KEY);
        filterIntolerance = filters.get(INTOLERANCES_FILTER_KEY);
        filterRecipeType = filters.get(RECIPE_TYPES_FILTER_KEY);
    }
}
