package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by daliborp on 26.8.16..
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, FragmentHolderActivity.class);
        startActivity(intent);
        finish();
    }
}