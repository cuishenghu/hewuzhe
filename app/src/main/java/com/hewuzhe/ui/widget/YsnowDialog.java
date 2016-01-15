package com.hewuzhe.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.FileUtil;


public class YsnowDialog {

    public final TextView tvTitle;
    public final TextView tvTakePic;
    public final TextView tvChoosePic;
    public final TextView tvCancel;
    private final Activity context;
    public Dialog mDialog;

    public YsnowDialog(final Activity context) {

        this.context = context;

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.capture_img_view, null);

        mDialog = new Dialog(context, R.style.dialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.setContentView(layout);

        Window win = mDialog.getWindow();
        win.getDecorView().setPadding(20, 0, 20, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.dialogAnim;
        win.setAttributes(lp);

        mDialog.setCanceledOnTouchOutside(true);


        tvTitle = (TextView) layout.findViewById(R.id.tv_title);
        tvTakePic = (TextView) layout.findViewById(R.id.tv_take_pic);
        tvChoosePic = (TextView) layout.findViewById(R.id.tv_choose_pic);
        tvCancel = (TextView) layout.findViewById(R.id.tv_cancel);


        tvTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri = Uri.fromFile(FileUtil.getTempImage());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                context.startActivityForResult(intent, C.TAKE_PIC);
            }
        });

        tvChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                photoPickerIntent.putExtra("crop", "true");
                photoPickerIntent.putExtra("aspectX", 4);
                photoPickerIntent.putExtra("aspectY", 4);
                photoPickerIntent.putExtra("outputX", 150);
                photoPickerIntent.putExtra("outputY", 150);
                photoPickerIntent.putExtra("scale", true);
                photoPickerIntent.putExtra("circleCrop", true);
                photoPickerIntent.putExtra("return-data", true);
                context.startActivityForResult(photoPickerIntent, C.CHOOSE_PIC);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    public void goToCrop(int code) {
        //拍照
        Uri picUri = Uri.fromFile(FileUtil.getTempImage());
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(picUri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 4);
        cropIntent.putExtra("aspectY", 4);
        cropIntent.putExtra("outputX", 150);
        cropIntent.putExtra("outputY", 150);
        cropIntent.putExtra("return-data", true);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("circleCrop", true);
        context.startActivityForResult(cropIntent, code);

    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}
