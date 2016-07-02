package com.zitech.framework.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class FileDiskAllocator {
	private static final long LOW_STORAGE_THRESHOLD = 1024 * 1024 * 15;
	private static final String TAG = "FileDeskAllocator";
	private static final String  FileName=".fans";
	
	
	
	/**
	 * 生成头像，的保存地址
	 * @param mContext
	 * @param uniqueMedian
	 */
	public static File creatAvaterDir(Context mContext,String uniqueMedian){
	
		String path=FileName + "/" +uniqueMedian + "/avater";
		File file=creatPathByNeed(path);
		 return  allocateFileDir(mContext, file);
	}
	/**
	 * @param mContext
	 * @param uniqueMedian
	 */
	public static File creatCropDir(Context mContext,String uniqueMedian){
	
		String path=FileName + "/" +uniqueMedian + "/crop";
		File file=creatPathByNeed(path);
		 return  allocateFileDir(mContext, file);
	}
	/**
	 * volley load image path
	 * @param mContext
	 * @return
	 */
	public static File CreatVolleyCachePath(Context mContext){
		
		String path=FileName   + "/volley";
		File file=creatPathByNeed(path);
		 return  allocateFileDir(mContext, file);
	}
	
	
	/**
	 *  all cache  dir file path
	 * @param mContext
	 * @return
	 */
	
	public static File CreatAllDirPath(Context mContext){
		
		String path=FileName ;
		File file=creatPathByNeed(path);
		 return  allocateFileDir(mContext, file);
	}
	
	public static File creatUnionDir(Context mContext){
		
		String path=FileName   + "/union";
		File file=creatPathByNeed(path);
		 return  allocateFileDir(mContext, file);
	}
	
	public static File creatFansDir(Context mContext){
		
		String path=FileName + "/" +"default" + "/avater";
		File file=creatPathByNeed(path);
		 return  allocateFileDir(mContext, file);
	}
	
	
	
	/**
	 * 生成bbs发图路径
	 */
	
	public static File creatBbsPostDir(Context mContext,String uniqueMedian){
		String path=FileName + "/" +uniqueMedian + "/bbspost";
		File file=creatPathByNeed(path);
		 return allocateFileDir(mContext, file);
	}
	
	
	
	/**
	 * 生成文件保存路径
	 * 
	 * @param context
	 * @param cache
	 * @return
	 */
	public static File allocateFileDir(Context context, File pathFile) {
		File fileAvater = null;
		if (getExternalStoragePath() != null
				&& getAvailableStore(getExternalStoragePath()) > LOW_STORAGE_THRESHOLD) {
			fileAvater = pathFile;
			if (!fileAvater.exists()) {
				fileAvater.mkdirs();
			}
		} else {

			long available = getAvailableStore(context.getFilesDir().toString());
//			Log.i(TAG, "available desk:" + available);
			if (available > LOW_STORAGE_THRESHOLD) {
					fileAvater = context.getFilesDir();
			}
		}
		return fileAvater;
	}
	
	
	
	
	
	public static File  creatPathByNeed(String path){
		File 	filePath = new File(getExternalStoragePath(),path);
		return  filePath;
		
	}
	
	public static String getExternalStoragePath() {
		// 获取SdCard状态
		String state = Environment.getExternalStorageState();
		// 判断SdCard是否存在并且是可用的
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			if (Environment.getExternalStorageDirectory().canWrite()) {
				return Environment.getExternalStorageDirectory().getPath();
			}
		}
		return null;
	}

	public static boolean checkSdCardAvailable() {

		String state = Environment.getExternalStorageState();
		File sdCardDir = Environment.getExternalStorageDirectory();
		if (Environment.MEDIA_MOUNTED.equals(state) && sdCardDir.canWrite()) {
			if (getAvailableStore(sdCardDir.toString()) > LOW_STORAGE_THRESHOLD) {
				try {
//					File f = new File(Environment.getExternalStorageDirectory(), "_temp");
//					f.createNewFile();
//					f.delete();
					return true;
				} catch (Exception e) {
				}
			}
		}
		return false;

	}
	
	
	/**
	 * 
	 * 获取存储卡的剩余容量，单位为字节
	 * 
	 * @param filePath
	 * 
	 * @return availableSpare
	 */

	private static long getAvailableStore(String filePath) {
		// 取得sdcard文件路径
		StatFs statFs = new StatFs(filePath);
		// 获取block的SIZE
		long blocSize = statFs.getBlockSize();
		// 获取BLOCK数量
		long totalBlocks = statFs.getBlockCount();
		// 可使用的Block的数量
		long availaBlock = statFs.getAvailableBlocks();
		long availableSpare = availaBlock * blocSize;
		return availableSpare;

	}

}
