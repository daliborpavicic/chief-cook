package rs.ac.uns.ftn.chiefcook.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.model.ExtendedIngredient;
import rs.ac.uns.ftn.chiefcook.ui.adapters.IngredientsAdapter;

/**
 * Created by daliborp on 9.9.16..
 */
public class IngredientsFragment extends DialogFragment {

    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String LOG_TAG = IngredientsFragment.class.getSimpleName();

    @BindView(R.id.lvIngredients) protected ListView lvIngredients;
    @BindView(R.id.btnAddToShoppingList) protected Button btnAddToShoppingList;

    private IngredientsAdapter ingredientsAdapter;
    private ArrayList<ExtendedIngredient> ingredients;

    public interface Listener {
        void onAddToShoppingList(List<ExtendedIngredient> selectedIngredients);
    }

    public IngredientsFragment() {
    }

    public static IngredientsFragment newInstance(ArrayList<ExtendedIngredient> ingredients) {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container);
        ButterKnife.bind(this, rootView);

        btnAddToShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ExtendedIngredient> selectedIngredients = ingredientsAdapter.getSelectedIngredients();
                Listener listener = (Listener) getActivity();
                listener.onAddToShoppingList(selectedIngredients);
                dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(
                getContext().getResources().getString(R.string.ingredients_dialog_title));

        ExtendedIngredient[] ingredientsArray = new ExtendedIngredient[ingredients.size()];
        ingredients.toArray(ingredientsArray);

        ingredientsAdapter = new IngredientsAdapter(getActivity(), ingredientsArray);
        lvIngredients.setAdapter(ingredientsAdapter);
    }

}
