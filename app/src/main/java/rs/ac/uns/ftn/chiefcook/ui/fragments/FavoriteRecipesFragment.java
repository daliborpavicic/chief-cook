package rs.ac.uns.ftn.chiefcook.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rs.ac.uns.ftn.chiefcook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteRecipesFragment extends Fragment {


    public FavoriteRecipesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_recipes, container, false);

        return rootView;
    }

}
