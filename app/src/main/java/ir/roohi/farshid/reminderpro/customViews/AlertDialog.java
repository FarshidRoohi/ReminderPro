package ir.roohi.farshid.reminderpro.customViews;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import ir.roohi.farshid.reminderpro.R;

/**
 * Created by Farshid Roohi.
 * MyZarinPal-AndroidV4 | Copyrights 11/18/18.
 */

@SuppressLint("ValidFragment")
public class AlertDialog extends DialogFragment {

    private FragmentManager fragmentManager;
    private ViewGroup       layoutAllContent;
    private ImageView       imgDialog;
    private Button          btnPositive;
    private Button          btnNegative;
    private Button          btnSkip;
    private CheckBox        chkNotShowAgain;

    private Builder builder;

    private AlertDialog(FragmentManager fm, Builder builder) {
        this.fragmentManager = fm;
        this.builder = builder;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.alert_dialog, container);

        this.layoutAllContent = view.findViewById(R.id.layout_all_content);
        this.imgDialog = view.findViewById(R.id.img_dialog);
        this.btnPositive = view.findViewById(R.id.btn_positive);
        this.btnNegative = view.findViewById(R.id.btn_negative);
        this.btnSkip = view.findViewById(R.id.btn_skip);
        this.chkNotShowAgain = view.findViewById(R.id.chk_not_show_again);

        TextView txtTitle = view.findViewById(R.id.txt_title);
        TextView txtDesc  = view.findViewById(R.id.txt_desc);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        txtTitle.setText(this.builder.getTitle());
        txtDesc.setText(this.builder.getDesc());

        this.setPositiveButton(this.builder.getBtnPositiveTitle(), this.builder.getBtnPositiveListener());
        this.setNegativeButton(this.builder.getBtnNegativeTitle(), this.builder.getBtnNegativeListener());
        this.setSkipButton(this.builder.getBtnThirdTitle(), this.builder.getBtnThirdListener());
        this.setCheckBox(this.builder.getChkTitle(), this.builder.getChkChangeListener());
        this.setButtonColor(this.builder.getColorButton());
        this.setBackgroundColor(this.builder.getColorBackground());
        this.setIcon(this.builder.getIcon());


        return view;
    }

    private void setPositiveButton(String title, View.OnClickListener listener) {
        this.btnPositive.setVisibility(View.VISIBLE);
        if (listener == null) {
            this.btnPositive.setText(getString(R.string.ok));
            this.btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            return;
        }

        this.btnPositive.setText(title);
        this.btnPositive.setOnClickListener(listener);
    }


    private void setNegativeButton(String title, View.OnClickListener listener) {
        if (listener == null) {
            return;
        }
        this.btnNegative.setVisibility(View.VISIBLE);
        this.btnNegative.setText(title);
        this.btnNegative.setOnClickListener(listener);
    }

    private void setSkipButton(String title, View.OnClickListener listener) {

        if (listener == null) {
            return;
        }

        this.btnSkip.setVisibility(View.VISIBLE);
        this.btnSkip.setText(title);
        this.btnSkip.setOnClickListener(listener);
    }

    private void setCheckBox(String title, CheckBox.OnCheckedChangeListener listener) {

        if (listener == null) {
            return;
        }
        this.chkNotShowAgain.setVisibility(View.VISIBLE);
        this.chkNotShowAgain.setText(title);
        this.chkNotShowAgain.setOnCheckedChangeListener(listener);
    }

    private void setIcon(@DrawableRes int icon) {
        if (icon == 0) {
            this.imgDialog.setVisibility(View.GONE);
            return;
        }
        this.imgDialog.setImageResource(icon);

    }

    private void setButtonColor(@ColorInt int color) {
        if (color == 0) {
            color = Color.DKGRAY;
        }
        this.chkNotShowAgain.setTextColor(color);
        this.btnSkip.setTextColor(color);
        this.btnNegative.setTextColor(color);
        this.btnPositive.setTextColor(color);

    }

    private void setBackgroundColor(@ColorInt int color) {
        if (color == 0) {
            color = Color.WHITE;
        }
        this.layoutAllContent.setBackgroundColor(color);
    }

    public void show() {
        super.show(this.fragmentManager, getClass().getSimpleName());
    }

    public static class Builder {

        private AlertDialog     zarinAlertDialog;
        private String          title;
        private String          desc;
        private boolean         cancelable;
        private FragmentManager fm;


        //region Button Negative
        private String               btnNegativeTitle;
        private View.OnClickListener btnNegativeListener;
        //endRegion

        //region Button Positive
        private String               btnPositiveTitle;
        private View.OnClickListener btnPositiveListener;
        //endRegion

        //region Third Button
        private String               btnThirdTitle;
        private View.OnClickListener btnThirdListener;
        //endRegion

        //region CheckBox
        private String                           chkTitle;
        private CheckBox.OnCheckedChangeListener chkChangeListener;
        //endRegion


        @DrawableRes
        private int iconRes;
        @ColorInt
        private int colorButton;
        @ColorInt
        private int colorBackground;


        public Builder(@NonNull FragmentManager fm, @NonNull String title, @NonNull String desc) {
            this.title = title;
            this.desc = desc;
            this.fm = fm;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getIcon() {
            return iconRes;
        }

        public void setIcon(@DrawableRes int iconRes) {
            this.iconRes = iconRes;
        }

        public int getColorButton() {
            return colorButton;
        }

        public void setColorButton(@ColorInt int colorButton) {
            this.colorButton = colorButton;
        }

        public int getColorBackground() {
            return colorBackground;
        }

        public void setColorBackground(@ColorInt int colorBackground) {
            this.colorBackground = colorBackground;
        }

        public boolean isCancelable() {
            return cancelable;
        }

        public void setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
        }

        public void setBtnNegative(@NonNull String title, @NonNull View.OnClickListener listener) {
            this.btnNegativeTitle = title;
            this.btnNegativeListener = listener;
        }

        public void setBtnPositive(@NonNull String title, @NonNull View.OnClickListener listener) {
            this.btnPositiveTitle = title;
            this.btnPositiveListener = listener;
        }

        public void setBtnThird(@NonNull String title, @NonNull View.OnClickListener listener) {
            this.btnThirdTitle = title;
            this.btnThirdListener = listener;
        }

        public void setCheckBox(@NonNull String title, @NonNull CheckBox.OnCheckedChangeListener listener) {
            this.chkTitle = title;
            this.chkChangeListener = listener;
        }

        public String getBtnNegativeTitle() {
            return btnNegativeTitle;
        }

        public View.OnClickListener getBtnNegativeListener() {
            return btnNegativeListener;
        }

        public String getBtnPositiveTitle() {
            return btnPositiveTitle;
        }

        public View.OnClickListener getBtnPositiveListener() {
            return btnPositiveListener;
        }

        public String getBtnThirdTitle() {
            return btnThirdTitle;
        }

        public View.OnClickListener getBtnThirdListener() {
            return btnThirdListener;
        }

        public String getChkTitle() {
            return chkTitle;
        }

        public CheckBox.OnCheckedChangeListener getChkChangeListener() {
            return chkChangeListener;
        }

        public AlertDialog build() {
            this.zarinAlertDialog = new AlertDialog(this.fm, this);
            return this.zarinAlertDialog;

        }

        @Nullable
        public AlertDialog getDialog() {
            return this.zarinAlertDialog;
        }
    }
}
