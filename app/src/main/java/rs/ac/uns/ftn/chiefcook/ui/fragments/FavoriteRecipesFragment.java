package rs.ac.uns.ftn.chiefcook.ui.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.data.ChiefCookContract;
import rs.ac.uns.ftn.chiefcook.ui.activities.RecipeDetailsActivity;
import rs.ac.uns.ftn.chiefcook.ui.adapters.FavoriteRecipeAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteRecipesFragment extends Fragment
        implements FavoriteRecipeAdapter.Listener {

    private FavoriteRecipeAdapter favoriteRecipeAdapter;

    LoaderManager.LoaderCallbacks<Cursor> favoriteRecipesLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {

                    String[] projectionFields = {
                            ChiefCookContract.RecipeEntry._ID,
                            ChiefCookContract.RecipeEntry.COLUMN_TITLE,
                            ChiefCookContract.RecipeEntry.COLUMN_READY_IN_MINUTES,
                            ChiefCookContract.RecipeEntry.COLUMN_IMAGE_URL,
                            ChiefCookContract.RecipeEntry.COLUMN_API_ID
                    };

                    String sortOrder = ChiefCookContract.RecipeEntry.COLUMN_TITLE + " ASC";

                    CursorLoader cursorLoader = new CursorLoader(
                            getActivity(),
                            ChiefCookContract.RecipeEntry.CONTENT_URI,
                            projectionFields,
                            null,
                            null,
                            sortOrder
                    );

                    return cursorLoader;
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    favoriteRecipeAdapter.swapCursor(data);
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    favoriteRecipeAdapter.swapCursor(null);
                }
            };

    public FavoriteRecipesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        favoriteRecipeAdapter = new FavoriteRecipeAdapter(getActivity(), null);
        favoriteRecipeAdapter.setListener(this);

        View rootView = inflater.inflate(R.layout.fragment_favorite_recipes, container, false);

        ListView lvRecipes = (ListView) rootView.findViewById(R.id.lvFavoriteRecipes);
        lvRecipes.setAdapter(favoriteRecipeAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, new Bundle(), favoriteRecipesLoader);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRecipeClick(int recipeId) {
        Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.RECIPE_ID_KEY, recipeId);
        startActivity(intent);
    }
}
