package com.example.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
* @作者 hao
* @创建日期 2018/1/30 14:04
* Description: pop弹框展示
*/

public class ImageSelectPopupWindow extends PopupWindow {

    TextView select_local,select_camera,cancel;
    private View mPopView;

    private Activity mContext;
    private Fragment fragment;

    public ImageSelectPopupWindow(Activity context, Fragment fragment) {
        super(context);
        this.mContext = context;
        this.fragment = fragment;
        init();
        setPopupWindow();
    }

    /**
     * 初始化
     *
     */
    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //绑定布局
        mPopView = inflater.inflate(R.layout.image_choose_pw, null);
        select_local = mPopView.findViewById(R.id.select_local);
        select_camera = mPopView.findViewById(R.id.select_camera);
        cancel = mPopView.findViewById(R.id.cancel);
        select_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromLocal();
                dismiss();
            }
        });

        select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicFromCamera();
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mPopView.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    /**
     * 展示pop
     * @param view
     */
    public void showPop(View view){
        if (isShowing()) return;
        this.showAtLocation(view, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        setBackgroundAlpha(0.5f);
    }

    /**
     * 选择本地图片
     */
    private void selectFromLocal() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (fragment != null){
            fragment.startActivityForResult(intent, CropUtils.CHOOSE_IMAGE);
            return;
        }
        mContext.startActivityForResult(intent, CropUtils.CHOOSE_IMAGE);
    }

    /**
     * 拍照
     */
    private void selectPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, CropUtils.imageUri);
        if (fragment != null){
            fragment.startActivityForResult(intent, CropUtils.PICK_CAMERA);
            return;
        }
        mContext.startActivityForResult(intent, CropUtils.PICK_CAMERA);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param alpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    private void setBackgroundAlpha( float alpha) {
        if (alpha < 0 || alpha > 1) return;
        WindowManager.LayoutParams windowLP = mContext.getWindow().getAttributes();
        windowLP.alpha = alpha;
        if (alpha == 1) {
            mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        mContext.getWindow().setAttributes(windowLP);
    }
}
