# ZHImageCropApp
头像、封面裁剪上传
引用：compile 'com.github.ZHBoy:ZHImageCropApp:v1.0.2'

调用代码：
```
public class MainActivity extends AppCompatActivity {

    ImageView cropImage;
    ImageSelectPopupWindow imageSelectPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPopupWindow();
        cropImage = findViewById(R.id.cropImage);
        cropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelectPopupWindow.showPop(((ViewGroup)findViewById(android.R.id.content)).getChildAt(0));
            }
        });
    }

    private void initPopupWindow() {
        imageSelectPopupWindow = new ImageSelectPopupWindow(this,null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CropUtils.CHOOSE_IMAGE:
                    CropUtils.startPhotoZoom(this,data.getData());
                    break;
                case CropUtils.PICK_CAMERA:
                    CropUtils.startPhotoZoom(this,CropUtils.imageUri);
                    break;
                case CropUtils.CROP_IMAGE: // 取得裁剪后的图片
                    cropImage.setImageBitmap(CropUtils.setImageToView(data));
//                    CropUtils.getByte(data);//获取裁剪后byte数组
                    break;
                default:
                    break;
            }
        }
    }
}
```
