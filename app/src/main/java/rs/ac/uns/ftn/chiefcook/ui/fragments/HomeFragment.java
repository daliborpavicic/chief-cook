package rs.ac.uns.ftn.chiefcook.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.api.RecipesService;
import rs.ac.uns.ftn.chiefcook.api.SpoonacularApi;
import rs.ac.uns.ftn.chiefcook.model.Recipe;
import rs.ac.uns.ftn.chiefcook.model.RecipesListResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {


    public static final String LOG_TAG = HomeFragment.class.getSimpleName();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle(R.string.title_home);

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

        final TextView textView = (TextView) getView().findViewById(R.id.recipes_list);

        RecipesService recipesService = SpoonacularApi.getRecipesService();
        Call<RecipesListResponse> listCall = recipesService.searchRecipes(query, 10, null, null, null, null, null, false, null);

        listCall.enqueue(new Callback<RecipesListResponse>() {
            @Override
            public void onResponse(Call<RecipesListResponse> call, Response<RecipesListResponse> response) {
                RecipesListResponse recipesListResponse = response.body();
                List<Recipe> recipeList = recipesListResponse.getResults();

                Log.d(LOG_TAG, "Number of matched recipes: " + recipeList.size());

                if (!recipeList.isEmpty()) {
                    for (Recipe recipe: recipeList) {
                        Log.d(LOG_TAG, recipe.getTitle());
                    }
                }

                textView.setText(recipeList.toString());
            }

            @Override
            public void onFailure(Call<RecipesListResponse> call, Throwable t) {
                String localizedMessage = t.getLocalizedMessage();

                Log.d(LOG_TAG, localizedMessage);
                textView.setText(localizedMessage);

            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
