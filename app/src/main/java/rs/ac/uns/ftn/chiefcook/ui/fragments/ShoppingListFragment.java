package rs.ac.uns.ftn.chiefcook.ui.fragments;


import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.DialogInterface;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.data.ChiefCookContract;
import rs.ac.uns.ftn.chiefcook.ui.adapters.IngredientCursorAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends Fragment {

    public static final String LOG_TAG = ShoppingListFragment.class.getSimpleName();
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
                if (adapter.getSelectedIngredientIds().isEmpty()) {
                    return false;
                } else {
                    showConfirmationDialog();
                    return true;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showConfirmationDialog() {
        FragmentActivity activity = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog confirmationDialog = builder
                .setTitle(activity.getString(R.string.dialog_delete_title))
                .setMessage(activity.getString(R.string.dialog_delete_message))
                .setPositiveButton(activity.getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteSelectedIngredients(adapter.getSelectedIngredientIds());
                    }
                })
                .setNegativeButton(activity.getString(R.string.dialog_no), null)
                .create();

        confirmationDialog.show();
    }

    private void deleteSelectedIngredients(List<Integer> selectedIngredientIds) {

        ArrayList<ContentProviderOperation> deleteOperations = new ArrayList<>();

        for (Integer ingredientId :selectedIngredientIds) {
            ContentProviderOperation deleteOperation = ContentProviderOperation
                    .newDelete(ChiefCookContract.IngredientEntry.CONTENT_URI)
                    .withSelection(
                            ChiefCookContract.IngredientEntry._ID + " = ?",
                            new String[] { String.valueOf(ingredientId) }
                    )
                    .build();

            deleteOperations.add(deleteOperation);
        }

        try {
            ContentProviderResult[] contentProviderResults = getActivity().getContentResolver()
                    .applyBatch(ChiefCookContract.CONTENT_AUTHORITY, deleteOperations);

            Log.d(LOG_TAG, String.format("Executed delete operations: %d", contentProviderResults.length));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
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
