package rs.ac.uns.ftn.chiefcook.ui.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterDialogFragment extends DialogFragment {

    @BindView(R.id.spCuisine)
    protected Spinner spCuisine;

    @BindView(R.id.spDiets)
    protected Spinner spDiets;

    @BindView(R.id.spIntolerances)
    protected Spinner spIntolerances;

    @BindView(R.id.spRecipeTypes)
    protected Spinner spRecipeTypes;

    private HashMap<String, String> filters;
    private FilterListener filterListener;

    public interface FilterListener {
        public void onFilter(HashMap<String, String> filters);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            // Instantiate the FilterListener so dialog can send events to the host
            filterListener = (FilterListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString()
                    + " must implement FilterListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filters = new HashMap<>();
        filters.put(HomeFragment.CUISINE_FILTER_KEY, null);
        filters.put(HomeFragment.DIET_FILTER_KEY, null);
        filters.put(HomeFragment.INTOLERANCES_FILTER_KEY, null);
        filters.put(HomeFragment.RECIPE_TYPES_FILTER_KEY, null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_filter_dialog, null);

        ButterKnife.bind(this, dialogView);

        AlertDialog filterDialog = builder.setView(dialogView)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selectedCuisine = (String) spCuisine.getSelectedItem();
                        String selectedDiet = (String) spDiets.getSelectedItem();
                        String selectedIntolerance = (String) spIntolerances.getSelectedItem();
                        String selectedRecipeType = (String) spRecipeTypes.getSelectedItem();

                        filters.put(HomeFragment.CUISINE_FILTER_KEY,
                                selectedCuisine.equals("all") ? null : selectedCuisine);
                        filters.put(HomeFragment.DIET_FILTER_KEY,
                                selectedDiet.equals("all") ? null : selectedDiet);
                        filters.put(HomeFragment.INTOLERANCES_FILTER_KEY,
                                selectedIntolerance.equals("all") ? null : selectedIntolerance);
                        filters.put(HomeFragment.RECIPE_TYPES_FILTER_KEY,
                                selectedRecipeType.equals("all") ? null : selectedRecipeType);

                        filterListener.onFilter(filters);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .create();

        return filterDialog;
    }
}
