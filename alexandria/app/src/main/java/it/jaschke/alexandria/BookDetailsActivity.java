package it.jaschke.alexandria;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by alexanderkulikovskiy on 24.10.15.
 */
public class BookDetailsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbarView = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarView);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putString(FragmentBookDetail.EAN_KEY, getIntent().getStringExtra(FragmentBookDetail.EAN_KEY));

            FragmentBookDetail fragment = new FragmentBookDetail();
            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }
}
