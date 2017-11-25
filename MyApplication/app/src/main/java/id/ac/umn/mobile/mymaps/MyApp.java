package id.ac.umn.mobile.mymaps;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by Reynaldi on 7/21/2017.
 */
public class MyApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        SystemClock.sleep(2000);
    }
}
