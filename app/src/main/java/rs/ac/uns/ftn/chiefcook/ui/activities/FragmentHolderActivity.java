package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.ui.fragments.FavoriteRecipesFragment;
import rs.ac.uns.ftn.chiefcook.ui.fragments.HomeFragment;
import rs.ac.uns.ftn.chiefcook.ui.fragments.RecipesFragment;

public class FragmentHolderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_fragment_holder, frameLayout);
        Intent intent = getIntent();
        int fragmentIndex = intent.getIntExtra(DRAWER_ITEM_ID_KEY, 0);

        Fragment fragment;

        switch (fragmentIndex) {
            case 1:
                fragment = new RecipesFragment();
                break;
            case 2:
                fragment = new FavoriteRecipesFragment();
                break;
            default:
                fragment = new HomeFragment();
        }

        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
