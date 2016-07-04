package cn.kuailaimei.client.common.ui;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import cn.kuailaimei.client.BeautApplication;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.utils.ToastMaster;
import com.zitech.framework.crop.CropActivity;
import com.zitech.framework.utils.ImageUtils;
import com.zitech.framework.utils.Utils;
import com.zitech.framework.widget.ActionSheet;
import com.zitech.framework.widget.ActionSheetHelper;

import java.io.File;

/**
 * 选取照片的基础类
 *
 * @author daiqian
 * @since 1.0
 */
public abstract class PhotoPickingActivity extends AppBarActivity {
    private static final String TOOK_PHOTO_NAME = "_temp.jpg";
    private static final String COMPRESSED_PHOTO_NAME = "_saved.jpg";
    //
    protected static final int TAKE_PHOTO = 9997;
    protected static final int SELECT_FROM_ALBUMS = 9998;
    protected static final int CROP_PIC = 9999;
    //
    public static final int EFFECT_TYPE_CUT = 1;
    public static final int EFFECT_TYPE_NONE = 0;
    private int effecType;
    private float ratio = 1f;

    public void requestTakePhoto(String tilte, int effecType, float ratio) {
        this.ratio = ratio;
        requestTakePhoto(tilte, effecType);
    }

    public void requestTakePhoto(String tilte, int effecType) {
        this.effecType = effecType;
        initDialogMenu(tilte);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                File photoTookedPath = getPhotoTookPath();
                if (photoTookedPath != null && photoTookedPath.exists()) {
                    if (effecType == EFFECT_TYPE_CUT) {
                        cutPhoto(Uri.fromFile(photoTookedPath), ImageUtils.MAX_LIMIT);
                    } else {
                        String picturePath = ImageUtils.compressNoLargePhoto(this, photoTookedPath, getCompressedPhotoPath(),
                                true, ImageUtils.MAX_LIMIT);
                        onPhotoTaked(picturePath);
                    }
                }
            } else if (requestCode == SELECT_FROM_ALBUMS) {
                try {
                    if (data == null) {
                        return;
                    }
                    Uri uri = data.getData();
                    if (EFFECT_TYPE_CUT == effecType) {
                        cutPhoto(uri, ImageUtils.MAX_LIMIT);
                    } else {
                        String photoTookPath = Utils.getPathFromUri(this, uri);
                        String picturePath = ImageUtils.compressNoLargePhoto(this, new File(photoTookPath),
                                getCompressedPhotoPath(), true, ImageUtils.MAX_LIMIT);
                        onPhotoTaked(picturePath);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CROP_PIC) {
                if (data == null || data.getExtras() == null) {
                    return;
                }
                String picturePath = ImageUtils.compressNoLargePhoto(this, new File(data.getExtras().getString("src")),
                        getCompressedPhotoPath(), true, ImageUtils.MAX_LIMIT);
                onPhotoCut(picturePath, data.getExtras().getString("data"));
            }
        }

    }

    public void cutPhoto(Uri uri) {
        Intent intent = new Intent(this, CropActivity.class);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("ratio", ratio);
        startActivityForResult(intent, CROP_PIC);

    }

    public void onItemClick(int itemId) {
        switch (itemId) {
            case 1:
                selectFromAlbum();
                break;
            case 0:
                takePhoto();
                break;

            default:
                break;
        }
    }

    protected void onPhotoTaked(String picturePath) {

    }

    protected void onPhotoCut(String picturePath, String cutPicturePath) {

    }

    @SuppressWarnings("deprecation")
    private void initDialogMenu(String title) {
        final ActionSheet sheet = (ActionSheet) ActionSheetHelper.createDialog(this, null);
        String[] items = null;
        items = getResources().getStringArray(R.array.detail_camer_pic);
        sheet.addButton(items[0], ActionSheet.WHITE_STYLE_BTN);
        sheet.addButton(items[1], ActionSheet.WHITE_STYLE_BTN);
        sheet.setMainTitle(title);
        sheet.setOnButtonClickListener(new ActionSheet.OnButtonClickListener() {

            @Override
            public void OnClick(View clickedView, int which) {
                // TODO Auto-generated method stub
                onItemClick(which);
                sheet.dismiss();
            }
        });
        sheet.addCancelButton(items[2]);
        sheet.show();
    }

    public void takePhoto() {
        if (Utils.checkSdCardAvailable()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File tookPath = getPhotoTookPath();
            if (tookPath.exists())
                tookPath.delete();
            Uri outputFileUri = Uri.fromFile(tookPath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, TAKE_PHOTO);
            }
        } else {
            ToastMaster.shortToast("请插入SD卡");
        }
    }

    public void selectFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, SELECT_FROM_ALBUMS);
        }
    }

    private File getPhotoTookPath() {
        return Utils.getDiskCachePath(TOOK_PHOTO_NAME);
    }

    private File getCompressedPhotoPath() {
        String userId = BeautApplication.getInstance().getUser().getMobile();
        if (userId == null) {
            userId = "DateFeelingPhoto_";
        } else {
            userId = (userId + "_");
        }
        return Utils.getDiskCachePath(userId + System.currentTimeMillis() + COMPRESSED_PHOTO_NAME);
    }

    public void cutPhoto(Uri uri, int limit) {
        Intent intent = new Intent(this, CropActivity.class);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("ratio", ratio);
        intent.putExtra("limit", limit);
        startActivityForResult(intent, CROP_PIC);

    }

}
