package rs.ac.uns.ftn.chiefcook.ui.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.data.ChiefCookContract;
import rs.ac.uns.ftn.chiefcook.ui.adapters.IngredientCursorAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends Fragment {

    private IngredientCursorAdapter adapter;

    LoaderManager.LoaderCallbacks<Cursor> loader =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {

                    String[] projectionFields = {
                            ChiefCookContract.IngredientEntry._ID,
                            ChiefCookContract.IngredientEntry.COLUMN_NAME,
                            ChiefCookContract.IngredientEntry.COLUMN_AMOUNT,
                            ChiefCookContract.IngredientEntry.COLUMN_UNIT
                    };

                    String sortOrder = ChiefCookContract.IngredientEntry.COLUMN_NAME + " ASC";

                    CursorLoader cursorLoader = new CursorLoader(
                            getActivity(),
                            ChiefCookContract.IngredientEntry.CONTENT_URI,
                            projectionFields,
                            null,
                            null,
                            sortOrder
                    );

                    return cursorLoader;
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    adapter.swapCursor(data);
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    adapter.swapCursor(null);
                }
            };


    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shopping_list_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_items:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new IngredientCursorAdapter(getActivity(), null);

        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        ListView lvShoppingItems = (ListView) rootView.findViewById(R.id.lvShoppingItems);
        lvShoppingItems.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, new Bundle(), loader);
        super.onActivityCreated(savedInstanceState);
    }
}
