package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import rs.ac.uns.ftn.chiefcook.R;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String DRAWER_ITEM_ID_KEY = "drawer_item_id_key";

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.home:
                displayActivity(FragmentHolderActivity.class, 0);
                return true;
            case R.id.recipes:
                displayActivity(FragmentHolderActivity.class, 1);
                return true;
            case R.id.favorite_recipes:
                displayActivity(FragmentHolderActivity.class, 2);
                return true;
            default:
                return true;
        }
    }

    private void displayActivity(Class activityClass, int drawerItemIndex) {
        drawerLayout.closeDrawers();
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(DRAWER_ITEM_ID_KEY, drawerItemIndex);
        startActivity(intent);
        this.overridePendingTransition(0, 0); // disable animation when starting the activity
        finish(); // finish the current activity
    }
}
