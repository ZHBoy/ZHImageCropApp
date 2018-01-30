package com.example.library;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by hao on 2018/1/30.
 */

public class CropUtils {
    public static final int CHOOSE_IMAGE = 1;//选择本地图片
    public static final int CROP_IMAGE = 2;//裁剪图片
    public static final int PICK_CAMERA = 3;//拍照

    public static File file = new File(Environment
            .getExternalStorageDirectory(), "cropImage.jpg");

    //图片的Uri
    public static Uri imageUri =   Uri.fromFile(file);

    /**
     * 裁剪
     * @param mContext fragment
     * @param uri
     */
    public static void startPhotoZoom(Fragment mContext, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);

        // aspectX aspectY 是宽高的比例，正方形效果
        intent.putExtra("aspectX", 100);
        intent.putExtra("aspectY", 99);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);

        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        mContext.startActivityForResult(intent, CROP_IMAGE);
    }
    /**
     * 裁剪
     * @param mContext activity
     * @param uri
     */
    public static void startPhotoZoom(Activity mContext, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);

        // aspectX aspectY 是宽高的比例，正方形效果
        intent.putExtra("aspectX", 100);
        intent.putExtra("aspectY", 99);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);

        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        mContext.startActivityForResult(intent, CROP_IMAGE);
    }

    /**
     * image绑定
     */
    public static Bitmap setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap mBitmap = extras.getParcelable("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return mBitmap;
        }
        return null;
    }

    /**
     * 获取byte数组，用于上传服务器，如七牛
     * @param data
     */
    public static byte[]  getByte(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap mBitmap = extras.getParcelable("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] datas = baos.toByteArray();//

//            new UploadManager().put(datas, String.valueOf(System.currentTimeMillis()),(String) o,
//                    new UpCompletionHandler() {
//                        @Override
//                        public void complete(String key, ResponseInfo info, JSONObject res) {
//                            if(info.isOK()) {
                                    //上传成功
//                            } else {
                                    //上传失败
//                            }
//                        }
//                    }, null);
            return datas;
        }
        return null;
    }
}
