package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;
import rs.ac.uns.ftn.chiefcook.ui.fragments.FavoriteRecipesFragment;
import rs.ac.uns.ftn.chiefcook.ui.fragments.HomeFragment;
import rs.ac.uns.ftn.chiefcook.ui.fragments.SettingsFragment;
import rs.ac.uns.ftn.chiefcook.ui.fragments.ShoppingListFragment;

/**
 * Created by daliborp on 28.8.16..
 */
public class MainActivity extends AppCompatActivity {

    public static final String VISIBLE_FRAGMENT_TAG = "visible_fragment";
    public static final String CURRENT_ITEM_ID_KEY = "currentItemId";

    @BindView(R.id.drawer_layout) protected DrawerLayout drawerLayout;
    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.nvView) protected NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    private int currentItemId = R.id.nav_home;
    boolean addToBackStack = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // Set a Toolbar to replace the ActionBar.
        setSupportActionBar(toolbar);

        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener(drawerToggle);

        // Setup drawer view
        setupDrawerContent(nvDrawer);

        final Menu nvDrawerMenu = nvDrawer.getMenu();

        if (savedInstanceState == null) {
            selectDrawerItem(nvDrawerMenu.findItem(R.id.nav_home));
        } else {

            currentItemId = savedInstanceState.getInt(CURRENT_ITEM_ID_KEY);
            selectDrawerItem(nvDrawerMenu.findItem(currentItemId));
        }

        // add all fragment transactions to the back stack, except the first one to avoid empty activity on a back button click
        addToBackStack = true;

        getSupportFragmentManager()
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Fragment visibleFragment = fragmentManager.findFragmentByTag(VISIBLE_FRAGMENT_TAG);

                        if (visibleFragment instanceof HomeFragment) {
                            currentItemId = R.id.nav_home;
                        }

                        if (visibleFragment instanceof FavoriteRecipesFragment) {
                            currentItemId = R.id.nav_favorite_recipes;
                        }

                        if (visibleFragment instanceof ShoppingListFragment) {
                            currentItemId = R.id.nav_shopping_list;
                        }

                        if (visibleFragment instanceof SettingsFragment) {
                            currentItemId = R.id.nav_settings;
                        }

                        setToolbarTitle(nvDrawer.getMenu().findItem(currentItemId));
                        nvDrawer.setCheckedItem(currentItemId);
                    }
                });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // update current item
        currentItemId =  menuItem.getItemId();
        Fragment fragment = createFragmentForMenuItem(currentItemId);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager
                .beginTransaction()
                .replace(R.id.flContent, fragment, VISIBLE_FRAGMENT_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title. Use app name if home fragment is selected
        setToolbarTitle(menuItem);
        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }

    @Nullable
    private Fragment createFragmentForMenuItem(int itemId) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(itemId) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_favorite_recipes:
                fragmentClass = FavoriteRecipesFragment.class;
                break;
            case R.id.nav_shopping_list:
                fragmentClass = ShoppingListFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fragment;
    }

    private void setToolbarTitle(MenuItem menuItem) {
        setTitle(menuItem.getItemId() == R.id.nav_home ?
                getResources().getString(R.string.app_name) : menuItem.getTitle());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_ITEM_ID_KEY, currentItemId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // This is necessary so that ActionBarDrawerToggle can handle being clicked
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);

    }
}
