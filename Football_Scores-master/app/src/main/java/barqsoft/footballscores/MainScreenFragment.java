package barqsoft.footballscores;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import barqsoft.footballscores.data.DatabaseContract;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainScreenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String KEY_DATE = "date";

    public ScoresAdapter mAdapter;
    public static final int SCORES_LOADER = 0;
    private String[] fragmentdate = new String[1];

    @Bind(R.id.scores_list) RecyclerView mScoresList;
    @Bind(R.id.recyclerview_matches_empty) View mEmptyView;

    public MainScreenFragment() {
    }
    public static MainScreenFragment newInstance(String date) {
        MainScreenFragment result = new MainScreenFragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY_DATE, date);
        result.setArguments(bundle);

        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            fragmentdate = new String[]{args.getString(KEY_DATE)};
        }
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        mScoresList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ScoresAdapter(getActivity(), mEmptyView, new ScoresAdapter.ScoreAdapterOnClickHandler() {
            @Override
            public void onClick(final double matchId) {
                mAdapter.mDetailMatchId = matchId;
                MainActivity.selectedMatchId = (int) matchId;
                mAdapter.notifyDataSetChanged();
            }
        });
        mScoresList.setAdapter(mAdapter);
        mAdapter.mDetailMatchId = MainActivity.selectedMatchId;
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(SCORES_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(SCORES_LOADER, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), DatabaseContract.scores_table.buildScoreWithDate(),
                null, null, fragmentdate, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }


}
