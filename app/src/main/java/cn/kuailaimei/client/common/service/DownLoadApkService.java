/*
 * Copyright (C) 2009 Teleca Poland Sp. z o.o. <android@teleca.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.kuailaimei.client.common.service;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import java.io.File;

import cn.kuailaimei.client.R;

/**
 *  下载apk service
 * 
 * @author  BSN
 */
@SuppressLint("NewApi")
public class DownLoadApkService extends Service {
	

	private DownloadManager downloadManager;
	
	private BroadcastReceiver receiver;
	
	private long lastDownloadId = -1L;
	
	private String loadUrl;
	
	public static final String DOWNLOADURL="downloadurl";
	
	
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

	@Override
	public void onCreate()
	{
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);		
		if(intent == null){
			return;
		}
		try {
			loadUrl = intent.getStringExtra(DOWNLOADURL);
			update(loadUrl);
		} catch (Exception e) {
		}
		
	}
	

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void startDownload(String url) {
		try {
			Uri uri = Uri.parse(url);
			Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs();
//			File folder = new File(folderName);
//			(folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
			Request dwreq = new Request(uri);
			dwreq.setAllowedNetworkTypes(Request.NETWORK_MOBILE
					| Request.NETWORK_WIFI);
			String filename ="klm";
			dwreq.setTitle(getString(R.string.app_name));
			dwreq.setDescription(filename+ System.currentTimeMillis());
			dwreq.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, System.currentTimeMillis()+filename);
			lastDownloadId = downloadManager.enqueue(dwreq);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void update(String url) {
		downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		receiver = new DownloadCompleteReceiver();
		registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		startDownload(url);
	}
	
	
	class DownloadCompleteReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
				//long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
				Query query = new Query();
				query.setFilterById(lastDownloadId);
				Cursor c = downloadManager.query(query);
				if (c.moveToFirst()) {
					int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
					if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
						String filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
						Log.e("file local url:", filename);
						openFile(filename, "application/vnd.android.package-archive");
					}
				}
				context.unregisterReceiver(receiver);
			}
		}
		
		private void openFile(String fileDir, String mimeType) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			File file = new File(fileDir.substring(7));
			if (file.exists()) {
				intent.setDataAndType(Uri.fromFile(file), mimeType);
				startActivity(intent);
				stopSelf();
				downloadManager=null;
				
			}
		}
	
		
	}
	


}
