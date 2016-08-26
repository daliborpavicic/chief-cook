package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import rs.ac.uns.ftn.chiefcook.R;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String DRAWER_ITEM_ID_KEY = "drawer_item_id_key";

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    @BindView(R.id.content_frame)
    protected FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
            case R.id.settings:
                displayActivity(FragmentHolderActivity.class, 3);
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
