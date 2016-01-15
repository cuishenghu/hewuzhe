package com.hewuzhe.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hewuzhe.R;


public class YsnowEditDialog {

    public EditText content;
    private Dialog mDialog;
    private TextView dialog_title;
    private TextView dialog_message;
    public TextView positive;
    public TextView negative;


    public YsnowEditDialog(Context context, String title, String message) {


        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.dialog_edit, null);

        mDialog = new Dialog(context, R.style.dialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.setContentView(layout);

        Window win = mDialog.getWindow();
        win.getDecorView().setPadding(20, 0, 20, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.dialogAnim;
        win.setAttributes(lp);

        mDialog.setCanceledOnTouchOutside(true);


        positive = (TextView) layout.findViewById(R.id.choosePhotoTv);
        negative = (TextView) layout.findViewById(R.id.cancelTv);
        content = (EditText) layout.findViewById(R.id.edt_content);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}
