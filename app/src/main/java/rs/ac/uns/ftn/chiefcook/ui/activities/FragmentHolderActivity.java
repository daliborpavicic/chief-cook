package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.ui.fragments.FavoriteRecipesFragment;
import rs.ac.uns.ftn.chiefcook.ui.fragments.HomeFragment;
import rs.ac.uns.ftn.chiefcook.ui.fragments.RecipesFragment;

public class FragmentHolderActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_fragment_holder, frameLayout);

        setupActionBar();
        setTitle("");

        int fragmentIndex = getIntent().getIntExtra(DRAWER_ITEM_ID_KEY, 0);

        navigationView.getMenu().getItem(fragmentIndex).setChecked(true);

        Fragment selectedFragment = selectFragment(fragmentIndex);
        displayFragment(selectedFragment);
    }

    private void setupActionBar() {
        // Set a toolbar to replace the action bar.
        toolbar = (Toolbar) findViewById(R.id.toolbar_fragment_holder);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_drawer);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private Fragment selectFragment(int fragmentIndex) {
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

        return fragment;
    }

    private void displayFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
