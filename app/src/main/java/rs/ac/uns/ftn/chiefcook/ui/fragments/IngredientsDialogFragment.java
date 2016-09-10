package rs.ac.uns.ftn.chiefcook.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.model.ExtendedIngredient;
import rs.ac.uns.ftn.chiefcook.ui.adapters.IngredientArrayAdapter;

/**
 * Created by daliborp on 9.9.16..
 */
public class IngredientsDialogFragment extends DialogFragment {

    public static final String LOG_TAG = IngredientsDialogFragment.class.getSimpleName();
    public static final String KEY_INGREDIENTS = "ingredients";

    private IngredientArrayAdapter ingredientArrayAdapter;
    private ArrayList<ExtendedIngredient> ingredients;

    public interface Listener {
        void onAddToShoppingList(List<ExtendedIngredient> selectedIngredients);
    }

    public IngredientsDialogFragment() {
    }

    public static IngredientsDialogFragment newInstance(ArrayList<ExtendedIngredient> ingredients) {
        IngredientsDialogFragment ingredientsFragment = new IngredientsDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_INGREDIENTS, ingredients);
        ingredientsFragment.setArguments(args);

        return ingredientsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredients = getArguments().getParcelableArrayList(KEY_INGREDIENTS);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_ingredients_dialog, null);

        ButterKnife.bind(this, dialogView);

        ingredientArrayAdapter = createIngredientsAdapter(ingredients);

        AlertDialog ingredientsDialog = builder.setView(dialogView)
                .setTitle(R.string.ingredients_dialog_title)
                .setPositiveButton(R.string.dialog_shopping_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<ExtendedIngredient> selectedIngredients = ingredientArrayAdapter.getSelectedIngredients();
                        Listener listener = (Listener) getActivity();

                        Log.d(LOG_TAG, String.format("Number of selected ingredients: %d", selectedIngredients.size()));
                        listener.onAddToShoppingList(selectedIngredients);
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .setAdapter(ingredientArrayAdapter, null)
                .create();

        return ingredientsDialog;
    }

    private IngredientArrayAdapter createIngredientsAdapter(ArrayList<ExtendedIngredient> ingredients) {
        ExtendedIngredient[] ingredientsArray = new ExtendedIngredient[ingredients.size()];
        ingredients.toArray(ingredientsArray);

        return new IngredientArrayAdapter(getActivity(), ingredientsArray);
    }
}
