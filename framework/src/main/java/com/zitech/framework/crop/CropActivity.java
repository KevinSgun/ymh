package com.zitech.framework.crop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.zitech.framework.BaseApplication;
import com.zitech.framework.utils.FileDiskAllocator;
import com.zitech.framework.utils.ImageUtils;
import com.zitech.framework.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;

public class CropActivity extends Activity implements View.OnClickListener {

	private static final int IMAGE_PREVIEW_SIZE = 800;
	private View mDone, mCancel, mLoadPhoto, mRotate, mMirror;
	private CropImageView mCropView;

	private float mRatio = 1;
	private boolean mOnShow = false;
	private LoadPicTask mLoadPicTask;

	private ProgressDialog mProgressD;
	private String picPath;
	private int quitity = ImageUtils.QUALITY_NOT_WIFI;
	private int maxSize = ImageUtils.MAX_LIMIT;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.zitech.framework.R.layout.photo_crop);
		mDone = findViewById(com.zitech.framework.R.id.photo_crop_done);
		mCancel = findViewById(com.zitech.framework.R.id.photo_crop_cancel);
		mLoadPhoto = findViewById(com.zitech.framework.R.id.photo_crop_load);
		mRotate = findViewById(com.zitech.framework.R.id.photo_crop_rotate);
		mMirror = findViewById(com.zitech.framework.R.id.photo_crop_mirror);
		mCropView = (CropImageView) findViewById(com.zitech.framework.R.id.photo_crop_main);
		mDone.setOnClickListener(this);
		mCancel.setOnClickListener(this);
		mLoadPhoto.setOnClickListener(this);
		mRotate.setOnClickListener(this);
		mMirror.setOnClickListener(this);

		Bundle bundle = getIntent().getExtras();
		Uri uri = getIntent().getData();
		mRatio = bundle.getFloat("ratio");
		int quitity = bundle.getInt("quitity");
		int maxSize = bundle.getInt("limit");
		if (quitity > 0)
			this.quitity = quitity;
		if (maxSize > 0)
			this.maxSize = maxSize;
		picPath = Utils.getPathFromUri(CropActivity.this, uri);// (uri);
		if (picPath != null) {
			mLoadPicTask = new LoadPicTask();
			mLoadPicTask.execute(picPath);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == com.zitech.framework.R.id.photo_crop_done) {
			Intent i = new Intent();
			// i.putExtra("image_uri", mImageUri);
			File dir = FileDiskAllocator.creatCropDir(this, String.valueOf(System.currentTimeMillis()));
			File file = new File(dir, System.currentTimeMillis() + "_crop.jpg");
			if (file.exists())
				file.delete();
			Bitmap cropped = mCropView.getActualCroppedImage();
			if (cropped != null) {
				String path = ImageUtils.compressNoLargePhoto(cropped, file, quitity, maxSize, 0);
				i.putExtra("data", path);
				i.putExtra("src", picPath);
				setResult(Activity.RESULT_OK, i);
			} else {
				Toast.makeText(BaseApplication.getInstance(),"剪切失败了...换张图片试试吧!",Toast.LENGTH_SHORT).show();
				setResult(Activity.RESULT_CANCELED);
			}
			finish();
		} else if (id == com.zitech.framework.R.id.photo_crop_cancel) {
			finish();
		} else if (id == com.zitech.framework.R.id.photo_crop_load) {
		} else if (id == com.zitech.framework.R.id.photo_crop_rotate) {
			if (mOnShow) {
				mCropView.rotateImage(90);
			}
		} else if (id == com.zitech.framework.R.id.photo_crop_mirror) {
			if (mOnShow) {
				mCropView.mirrorImage();
			}
		}
	}

	private class LoadPicTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			showProgress("图片裁剪中...");
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			String path = params[0];
			return ImageUtils.readFileBitmap(new File(path), IMAGE_PREVIEW_SIZE);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			dismissProgress();
			if (result != null) {
				result.getWidth();
				result.getHeight();
				mCropView.setImageBitmap(result);
				float ratioX = 50f;
				float ratioY = ratioX * mRatio;
				mCropView.setAspectRatio(ratioX, ratioY);
				mOnShow = true;
			}
		}

		@Override
		protected void onCancelled() {
			dismissProgress();
		}

	}

	@SuppressWarnings("unused")
	private String saveCropBitmap(Bitmap bitmap) throws Exception {
		String dirName;
		if (FileDiskAllocator.checkSdCardAvailable()) {
			String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
			dirName = rootDir + File.separator + "DCIM" + File.separator + "vimi";
		} else {
			String rootDir = BaseApplication.getInstance().getCacheDir().getAbsolutePath();
			dirName = rootDir + File.separator + "temp";
		}
		File dir = new File(dirName);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
		String picName = "vimi-" + String.valueOf(System.currentTimeMillis()) + ".jpg";
		File picFile = new File(dir, picName);
		if (picFile.exists()) {
			picFile.delete();
		}
		FileOutputStream out = new FileOutputStream(picFile);
		bitmap.compress(CompressFormat.JPEG, 90, out);
		out.flush();
		out.close();
		return picFile.getAbsolutePath();
	}

	public final void showProgress(String message) {
		if (mProgressD == null) {
			mProgressD = new ProgressDialog(this);
			mProgressD.setIndeterminate(true);
			mProgressD.setCancelable(true);
		}
		mProgressD.setMessage(message);
		mProgressD.show();
	}

	public final void dismissProgress() {
		if (mProgressD != null && mProgressD.isShowing()) {
			mProgressD.dismiss();
		}
	}

}
