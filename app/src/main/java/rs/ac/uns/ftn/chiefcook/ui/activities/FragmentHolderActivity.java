package rs.ac.uns.ftn.chiefcook.ui.activities;

import android.os.Bundle;

import rs.ac.uns.ftn.chiefcook.R;

public class FragmentHolderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_fragment_holder, frameLayout);
    }
}
