package cn.kuailaimei.client.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;

import cn.kuailaimei.client.common.service.DownLoadApkService;
import cn.kuailaimei.client.common.widget.CommonDialog;

public class UpdateManager {

    private static final String TAG = "Updater";

    private Context context;

    private DownloadManager downloadManager;

    private BroadcastReceiver receiver;

    private long lastDownloadId = -1L;

    protected static String mCurrentVersion;

    public static String mNewVersion;

    public static String mVersionUrl;

    private boolean isOnlineInstall;

    protected static String mVersionUpdateContent;

    public static String OLDVERSION = "oldversion_brocast";

    public static String NEWVERSION = "newversion_brocast";

    private AlertDialog SuggestUpdateDialog;

    // private AlertDialog MustUpdateDialog;

    private ProgressDialog mProgressDialog;

    private CallBack updateCallBack;

    public UpdateManager(Context context) {
        this.context = context;
    }

    public interface CallBack {

        void updateFinish();

        void updateError();
    }

    public synchronized void update(final CallBack back) {
//        ApkUpdateRequest apkUpdateRequest = new ApkUpdateRequest();
//        apkUpdateRequest.setVersion(Session.getInstance().getVersionName());
//        Request request = new Request(apkUpdateRequest);
//        request.setMethod(ServiceApi.VERSION);
//        request.sign();
//        ApiFactory.execute(context, request, new ApiResponseListener<ApiResponse>() {
//            @Override
//            public void onResponseInActive(ApiResponse response) {
//                ApkUpdateResponse apkUpdateResponse = (ApkUpdateResponse) response.getData();
//               if(apkUpdateResponse!=null&&!TextUtils.isEmpty(apkUpdateResponse.getAddress())){
//                   final String url = apkUpdateResponse.getAddress();
//                   int status = apkUpdateResponse.getStatus();
//                   String content = apkUpdateResponse.getVersionDescribe();
//                   if(status ==0 ) {//建议升级
//                       showSuggestDialog(content,url);
//                   }else if(status ==1 ) {//强制升级
//                       showForceDialog(content,url);
//                   }
//
//               }else {
//                   ToastMaster.shortToast("当前已经是最新版本");
//               }
//
//
//            }
//
//            @Override
//            public void onError(HttpError error) {
//
//            }
//        });

    }

    private void showForceDialog(String content, final String url) {
        CommonDialog dialog = new CommonDialog(context,content);
        dialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
            @Override
            public void onClick(Dialog dialog) {
                Intent intent = new Intent(context, DownLoadApkService.class);
                intent.putExtra(DownLoadApkService.DOWNLOADURL, url);
                context.startService(intent);
                ToastMaster.shortToast("开始下载...");
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode== KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
                    return true;
                }else{
                    return false;
                }
            }
        });
        dialog.show();

    }

    //建议升级
    private void showSuggestDialog(String content, final String url) {
        CommonDialog dialog = new CommonDialog(context,content);
        dialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
            @Override
            public void onClick(Dialog dialog) {
                Intent intent = new Intent(context, DownLoadApkService.class);
                intent.putExtra(DownLoadApkService.DOWNLOADURL, url);
                context.startService(intent);
                ToastMaster.shortToast("开始下载...");
            }
        });
        dialog.show();
    }

    private void showUpdateDialog() {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("正在检查新版本");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
    }

    public boolean isNewVersion(String current, String remote) {
        String[] oldArray = current.split("\\.");
        String[] newArray = remote.split("\\.");
        int length = oldArray.length < newArray.length ? oldArray.length : newArray.length;
        for (int i = 0; i < length; i++) {
            if (Integer.parseInt(oldArray[i]) < Integer.parseInt(newArray[i]))
                return true;
            else if (Integer.parseInt(oldArray[i]) > Integer.parseInt(newArray[i]))
                return false;
        }
        return false;
    }

}
