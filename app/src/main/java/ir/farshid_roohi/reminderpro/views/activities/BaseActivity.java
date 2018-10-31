package ir.farshid_roohi.reminderpro.views.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ir.farshid_roohi.reminderpro.ResourceApplication;

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private static final int                         PERMISSION_REQUEST_ID = 901;
    private              OnPermissionRequestListener listener;

    private ResourceApplication resourceApplication;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.resourceApplication = ResourceApplication.getApplicationResource();

    }


    public <T extends ViewDataBinding> T setBindingView(@LayoutRes int layout) {
        T binding = DataBindingUtil.setContentView(this, layout);
        return binding;
    }


    public ResourceApplication getResourceApp() {
        return this.resourceApplication;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermission(String[] permissions, OnPermissionRequestListener listener) {


        this.listener = listener;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.listener.onAllow("Current SDK Version lower than API 23");
            return;
        }


        List<String> permissionList = new ArrayList<>();


        for (String permission : permissions) {
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                this.listener.onAllow(permission);
                continue;
            }
            permissionList.add(permission);
        }

        if (permissionList.size() <= 0) {
            return;
        }
        requestPermissions(
                permissionList.toArray(new String[permissionList.size()])
                , PERMISSION_REQUEST_ID
        );


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode != PERMISSION_REQUEST_ID) {
            return;
        }

        for (int i = 0; i < permissions.length; i++) {

            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                this.listener.onAllow(permissions[i]);
                continue;
            }

            listener.onDenied(permissions[i]);

        }

    }

    public interface OnPermissionRequestListener {
        void onAllow(String permission);

        void onDenied(String permission);
    }
}
