package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.data.DatabaseContract;

/**
 * Created by alexander on 22.10.15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TodayListWidgetRemoteViewsService extends RemoteViewsService {
    public final String LOG_TAG = TodayListWidgetRemoteViewsService.class.getSimpleName();

    // these indices must match the projection
    public static final int COL_DATE = 1;
    public static final int COL_MATCHTIME = 2;
    public static final int COL_HOME = 3;
    public static final int COL_AWAY = 4;
    public static final int COL_LEAGUE = 5;
    public static final int COL_HOME_GOALS = 6;
    public static final int COL_AWAY_GOALS = 7;
    public static final int COL_ID = 8;
    public static final int COL_MATCHDAY = 9;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();
                Date todayDate = new Date();
                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                data = getContentResolver().query(DatabaseContract.scores_table.buildScoreWithDate(),
                        null,
                        null,
                        new String[]{format.format(todayDate)},
//                        new String[]{"2015-10-23"},
                        null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_today_list_item);

                final String homeName = data.getString(COL_HOME);
                final String awayName = data.getString(COL_AWAY);
                final String matchTime = data.getString(COL_MATCHTIME);
                final int homeGoals = data.getInt(COL_HOME_GOALS);
                final int awayGoals = data.getInt(COL_AWAY_GOALS);
                views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(TodayListWidgetRemoteViewsService.this, homeName));
                views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(TodayListWidgetRemoteViewsService.this, awayName));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    setRemoteContentDescription(views, R.id.home_crest, homeName);
                    setRemoteContentDescription(views, R.id.away_name, awayName);
                }
                views.setTextViewText(R.id.score_textview, Utilies.getScores(homeGoals, awayGoals));
                views.setTextViewText(R.id.home_name, homeName);
                views.setTextViewText(R.id.away_name, awayName);
                views.setTextViewText(R.id.data_textview, matchTime);

                final Intent openAppActivity = new Intent();
                views.setOnClickFillInIntent(R.id.widget_list_item, openAppActivity);

                return views;
            }

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            private void setRemoteContentDescription(RemoteViews views, int widgetId, String description) {
                views.setContentDescription(widgetId, description);
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_today_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position)) {
                    return data.getLong(COL_ID);
                }
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }

}
