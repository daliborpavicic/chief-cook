package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import rs.ac.uns.ftn.chiefcook.R;

/**
 * Created by daliborp on 26.8.16..
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        final TextView splashText = (TextView) findViewById(R.id.tvSplashText);
        final Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);

        final Animation fadeAnimation = AnimationUtils.loadAnimation(this,
                android.support.v7.appcompat.R.anim.abc_fade_out);

        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashText.startAnimation(fadeAnimation);
                finish();
                Intent intent = new Intent(SplashActivity.this, FragmentHolderActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        splashText.startAnimation(rotation);
    }
}
