package barqsoft.footballscores.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by alexanderkulikovskiy on 17.10.15.
 */
public class FootballAuthenticatorService extends Service {
    private FootballAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new FootballAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
