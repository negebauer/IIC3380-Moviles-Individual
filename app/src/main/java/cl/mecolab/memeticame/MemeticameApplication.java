package cl.mecolab.memeticame;

import android.app.Application;

import cl.mecolab.memeticame.networking.RequestManager;

/**
 * Created by aamat on 07-Sep-17.
 */

public class MemeticameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Init request manager
        RequestManager.getInstance(getApplicationContext());
    }
}
