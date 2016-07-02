package com.zitech.framework.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

import com.zitech.framework.BaseApplication;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SyncFailedException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@SuppressLint("NewApi")
public class ImageUtils {

	private static final int APP_DEFAULT_DENSITY = DisplayMetrics.DENSITY_XHIGH;

	public static final int QUALITY_NOT_WIFI = 80;

	public static final int QUALITY_WIFI = 80;
	// 最大剪切大小
	public static int CUT_PICTURE_SIZE = 350;
	// 相册小图默认大小
	public static int ALBUMDEFSIZE = 200;
	// fans最大图片
	public static int MAX_LIMIT = 1200;

	public static Bitmap viewToBitmap(View view, int bitmapWidth, int bitmapHeight) {
		Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Config.ARGB_8888);
		view.draw(new Canvas(bitmap));

		return bitmap;
	}

	public static Bitmap getViewBitmap(View v) {
		v.clearFocus();
		v.setPressed(false);
		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);
		// Reset the drawing cache background color to fully transparent
		// for the duration of this operation
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);
		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null || cacheBitmap.isRecycled()) {
			Log.e("TTTTTTTTActivity", "failed getViewBitmap(" + v + ")", new RuntimeException());
			return viewToBitmap(v, v.getWidth(), v.getHeight());
		}

		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);

		return bitmap;
	}

	/**
	 * 从文件解析出Bitmap格式的图片
	 *
	 * @param path
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public static Bitmap decodeFile(String path, int maxWidth, int maxHeight, Config config) {
		try {
			Options options = new Options();
			options.inDensity = APP_DEFAULT_DENSITY;// 320dip 160dpi
			options.inTargetDensity = BaseApplication.getInstance().getResources().getDisplayMetrics().densityDpi;
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);
			FileInputStream fInputStream = new FileInputStream(path);
			return decode(fInputStream, maxWidth, maxHeight, options, config);
		} catch (Exception e) {
			return null;
		}
	}

	public static Bitmap decodeFile(String path, int maxWidth, int maxHeight) {
		try {
			Options options = new Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);
			FileInputStream fInputStream = new FileInputStream(path);
			return decode(fInputStream, maxWidth, maxHeight, options, null);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/**
	 * 读取图片属性：旋转的角度
	 *
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static Bitmap decodeFromResources(Context c, int id, int maxWidth, int maxHeight, Config config) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(c.getResources(), id, options);
		return decode(c.getResources().openRawResource(id), maxWidth, maxHeight, options, config);
	}

	public static Bitmap decodeFromResources(Context c, int id, int maxWidth, int maxHeight) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(c.getResources(), id, options);
		return decode(c.getResources().openRawResource(id), maxWidth, maxHeight, options, null);
	}

	public static Bitmap decodeFile(String path) {
		return decodeFile(path, 0, 0, null);
	}

	public static Bitmap decodeFile(String path, int inSimpleSize) {
		// return decodeFile(path, 0, 0, null);
		Options options = new Options();
		options.inSampleSize = inSimpleSize;
		options.inJustDecodeBounds = false;
		// if (config != null)
		options.inPreferredConfig = Config.RGB_565;// Bitmap.Config.ARGB_4444;
		options.inPurgeable = true;
		options.inInputShareable = true;
		return BitmapFactory.decodeFile(path, options);
	}

	public static Bitmap decodeFile(String path, Config config) {
		return decodeFile(path, 0, 0, config);
	}

	public static Bitmap decodeFromResources(Context c, int id) {
		return decodeFromResources(c, id, 0, 0, null);
	}

	public static Bitmap decodeFromResources(Context c, int id, Config config) {
		return decodeFromResources(c, id, 0, 0, config);
	}

	private static Bitmap decode(InputStream in, int maxWidth, int maxHeight, Options options, Config config) {
		double ratio = 1D;
		if (maxWidth > 0 && maxHeight <= 0) {
			// 限定宽度，高度不做限制
			ratio = Math.ceil(options.outWidth / maxWidth);
		} else if (maxHeight > 0 && maxWidth <= 0) {
			// 限定高度，不限制宽度
			ratio = Math.ceil(options.outHeight / maxHeight);
		} else if (maxWidth > 0 && maxHeight > 0) {
			// 高度和宽度都做了限制，这时候我们计算在这个限制内能容纳的最大的图片尺寸，不会使图片变形
			double widthRatio = Math.ceil(options.outWidth / maxWidth);
			double heightRatio = (double) Math.ceil(options.outHeight / maxHeight);
			ratio = widthRatio > heightRatio ? widthRatio : heightRatio;
		}
		if (ratio > 1) {
			options.inSampleSize = (int) Math.round(ratio);
		}
		options.inJustDecodeBounds = false;
		// if (config != null)
		options.inDensity = APP_DEFAULT_DENSITY;
		options.inTargetDensity = BaseApplication.getInstance().getResources().getDisplayMetrics().densityDpi;
		options.inPreferredConfig = config != null ? config : Config.RGB_565;// Bitmap.Config.ARGB_4444;
		/*
		 * 如果 inPurgeable
		 * 设为True的话表示使用BitmapFactory创建的Bitmap用于存储Pixel的内存空间在系统内存不足时可以被回收
		 * ，在应用需要再次访问Bitmap的Pixel时（如绘制Bitmap或是调用getPixel），系统会再次调用BitmapFactory
		 * decoder重新生成Bitmap的Pixel数组.为了能够重新解码图像，bitmap要能够访问存储Bitmap的原始数据.
		 * 在inPurgeable为false时表示创建的Bitmap的Pixel内存空间不能被回收
		 * ，这样BitmapFactory在不停decodeByteArray创建新的Bitmap对象
		 * ，不同设备的内存不同，因此能够同时创建的Bitmap个数可能有所不同
		 * ，200个bitmap足以使大部分的设备重新OutOfMemory错误.
		 * 当isPurgable设为true时，系统中内存不足时，可以回收部分Bitmap占据的内存空间，这时一般不会出现OutOfMemory
		 * 错误.
		 */
		options.inPurgeable = true;
		/*
		 * inInputShareable与inPurgeable一起使用，如果inPurgeable为false那该设置将被忽略，如果为true，
		 * 那么它可以决定位图是否能够共享一个指向数据源的引用，或者是进行一份拷贝；
		 */
		options.inInputShareable = true;
		Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);
		// System.out.println("old width:" + options.outWidth + ",old height:" +
		// options.outHeight + ", new width :"
		// + bitmap.getWidth() + ",new height:" + bitmap.getHeight());
		return bitmap;
	}

	// 放大缩小图片
	public static Bitmap zoomBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		if (width <= 0 || height <= 0)
			return bitmap;
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) maxWidth / width);
		float scaleHeight = ((float) maxHeight / height);

		float scale = 1f;
		if (scaleHeight <= 0) {
			scale = scaleWidth;
		} else if (scaleWidth <= 0) {
			scale = scaleHeight;
		} else
			scale = Math.min(scaleWidth, scaleHeight);
		matrix.postScale(scale, scale);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return newbmp;
	}

	// public static Bitmap rotateBitmap(Bitmap bitmap, int degree) {
	// Matrix m = new Matrix();
	// m.setRotate(degree); // 旋转angle度
	// return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
	// bitmap.getHeight(), m, true);// 从新生成图片
	// }

	public static String saveToLocal(String path, Bitmap bm) {
		/* String path = "/sdcard/mm.jpg"; */
		try {
			FileOutputStream fos = new FileOutputStream(path);
			bm.compress(CompressFormat.PNG, 90, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return path;
	}

	/**
	 * 生成缩略图
	 *
	 * @param imagePath
	 *
	 * @param width
	 *
	 * @param height
	 *
	 * @return
	 */
	public static Bitmap getThumbnailImage(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		Options options = new Options();
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // ��Ϊ false
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 根据sdcard的路径获取一张图片
	 */
	public static Bitmap getResizedBitmap(final String imagePath, int desiredWidth) {
		Bitmap result = null;
		try {
			result = getTemplateBitmap(new DecodeBitmap() {
				@Override
				public Bitmap decode(Options options, boolean decodeBounds) throws Exception {
					if (decodeBounds) {
						BitmapFactory.decodeFile(imagePath, options);
						return null;
					} else {
						Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
						return bitmap;
					}
				}
			}, desiredWidth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据URI的方式读取相册图片
	 */
	public static Bitmap getResizedBitmap(final Uri uri, int desiredWidth) {
		final ContentResolver cr = BaseApplication.getInstance().getContentResolver();

		Bitmap result = null;
		try {
			result = getTemplateBitmap(new DecodeBitmap() {
				@Override
				public Bitmap decode(Options options, boolean decodeBounds) throws Exception {
					if (decodeBounds) {
						InputStream is = cr.openInputStream(uri);
						BitmapFactory.decodeStream(is, null, options);
						return null;
					} else {
						InputStream is = cr.openInputStream(uri);
						Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
						is.close();
						return bitmap;
					}
				}
			}, desiredWidth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据URL的方式读取网络上的图片
	 */
	public static Bitmap getResizedBitmap(final URL url, int desiredWidth) {
		Bitmap result = null;
		try {
			result = getTemplateBitmap(new DecodeBitmap() {
				@Override
				public Bitmap decode(Options options, boolean decodeBounds) throws Exception {
					if (decodeBounds) {
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setDoInput(true);
						conn.setConnectTimeout(1000 * 6);
						conn.connect();
						InputStream is = conn.getInputStream();
						BitmapFactory.decodeStream(is, null, options);
						conn.disconnect();
						return null;
					} else {
						Bitmap bitmap = null;
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setDoInput(true);
						conn.setConnectTimeout(1000 * 6);
						conn.connect();
						InputStream is = conn.getInputStream();
						int length = (int) conn.getContentLength();
						if (length != -1) {
							byte[] imgData = new byte[length];
							byte[] temp = new byte[512];
							int readLen = 0;
							int destPos = 0;
							while ((readLen = is.read(temp)) > 0) {
								System.arraycopy(temp, 0, imgData, destPos, readLen);
								destPos += readLen;
							}
							bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length, options);
						}
						is.close();
						conn.disconnect();
						return bitmap;
					}
				}
			}, desiredWidth);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;
	}

	private static Bitmap getTemplateBitmap(DecodeBitmap decodeBitmap, int desiredWidth) throws Exception {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		decodeBitmap.decode(options, true);
		// The actual width of the image.
		int srcWidth = options.outWidth;
		int inSampleSize = 1;
		// 期望宽必须大于0才能去计算自适应宽度,否则默认大小
		if (desiredWidth > 0) {
			// The actual height of the image.
			if (desiredWidth > srcWidth)
				desiredWidth = srcWidth;
			// Calculate the correct inSampleSize/scale value. This helps reduce
			// memory use. It should be a power of 2.
			while (srcWidth / 2 > desiredWidth) {
				srcWidth /= 2;
				inSampleSize *= 2;
			}
		}
		// 大于1就需要做图片缩小
		if (inSampleSize > 1) {
			float desiredScale = (float) desiredWidth / srcWidth;
			// Decode with inSampleSize
			options.inJustDecodeBounds = false;
			options.inDither = false;
			options.inSampleSize = inSampleSize;
			options.inScaled = false;

			// Ensures the image stays as a 32-bit ARGB_8888 image.
			// This preserves image quality.
			options.inPreferredConfig = getPreferredConfig(options.inPreferredConfig);
			Bitmap sampledSrcBitmap = null;
			sampledSrcBitmap = decodeBitmap.decode(options, false);

			Matrix matrix = new Matrix();
			matrix.postScale(desiredScale, desiredScale);
			Bitmap scaledBitmap = Bitmap.createBitmap(sampledSrcBitmap, 0, 0, sampledSrcBitmap.getWidth(),
					sampledSrcBitmap.getHeight(), matrix, true);
			sampledSrcBitmap = null;
			return scaledBitmap;
		} else {
			options.inJustDecodeBounds = false;
			options.inSampleSize = inSampleSize;
			// Ensures the image stays as a 32-bit ARGB_8888 image.
			// This preserves image quality.
			options.inPreferredConfig = getPreferredConfig(options.inPreferredConfig);
			Bitmap sampledSrcBitmap = null;
			sampledSrcBitmap = decodeBitmap.decode(options, false);
			return sampledSrcBitmap;
		}
	}

	private static Config getPreferredConfig(Config res) {
		if (res == Config.RGB_565 || res == Config.ARGB_8888) {
			return res;
		} else {
			return Config.ARGB_8888;
		}
	}

	interface DecodeBitmap {
		/**
		 * 有2中情况，如果decodeBounds等于true, 那么只是取图片的宽而已，如果为false才去解码图片<br>
		 *
		 * @param options
		 * @param decodeBounds
		 *            是否解码图片
		 */
		Bitmap decode(Options options, boolean decodeBounds) throws Exception;
	}

	/**
	 * 转换图片成圆形
	 *
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);

		return output;
	}

	/*
	 * bitmap 转byte[]
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 裁剪图片的方法
	 *
	 * @param uri
	 */
	public static void startPicCut(Activity activity, Uri uri) {
		Intent intentCarema = new Intent("com.android.camera.action.CROP");
		intentCarema.setDataAndType(uri, "image/*");
		intentCarema.putExtra("crop", true);
		// intentCarema.putExtra("scale", false);
		// intentCarema.putExtra("noFaceDetection", true);//不需要人脸识别功能
		// intentCarema.putExtra("circleCrop", "");//设定此方法选定区域会是圆形区域
		// aspectX aspectY是宽高比例
		intentCarema.putExtra("aspectX", 1);
		intentCarema.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片的宽高
		intentCarema.putExtra("outputX", 150);
		intentCarema.putExtra("outputY", 150);
		intentCarema.putExtra("return-data", true);
		activity.startActivityForResult(intentCarema, 3);
	}

	// 图片灰度处理

	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	private static ColorMatrix mLightnessMatrix;
	private static ColorMatrix mSaturationMatrix;
	private static ColorMatrix mHueMatrix;
	private static ColorMatrix mAllMatrix;

	/**
	 *
	 * @param flag
	 *            比特位0 表示是否改变色相，比位1表示是否改变饱和度,比特位2表示是否改变明亮度
	 */
	public static Bitmap handleImage(Bitmap bm, int flag) {

		// 如果这些特效 没有你想要的 可以改变 相应的值进行调试

		float mSaturationValue = 0.0F;// 饱和度 0.0 到 2.0
		float mLumValue = 0.0F;// 亮度值 -180 到 180
		float mHueValue = 0.0F;// 色相值 0.0 到 2.0
		switch (flag) {
		case 1:// 黑白
			mSaturationValue = 0.0F;// 饱和度
			mLumValue = 0.0F;// 亮度值
			mHueValue = 1.0F;// 色相值
			break;
		case 2:// 淡黄
			mSaturationValue = 2.0F;// 饱和度
			mLumValue = 0.0F;// 亮度值
			mHueValue = 1.0F;// 色相值
			break;
		case 3:// 蓝色
			mSaturationValue = 0.8F;// 饱和度
			mLumValue = -90.0F;// 亮度值
			mHueValue = 1.2F;// 色相值
			break;
		case 4:// 怀旧
			mSaturationValue = 1.0F;// 饱和度
			mLumValue = 0.0F;// 亮度值
			mHueValue = 1.0F;// 色相值
			break;
		}
		Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Config.ARGB_8888);
		// 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
		Canvas canvas = new Canvas(bmp); // 得到画笔对象
		Paint paint = new Paint(); // 新建paint
		paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理
		if (null == mAllMatrix) {
			mAllMatrix = new ColorMatrix();
		}

		if (null == mLightnessMatrix) {
			mLightnessMatrix = new ColorMatrix(); // 用于颜色变换的矩阵，android位图颜色变化处理主要是靠该对象完成
		}

		if (null == mSaturationMatrix) {
			mSaturationMatrix = new ColorMatrix();
		}

		if (null == mHueMatrix) {
			mHueMatrix = new ColorMatrix();
		}

		// 需要改变色相
		mHueMatrix.reset();
		mHueMatrix.setScale(mHueValue, mHueValue, mHueValue, 1); // 红、绿、蓝三分量按相同的比例,最后一个参数1表示透明度不做变化，此函数详细说明参考

		// 需要改变饱和度
		// saturation 饱和度值，最小可设为0，此时对应的是灰度图(也就是俗话的“黑白图”)，
		// 为1表示饱和度不变，设置大于1，就显示过饱和
		mSaturationMatrix.reset();
		mSaturationMatrix.setSaturation(mSaturationValue);
		// 亮度
		// hueColor就是色轮旋转的角度,正值表示顺时针旋转，负值表示逆时针旋转
		mLightnessMatrix.reset(); // 设为默认值
		mLightnessMatrix.setRotate(0, mLumValue); // 控制让红色区在色轮上旋转的角度
		mLightnessMatrix.setRotate(1, mLumValue); // 控制让绿红色区在色轮上旋转的角度
		mLightnessMatrix.setRotate(2, mLumValue); // 控制让蓝色区在色轮上旋转的角度
		// 这里相当于改变的是全图的色相
		mAllMatrix.reset();
		mAllMatrix.postConcat(mHueMatrix);
		mAllMatrix.postConcat(mSaturationMatrix); // 效果叠加
		mAllMatrix.postConcat(mLightnessMatrix); // 效果叠加

		paint.setColorFilter(new ColorMatrixColorFilter(mAllMatrix));// 设置颜色变换效果
		canvas.drawBitmap(bm, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
		// 返回新的位图，也即调色处理后的图片
		return bmp;
	}

	// private static final String TAG = "ImageUtil";

	public static int computeSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		// 上下限范围
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
				Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static int getRotateDegree(String filePath) {
		int degree = 0;
		Object exif = null;
		try {
			Class<?> clazz = Class.forName("android.media.ExifInterface");
			Class<?>[] argTypes = { String.class };
			Constructor<?> constructor = clazz.getConstructor(argTypes);
			exif = constructor.newInstance(filePath);
		} catch (Exception e) {
		}
		if (exif != null) {
			Method method;
			int orientation = 0;
			try {
				method = exif.getClass().getMethod("getAttributeInt", new Class[] { String.class, Integer.TYPE });
				orientation = (Integer) method.invoke(exif, new Object[] { "Orientation", -1 });
			} catch (Exception e) {
			}
			if (orientation != -1) {
				// We only recognize a subset of orientation tag values.
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				default:
					break;
				}
			}
		}
		return degree;
	}

	public static Bitmap getBitmap(String imgFile, int minSideLength, int maxNumOfPixels) {
		if (imgFile == null || imgFile.length() == 0) {
			return null;
		}
		try {
			FileDescriptor fd = new FileInputStream(imgFile).getFD();
			Options options = new Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFileDescriptor(fd, null, options);
			options.inSampleSize = computeSampleSize(options, minSideLength, maxNumOfPixels);
			try {
				// 这里一定要将其设置回false，因为我们之前将其设置成了true
				// 设置inJustDecodeBounds为true后，decodeFile并不分配空间
				// 即，BitmapFactory解码出来的Bitmap为Null
				// 但可计算出原始图片的长度和宽度
				options.inJustDecodeBounds = false;
				Bitmap bmp = BitmapFactory.decodeFile(imgFile, options);
				return bmp == null ? null : bmp;
			} catch (OutOfMemoryError e) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static void recycleBitmap(Bitmap bm) {
		if (bm != null) {
			bm.recycle();
			bm = null;
			System.gc();
		}
	}

	public static Bitmap zoomPicture(String path, int size) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = computeSampleSize(options, -1, size);
		// Log.d(TAG, options.inSampleSize + "");
		options.inJustDecodeBounds = false;
		try {
			return BitmapFactory.decodeFile(path, options);
		} catch (OutOfMemoryError e) {
			Log.i(TAG, e.toString());
			return null;
		}
	}

	public static Bitmap zoomPictureWidth(String path, int width) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = width / options.outWidth;
		// Log.d(TAG, options.inSampleSize + "");
		options.inJustDecodeBounds = false;
		try {
			return BitmapFactory.decodeFile(path, options);
		} catch (OutOfMemoryError e) {
			Log.i(TAG, e.toString());
			return null;
		}
	}

	public static Bitmap zoomPictureWidthOrHeight(String path, int width, int height) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		int w = options.outWidth;
		int h = options.outHeight;
		float wScale = width / w;
		float hScale = height / h;
		if (wScale >= hScale) {
			options.inSampleSize = (int) wScale;
		} else {
			options.inSampleSize = (int) hScale;
		}
		options.inJustDecodeBounds = false;
		try {
			return BitmapFactory.decodeFile(path, options);
		} catch (OutOfMemoryError e) {
			Log.i(TAG, e.toString());
			return null;
		}
	}

	public static Bitmap convertViewToBitmap(View view) {
		view.setDrawingCacheEnabled(true);
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		return view.getDrawingCache();
	}

	public static boolean bitmapAvailable(Bitmap bitmap) {
		return bitmap != null && bitmap.getWidth() > 0 && bitmap.getHeight() > 0;
	}

	/**
	 * 选择图片
	 *
	 * @param bitmap
	 * @param round
	 *            旋转的角度
	 */
	public static Bitmap rotation(Bitmap bitmap, int round) {
		Matrix matrix = new Matrix();
		matrix.postRotate(round);
		// recreate the new Bitmap
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return bitmap;
	}

	/**
	 * 通过URL获取图片的缩略图
	 *
	 * @param url
	 * @param mContext
	 * @return
	 */
	public static Bitmap getFileThumbnail(String url, Context mContext) {

		Bitmap bitmapOrg = null;
		bitmapOrg = getImageThumbnail(Long.parseLong(url), mContext);
		return bitmapOrg;
	}

	private static Bitmap getImageThumbnail(long id, Context mContext) {
		int MINI_KIND = 3;
		return Images.Thumbnails.getThumbnail(mContext.getContentResolver(), id, MINI_KIND, null);
	}

	public static String compressNoLargePhoto(Context context, final File srcFile, File destFile, boolean rotate, int maxLimit) {
		// destFile = new File(destFile, System.currentTimeMillis() + ".jpg");
		int quality = 100;
		// 质量压缩
		if (NetworkUtil.isWifiEnabled(context)) {
			// reportSendPhoto(REPORT_SENDPHOTO_ISWIFI, context, true);
			quality = QUALITY_WIFI;
		} else {
			quality = QUALITY_NOT_WIFI;
			// reportSendPhoto(REPORT_SENDPHOTO_ISWIFI, context, false);
		}

		// maxLimit = MAX_LIMIT;
		Bitmap bitmapOrg = null;
		// boolean hasOOM = false;
		int degree = 0;
		if (rotate) {
			degree = ImageUtils.readPictureDegree(srcFile.getPath());
		}
		bitmapOrg = getBitmap(context, srcFile, maxLimit);

		return compressNoLargePhoto(bitmapOrg, destFile, quality, maxLimit, degree);

	}

	public static String compressNoLargePhoto(Bitmap bitmapOrg, File destFile, int quality, int maxLimit, int degree) {
		FileOutputStream os = null;
		try {
			if (bitmapOrg == null) {
				destFile.delete();
				return "";
			} else {
				// bitmapOrg = scaleAndRotateBitmap(bitmapOrg,srcFile);
				if (degree == 0) {
					int orgWidth = bitmapOrg.getWidth();
					int orgHeight = bitmapOrg.getHeight();
					if (orgWidth > maxLimit || orgHeight > maxLimit) {
						bitmapOrg = scaleBitmap(bitmapOrg, maxLimit);
					}
				} else {
					bitmapOrg = scaleAndRotateBitmap(bitmapOrg, degree);
				}
				if (bitmapOrg == null) {
					return "";
				}
				// 压图
				try {
					os = new FileOutputStream(destFile);
					boolean compressReulst = bitmapOrg.compress(CompressFormat.JPEG, quality, os);

					if (compressReulst) {
						forceSyncFile(os);
					}
				} catch (IOException e) {
				} finally {
					if (os != null) {
						try {
							os.close();
						} catch (IOException e) {
						}
					}
				}
			}
		} catch (OutOfMemoryError error) {
			// 这里是为了捕获写jpg文件可能产生的oom
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
			// 不做删除，这里也是无用代码，现在每次都重新做压缩。
			// if(hasOOM && destFile.exists()){
			// destFile.delete();
			// }
			if (bitmapOrg != null && !bitmapOrg.isRecycled()) {
				bitmapOrg.recycle();
			}
		}

		return destFile.getPath();
	}

	public static int getQuality(Context context) {
		int quality;
		// 质量压缩
		if (NetworkUtil.isWifiEnabled(context)) {
			// reportSendPhoto(REPORT_SENDPHOTO_ISWIFI, context, true);
			quality = QUALITY_WIFI;
		} else {
			quality = QUALITY_NOT_WIFI;
			// reportSendPhoto(REPORT_SENDPHOTO_ISWIFI, context, false);
		}
		return quality;
	}

	private static final void forceSyncFile(FileOutputStream os) throws IOException, SyncFailedException {
		os.flush();
		FileDescriptor fd = os.getFD();
		if (fd != null && fd.valid()) {
			fd.sync();
		}
	}

	public static String compressImageJpg(String srcPath, String destPath, int maxW, int maxH, int quality) {
		String path = null;
		InputStream is = null;
		try {
			File f = new File(srcPath);
			Options newOpts = getSizeOpt(f, maxW, maxH);
			is = new FileInputStream(f);
			path = CompressJPGFile(is, newOpts, destPath, Math.min(100, quality));
		} catch (Exception e) {
			// android.util.Log.e("compressImage", "compressImageJpg:", e);
			// QLog.d("compressImage", QLog.CLR, "compressImageJpg:", e);
			path = null;
		} catch (Error oom) {
			//
			path = null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// 忽略
				}
			}
		}
		return path;
	}

	private static String CompressJPGFile(InputStream is, Options newOpts, String filePath, int quality) {

		Bitmap destBm = BitmapFactory.decodeStream(is, null, newOpts);
		if (destBm == null) {
			return null;
		} else {
			File destFile = createNewFile(filePath);

			// 创建文件输出流
			OutputStream os = null;
			try {
				os = new FileOutputStream(destFile);
				// 存储
				destBm.compress(CompressFormat.JPEG, Math.min(100, quality), os);
			} catch (Exception e) {
				filePath = null;
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						// 忽略
					}
				}
			}
			return filePath;
		}
	}

	/**
	 * 先压缩图片大小
	 *
	 * @param is
	 * @param maxSize
	 * @return
	 * @throws IOException
	 */
	public static Options getSizeOpt(File file, int maxWidth, int maxHeight) throws IOException {

		// 对图片进行压缩，是在读取的过程中进行压缩，而不是把图片读进了内存再进行压缩
		Options newOpts = new Options();
		InputStream is = new FileInputStream(file);
		double ratio = getOptRatio(is, maxWidth, maxHeight);
		is.close();
		newOpts.inSampleSize = (int) Math.round(ratio);

		newOpts.inJustDecodeBounds = true;

		is = new FileInputStream(file);

		BitmapFactory.decodeStream(is, null, newOpts);
		is.close();
		int loopcnt = 0;
		while (newOpts.outWidth > maxWidth) {
			newOpts.inSampleSize += 1;
			is = new FileInputStream(file);
			BitmapFactory.decodeStream(is, null, newOpts);
			is.close();
			if (loopcnt > 3)
				break;
			loopcnt++;
		}
		// 组合使用下面两个参数
		// 1、在系统需要内存的时候可以回收掉图片占用的内存
		// 2、对已经在内存中的bitmap进行复用
		// newOpts.inInputShareable=true; //[#4802623]点击拍照上传图片，程序crash
		// newOpts.inPurgeable=true;
		// inJustDecodeBounds设为 false表示把图片读进内存中
		newOpts.inJustDecodeBounds = false;
		return newOpts;
	}

	/**
	 * 计算起始压缩比例 先根据实际图片大小估算出最接近目标大小的压缩比例 减少循环压缩的次数
	 *
	 * @param is
	 * @param maxLength
	 * @return
	 */
	public static double getOptRatio(InputStream is, int maxWidth, int maxHeight) {

		Options opts = new Options();
		opts.inJustDecodeBounds = true;

		BitmapFactory.decodeStream(is, null, opts);

		int srcWidth = opts.outWidth;
		int srcHeight = opts.outHeight;
		int destWidth = 0;
		int destHeight = 0;
		// 缩放的比例
		double ratio = 1.0;
		double ratio_w = 0.0;
		double ratio_h = 0.0;

		// 按比例计算缩放后的图片大小，maxLength是长或宽允许的最大长度
		if (srcWidth <= maxWidth && srcHeight <= maxHeight) {
			return ratio; // 小于屏幕尺寸时，不压缩
		}

		if (srcWidth > srcHeight) {
			ratio_w = srcWidth / (double) maxWidth;
			ratio_h = srcHeight / (double) maxHeight;

		} else {
			ratio_w = srcHeight / (double) maxWidth;
			ratio_h = srcWidth / (double) maxHeight;
		}
		if (ratio_w > ratio_h) {
			ratio = ratio_w;
		} else {
			ratio = ratio_h;
		}

		return ratio;
	}

	public static File createNewFile(String filePath) {
		if (filePath == null)
			return null;
		File newFile = new File(filePath);
		try {
			if (!newFile.exists()) {
				int slash = filePath.lastIndexOf('/');
				if (slash > 0 && slash < filePath.length() - 1) {
					String dirPath = filePath.substring(0, slash);
					File destDir = new File(dirPath);
					if (!destDir.exists()) {
						destDir.mkdirs();
					}
				}
			} else {
				newFile.delete();
			}
			newFile.createNewFile();
		} catch (IOException e) {
			return null;
		}
		return newFile;
	}

	public static Bitmap scaleAndRotateBitmap(Bitmap bitmapOrg, int degress) {
		if (bitmapOrg == null) {
			return null;
		}
		Matrix m = new Matrix();
		int bitmapWidth = bitmapOrg.getWidth();
		int bitmapHeight = bitmapOrg.getHeight();
		int max = Math.max(bitmapWidth, bitmapHeight);
		float scale = MAX_LIMIT / (max * 1.0f);
		int rotateDegree = degress;
		Bitmap newBitmap = null;
		try {
			if (scale < 1.0f) {
				m.postScale(scale, scale);
			}
			if (rotateDegree != 0 && 0 == rotateDegree % 90) {
				m.postRotate(rotateDegree, bitmapWidth / 2f, bitmapHeight / 2f);
			}
			newBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapWidth, bitmapHeight, m, true);
		} catch (Exception e) {
		} catch (OutOfMemoryError error) {
		}
		if (newBitmap != null) {
			if (bitmapOrg != null && !bitmapOrg.isRecycled() && !bitmapOrg.equals(newBitmap)) {
				bitmapOrg.recycle();
			}
			return newBitmap;
		} else {
			return bitmapOrg;
		}
	}

	public static Bitmap scaleBitmap(Bitmap bitmapOrg, int max) {
		if (bitmapOrg == null) {
			return null;
		}
		Matrix m = new Matrix();
		int bitmapWidth = bitmapOrg.getWidth();
		int bitmapHeight = bitmapOrg.getHeight();
		float scale = max / (Math.max(bitmapWidth, bitmapHeight) * 1.0f);
		Bitmap newBitmap = null;
		try {
			if (scale != 1.0f && scale > 0) {
				m.postScale(scale, scale);
				newBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapWidth, bitmapHeight, m, true);
			}
		} catch (Exception e) {
		} catch (OutOfMemoryError error) {
		}
		if (newBitmap != null) {
			if (bitmapOrg != null && !bitmapOrg.isRecycled() && !bitmapOrg.equals(newBitmap)) {
				bitmapOrg.recycle();
			}
			return newBitmap;
		} else {
			return bitmapOrg;
		}
	}

	private static Bitmap getBitmap(Context context, final File srcFile, int max) {
		Bitmap bitmapOrg = null;
		Options tempOptions = new Options();
		Options options = calculateInSampleSize(tempOptions, srcFile.getPath(), max);
		boolean hasOOM = false;
		boolean first = true;
		while ((bitmapOrg == null && (options.inSampleSize > 0 && options.inSampleSize <= 4)) || first) {
			if (!first) {
				options.inSampleSize *= 2;
			}
			first = false;
			try {
				bitmapOrg = BitmapFactory.decodeFile(srcFile.getPath(), options);
			} catch (OutOfMemoryError oome) {
				int size = tempOptions.outWidth * tempOptions.outHeight;
				bitmapOrg = null;
				hasOOM = true;
			}
		}
		if (bitmapOrg == null) {
		}
		return bitmapOrg;
	}

	/**
	 * 图像缩到max*max以内
	 *
	 * @param filepath
	 * @param max
	 * @return
	 */
	public static Options calculateInSampleSize(Options options, String filepath, int max) {
		// BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, options);
		// Raw height and width of image
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		while (height > max || width > max) {
			int ratio = Math.round(Math.max(height, width) / (max * 1f));
			if (ratio >= 2) {
				width /= 2;
				height /= 2;
				if (width < max && height < max) {
					options.inSampleSize = inSampleSize;
					break;
					// return inSampleSize;
				} else if (width == max || height == max) {
					options.inSampleSize = inSampleSize * 2;
					break;
					// return inSampleSize * 2;
				}
				inSampleSize *= 2;
			} else {
				options.inSampleSize = inSampleSize;
				break;
			}
		}
		options.inJustDecodeBounds = false;
		options.inSampleSize = options.inSampleSize >= 1 ? options.inSampleSize : 1;
		return options;
	}

	/**
	 * Gets the rectangular position of a Bitmap if it were placed inside a View
	 * with scale type set to {@link ImageView#ScaleType #CENTER_INSIDE}.
	 *
	 * @param bitmap
	 *            the Bitmap
	 * @param view
	 *            the parent View of the Bitmap
	 * @return the rectangular position of the Bitmap
	 */
	public static Rect getBitmapRectCenterInside(Bitmap bitmap, View view) {

		final int bitmapWidth = bitmap.getWidth();
		final int bitmapHeight = bitmap.getHeight();
		final int viewWidth = view.getWidth();
		final int viewHeight = view.getHeight();

		return getBitmapRectCenterInsideHelper(bitmapWidth, bitmapHeight, viewWidth, viewHeight);
	}

	/**
	 * Gets the rectangular position of a Bitmap if it were placed inside a View
	 * with scale type set to {@link ImageView#ScaleType #CENTER_INSIDE}.
	 *
	 * @param bitmapWidth
	 *            the Bitmap's width
	 * @param bitmapHeight
	 *            the Bitmap's height
	 * @param viewWidth
	 *            the parent View's width
	 * @param viewHeight
	 *            the parent View's height
	 * @return the rectangular position of the Bitmap
	 */
	public static Rect getBitmapRectCenterInside(int bitmapWidth, int bitmapHeight, int viewWidth, int viewHeight) {
		return getBitmapRectCenterInsideHelper(bitmapWidth, bitmapHeight, viewWidth, viewHeight);
	}

	/**
	 * Helper that does the work of the above functions. Gets the rectangular
	 * position of a Bitmap if it were placed inside a View with scale type set
	 * to {@link ImageView#ScaleType #CENTER_INSIDE}.
	 *
	 * @param bitmapWidth
	 *            the Bitmap's width
	 * @param bitmapHeight
	 *            the Bitmap's height
	 * @param viewWidth
	 *            the parent View's width
	 * @param viewHeight
	 *            the parent View's height
	 * @return the rectangular position of the Bitmap
	 */
	private static Rect getBitmapRectCenterInsideHelper(int bitmapWidth, int bitmapHeight, int viewWidth, int viewHeight) {
		double resultWidth;
		double resultHeight;
		int resultX;
		int resultY;

		double viewToBitmapWidthRatio = Double.POSITIVE_INFINITY;
		double viewToBitmapHeightRatio = Double.POSITIVE_INFINITY;

		// Checks if either width or height needs to be fixed
		if (viewWidth < bitmapWidth) {
			viewToBitmapWidthRatio = (double) viewWidth / (double) bitmapWidth;
		}
		if (viewHeight < bitmapHeight) {
			viewToBitmapHeightRatio = (double) viewHeight / (double) bitmapHeight;
		}

		// If either needs to be fixed, choose smallest ratio and calculate from
		// there
		if (viewToBitmapWidthRatio != Double.POSITIVE_INFINITY || viewToBitmapHeightRatio != Double.POSITIVE_INFINITY) {
			if (viewToBitmapWidthRatio <= viewToBitmapHeightRatio) {
				resultWidth = viewWidth;
				resultHeight = (bitmapHeight * resultWidth / bitmapWidth);
			} else {
				resultHeight = viewHeight;
				resultWidth = (bitmapWidth * resultHeight / bitmapHeight);
			}
		}
		// Otherwise, the picture is within frame layout bounds. Desired width
		// is simply picture size
		else {
			resultHeight = bitmapHeight;
			resultWidth = bitmapWidth;
		}

		// Calculate the position of the bitmap inside the ImageView.
		if (resultWidth == viewWidth) {
			resultX = 0;
			resultY = (int) Math.round((viewHeight - resultHeight) / 2);
		} else if (resultHeight == viewHeight) {
			resultX = (int) Math.round((viewWidth - resultWidth) / 2);
			resultY = 0;
		} else {
			resultX = (int) Math.round((viewWidth - resultWidth) / 2);
			resultY = (int) Math.round((viewHeight - resultHeight) / 2);
		}

		final Rect result = new Rect(resultX, resultY, resultX + (int) Math.ceil(resultWidth), resultY
				+ (int) Math.ceil(resultHeight));

		return result;
	}

	public static Bitmap readFileBitmap(File file, int reqSize) {
		int orientation = 0;
		try {
			ExifInterface exif = new ExifInterface(file.getAbsolutePath());
			if (exif != null) {
				int o = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
				switch (o) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					orientation = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					orientation = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					orientation = 270;
					break;
				}
			}
		} catch (IOException e) {

		}

		FileInputStream fis = null;
		FileDescriptor fd = null;
		Options options = new Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[32 * 1024];
		options.inPreferredConfig = Config.RGB_565;

		try {
			fis = new FileInputStream(file);
			fd = fis.getFD();

			if (reqSize > 0) {
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeFileDescriptor(fd, null, options);
				options.inSampleSize = scaleSize(options, reqSize, reqSize);
				options.inJustDecodeBounds = false;
			}

			Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd, null, options);
			if (orientation > 0) {
				Matrix matrix = new Matrix();
				matrix.setRotate(orientation, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
				Bitmap dest = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				if (dest != bitmap) {
					bitmap.recycle();
					bitmap = null;
				}
				return dest;
			}
			return bitmap;
		} catch (IOException e) {
			return null;
		} finally {
			IoUtil.closeQuietly(fis);
		}
	}

	private static int scaleSize(Options options, int reqWidth, int reqHeight) {
		int inSampleSize = 1;
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;
		if (outWidth > reqWidth || outHeight > reqHeight) {
			int hRadio = Math.round((float) outHeight / (float) reqHeight);
			int wRadio = Math.round((float) outWidth / (float) reqWidth);
			inSampleSize = hRadio < wRadio ? hRadio : wRadio;
		}
		return inSampleSize;
	}

	public static final int PREVIEW_SIZE = 230;

	public static boolean isValid(Bitmap bitmap) {
		return bitmap != null && !bitmap.isRecycled();
	}

	public static void release(HashMap<String, Bitmap> bitmaps) {
		Iterator<Map.Entry<String, Bitmap>> iter = bitmaps.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Bitmap> entry = iter.next();
			recycle(entry.getValue());
		}
	}

	public static void recycle(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
	}

	public static Bitmap cropBitmap(Bitmap bitmap, float radio) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float r = (float) width / (float) height;
		if (r < radio) {
			int destH = (int) ((float) width / radio);
			int delta = (int) ((height - destH) / 2f);
			return Bitmap.createBitmap(bitmap, 0, delta, width, height - (2 * delta), null, true);
		} else {
			int destW = (int) (height * radio);
			int delta = (int) ((width - destW) / 2f);
			return Bitmap.createBitmap(bitmap, delta, 0, width - (2 * delta), height, null, true);
		}
	}

	public static Bitmap loadBitmap(AssetManager am, String path) {
		Options option = new Options();
		option.inDither = false;
		option.inPurgeable = true;
		option.inInputShareable = true;
		option.inTempStorage = new byte[32 * 1024];
		option.inPreferredConfig = Config.ARGB_8888;

		InputStream in = null;
		try {
			in = am.open(path);
			return BitmapFactory.decodeStream(in, null, option);
		} catch (IOException e) {
			return null;
		} finally {
			IoUtil.closeQuietly(in);
		}
	}

	public static Bitmap loadBitmap(String uri, int reqSize) {
		return loadBitmap(uri, reqSize, Config.RGB_565);
	}

	public static Bitmap loadBitmap(String uri, int reqSize, Config config) {
		if (TextUtils.isEmpty(uri)) {
			return null;
		}

		String realUrl = uri;
		if (uri.startsWith("file://")) {
			realUrl = uri.substring(6, uri.length());
		}

		int orientation = 0;
		try {
			ExifInterface exif = new ExifInterface(realUrl);
			if (exif != null) {
				int orien = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
				switch (orien) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					orientation = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					orientation = 270;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					orientation = 180;
					break;
				}
			}
		} catch (IOException e) {

		}

		FileInputStream fis = null;
		FileDescriptor fd = null;
		Options option = new Options();
		option.inDither = false;
		option.inPurgeable = true;
		option.inInputShareable = true;
		option.inTempStorage = new byte[32 * 1024];
		option.inPreferredConfig = config;

		try {
			fis = new FileInputStream(new File(realUrl));
			fd = fis.getFD();

			option.inJustDecodeBounds = true;
			BitmapFactory.decodeFileDescriptor(fd, null, option);
			option.inSampleSize = caclulateInSampleSize(option, reqSize, reqSize);
			option.inJustDecodeBounds = false;

			Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd, null, option);
			if (orientation > 0) {
				Matrix matrix = new Matrix();
				matrix.setRotate(orientation, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
				Bitmap dest = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				if (dest != bitmap) {
					bitmap.recycle();
				}
				return dest;
			} else {
				return bitmap;
			}
		} catch (IOException e) {

		} finally {
			IoUtil.closeQuietly(fis);
		}
		return null;
	}

	private static int caclulateInSampleSize(Options option, int rw, int rh) {
		int inSampleSize = 1;
		int ow = option.outWidth;
		int oh = option.outHeight;

		if (ow > rw || oh > rh) {
			final int hr = Math.round((float) oh / (float) rh);
			final int wr = Math.round((float) ow / (float) rw);
			inSampleSize = hr < wr ? hr : wr;
		}

		return inSampleSize;
	}

	public static void writeImage(File file, Bitmap bitmap) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 90, fos);
		} catch (Exception e) {
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
			}
		}
	}

	public static Bitmap blurBitmap(Bitmap bitmap) {
		int w = bitmap.getWidth() / 2;
		int h = bitmap.getHeight() / 2;

		Bitmap work = Bitmap.createScaledBitmap(bitmap, w, h, true);

		int[] pix = new int[w * h];

		work.getPixels(pix, 0, w, 0, 0, w, h);

		int radius = 4;
		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.max(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				pix[yi] = 0xff000000 | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}
		work.setPixels(pix, 0, w, 0, 0, w, h);

		return work;
	}

	private static final String TAG = ImageUtils.class.getSimpleName();

	private static final int BITMAP_TEMP_STORAGE = 32 * 1024;

	public static byte[] bitmap2Bytes(Bitmap bm, int quality, CompressFormat format) {
		byte[] data = null;
		if (bm != null && !bm.isRecycled()) {
			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				bm.compress(format, quality, baos);
				data = baos.toByteArray();
			} catch (Exception e) {
				Log.e(TAG, e.toString(), e);
			} catch (OutOfMemoryError oom) {
				Log.e(TAG, oom.toString(), oom);
			} finally {
				try {
					if (baos != null) {
						baos.close();
					}
				} catch (IOException e) {
					Log.e(TAG, e.toString(), e);
				}
			}
		}
		return data;
	}

	/**
	 * 照片缩放
	 *
	 * @param src
	 * @param reqW
	 * @param reqH
	 * @return
	 */
	public static Bitmap scaleBitmap(Bitmap src, int reqW, int reqH, boolean recycle) {
		if (src == null || reqW <= 0 || reqH <= 0) {
			return src;
		}

		Rect srcRect = calculateRect(src.getWidth(), src.getHeight(), reqW, reqH);
		Rect dstRect = new Rect(0, 0, reqW, reqH);
		Bitmap dstBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(dstBitmap);
		canvas.drawBitmap(src, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

		if (src != dstBitmap && recycle) {
			src.recycle();
		}

		return dstBitmap;
	}

	/***
	 * 图片的缩放方法
	 *
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
		return bitmap;
	}

	public static Bitmap rotateBitmap(Bitmap src, int degrees) {
		if (src == null) {
			return null;
		}
		Bitmap bm = null;
		Matrix m = new Matrix();
		m.setRotate(degrees, (float) src.getWidth() / 2, (float) src.getHeight() / 2);
		try {
			bm = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, true);
			if (src != bm) {
				src.recycle();
			}
		} catch (OutOfMemoryError oom) {
			Log.e(TAG, oom.toString(), oom);
		}
		return bm;
	}

	private static Rect calculateRect(int srcW, int srcH, int dstW, int dstH) {
		final float srcAspect = (float) srcW / (float) srcH;
		final float dstAspect = (float) dstW / (float) dstH;
		if (srcAspect > dstAspect) {
			final int srcRectW = (int) (srcH * dstAspect);
			final int srcRectL = (srcW - srcRectW) / 2;
			return new Rect(srcRectL, 0, srcRectL + srcRectW, srcH);
		} else {
			final int srcRectH = (int) (srcW / dstAspect);
			final int srcRectT = (int) (srcH - srcRectH) / 2;
			return new Rect(0, srcRectT, srcW, srcRectT + srcRectH);
		}
	}

	public static Bitmap decodeBitmapFromFile(File file, int reqW, int reqH) {
		if (file == null || !file.exists()) {
			return null;
		}
		FileDescriptor fd = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			fd = fis.getFD();

			final Options options = new Options();
			options.inDither = false;
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inTempStorage = new byte[BITMAP_TEMP_STORAGE];
			options.inPreferredConfig = Config.RGB_565;
			if (reqW > 0 && reqH > 0) {
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeFileDescriptor(fd, null, options);
				options.inJustDecodeBounds = false;
				options.inSampleSize = calculateInSampleSize(options, reqW, reqH);
			}
			return BitmapFactory.decodeFileDescriptor(fd, null, options);
		} catch (IOException e) {
			Log.e(TAG, e.toString(), e);
		} catch (OutOfMemoryError oom) {
			Log.e(TAG, oom.toString(), oom);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
			}
		}
		return null;
	}

	/**
	 * 计算图片合适的压缩倍数
	 *
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
		return calculateInSampleSize(options.outWidth, options.outHeight, reqWidth, reqHeight);
	}

	public static int calculateInSampleSize(int srcWidth, int srcHeight, int reqWidth, int reqHeight) {
		int inSampleSize = 1;
		if (srcHeight > reqHeight || srcWidth > reqWidth) {
			if (srcWidth > srcHeight) {
				inSampleSize = (int) Math.round((float) srcHeight / (float) reqHeight + .25);
			} else {
				inSampleSize = (int) Math.round((float) srcWidth / (float) reqWidth + .25);
			}
		}
		return inSampleSize;
	}

	public static int[] decodeBitmapSize(File file) {
		if (file == null || !file.exists()) {
			return null;
		}
		FileDescriptor fd = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			fd = fis.getFD();
			Options options = new Options();
			options.inDither = false;
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inTempStorage = new byte[BITMAP_TEMP_STORAGE];
			options.inPreferredConfig = Config.RGB_565;
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFileDescriptor(fd, null, options);
			int[] size = new int[2];
			size[0] = options.outWidth;
			size[1] = options.outHeight;
			return size;
		} catch (IOException e) {
			Log.e(TAG, e.toString(), e);
		} catch (OutOfMemoryError oom) {
			Log.e(TAG, oom.toString(), oom);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
			}
		}
		return null;
	}

	public static boolean isWideImage(int width, int height) {
		int min = Math.min(width, height);
		int max = Math.max(width, height);
		if (max != 0 && min != 0 && max >= min * 2) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isSmallImage(int width, int height) {
		boolean isSmall = false;
		if (width != 0 && height != 0) {
			return width < 600 && height < 600;
		}
		return isSmall;
	}

	public static File saveBitmapToFile(Bitmap bitmap, String _file) throws IOException {
		BufferedOutputStream os = null;
		File file = null;
		try {
			file = new File(_file);
			// String _filePath_file.replace(File.separatorChar +
			// file.getName(), "");
			int end = _file.lastIndexOf(File.separator);
			if (end == -1) {
				end = _file.length();
			}
			String _filePath = _file.substring(0, end);
			File filePath = new File(_filePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			file.createNewFile();
			os = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(CompressFormat.PNG, 100, os);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					Log.e("erorr", e.getMessage(), e);
				}
			}
		}

		return file;
	}

	public static Bitmap decodeFromStream(InputStream in, int i) {
		Options option = new Options();
		option.inSampleSize = i;
		return BitmapFactory.decodeStream(in, null, option);
	}

	public static Bitmap decodeFromStream(InputStream in, int reqW, int reqH) {
		final Options options = new Options();
		options.inDither = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[BITMAP_TEMP_STORAGE];
		options.inPreferredConfig = Config.RGB_565;
		if (reqW > 0 && reqH > 0) {
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			options.inJustDecodeBounds = false;
			options.inSampleSize = calculateInSampleSize(options, reqW, reqH);
		}

		calculateInSampleSize(options, reqW, reqH);
		return BitmapFactory.decodeStream(in, null, options);
	}
}
