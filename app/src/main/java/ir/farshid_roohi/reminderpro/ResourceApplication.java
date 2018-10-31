package ir.farshid_roohi.reminderpro;

import android.content.Context;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

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
        this.initCalligraphy();
    }

    public Context getContext() {
        return context;
    }

    public Storage getStorage() {
        return storage;
    }

    private void initCalligraphy() {
        ViewPump.init(ViewPump.builder().addInterceptor(new CalligraphyInterceptor(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/IRSANS_light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build())).build());
    }

}
