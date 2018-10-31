package ir.farshid_roohi.reminderpro;

import android.app.Application;

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ResourceApplication.build(this);
    }
}
