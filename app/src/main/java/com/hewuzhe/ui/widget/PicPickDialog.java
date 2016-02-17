package com.hewuzhe.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.utils.GlideLoader;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;

import java.util.ArrayList;

import imagechooser.api.ChooserType;
import imagechooser.api.ImageChooserListener;
import imagechooser.api.ImageChooserManager;


public class PicPickDialog {

    public final TextView tvTitle;
    public final TextView tvTakePic;
    public final TextView tvChoosePic;
    public final TextView tvCancel;
    private final Activity context;
    public Dialog mDialog;
    private int chooserType;
    public ImageChooserManager imageChooserManager;
    public String filePath;
    private ImageConfig imageConfig;

    public PicPickDialog(Activity context) {

        this.context = context;

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.view_pic_pick, null);

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
            public void onClick(View view) {
                takePicture();
            }
        });
        tvChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    private void chooseImage() {
//        chooserType = ChooserType.REQUEST_PICK_PICTURE;
//        imageChooserManager = new ImageChooserManager(context,
//                ChooserType.REQUEST_PICK_PICTURE, true);
//        imageChooserManager.setImageChooserListener((ImageChooserListener) context);
//        imageChooserManager.clearOldFiles();
//        try {
////            pbar.setVisibility(View.VISIBLE);
//            filePath = imageChooserManager.choose();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(context.getResources().getColor(R.color.colorPrimary))
                .titleBgColor(context.getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(context.getResources().getColor(R.color.white))
                .titleTextColor(context.getResources().getColor(R.color.white))
                        // 开启多选   （默认为多选）
                .mutiSelect()
                        // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(9)
                        // 开启拍照功能 （默认关闭）
                .showCamera()
                        // 已选择的图片路径
                .pathList(new ArrayList<String>())
                        // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();

        ImageSelector.open(context, imageConfig);   // 开启图片选择器
        dismiss();
    }


    private void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(context,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener((ImageChooserListener) context);
        try {
//            pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismiss();
    }


    // Should be called if for some reason the ImageChooserManager is null (Due
    // to destroying of activity for low memory situations)
    public void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(context, chooserType, true);
        imageChooserManager.setImageChooserListener((ImageChooserListener) context);
        imageChooserManager.reinitialize(filePath);
    }


    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}
