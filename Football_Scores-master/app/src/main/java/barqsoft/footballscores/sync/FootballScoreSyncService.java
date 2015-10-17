package barqsoft.footballscores.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Objects;

/**
 * Created by alexanderkulikovskiy on 17.10.15.
 */
public class FootballScoreSyncService extends Service {
    public static final Object sSyncAdapterLock = new Object();
    private static FootballScoreSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("FootballSyncService", "onCreate - FootballScoreSyncService");
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new FootballScoreSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
