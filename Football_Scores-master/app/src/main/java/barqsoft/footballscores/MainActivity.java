package barqsoft.footballscores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import barqsoft.footballscores.sync.FootballScoreSyncAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    private static final String SAVE_TAG = "Save Test";

    public static int selected_match_id;
    public static int current_fragment = 2;
    PagerFragment mainPagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "Reached MainActivity onCreate");
        if (savedInstanceState == null) {
            mainPagerFragment = new PagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mainPagerFragment)
                    .commit();
        } else {
            mainPagerFragment = (PagerFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_pager);
        }

//        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);

        FootballScoreSyncAdapter.initializeSyncAdapter(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent start_about = new Intent(this, AboutActivity.class);
            startActivity(start_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v(SAVE_TAG, "will save");
        Log.v(SAVE_TAG, "fragment: " + String.valueOf(mainPagerFragment.viewPager.getCurrentItem()));
        Log.v(SAVE_TAG, "selected id: " + selected_match_id);
        outState.putInt("Pager_Current", mainPagerFragment.viewPager.getCurrentItem());
        outState.putInt("Selected_match", selected_match_id);
        getSupportFragmentManager().putFragment(outState, "mainPagerFragment", mainPagerFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(SAVE_TAG, "will retrive");
        Log.v(SAVE_TAG, "fragment: " + String.valueOf(savedInstanceState.getInt("Pager_Current")));
        Log.v(SAVE_TAG, "selected id: " + savedInstanceState.getInt("Selected_match"));
        current_fragment = savedInstanceState.getInt("Pager_Current");
        selected_match_id = savedInstanceState.getInt("Selected_match");
        mainPagerFragment = (PagerFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mainPagerFragment");
        super.onRestoreInstanceState(savedInstanceState);
    }


}
