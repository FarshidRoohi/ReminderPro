package ir.farshid_roohi.reminderpro;

import android.content.Context;

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */


public class ResourceApplication {

    private static ResourceApplication instance;

    private Context context;
    private Storage storage;

    public static void build(Context context) {
        instance = new ResourceApplication(context);
    }

    public static ResourceApplication getApplicationResource() {
        return instance;
    }

    public ResourceApplication(Context context) {
        this.context = context;
        this.storage = new Storage(context);
    }

    public Context getContext() {
        return context;
    }

    public Storage getStorage() {
        return storage;
    }

}
