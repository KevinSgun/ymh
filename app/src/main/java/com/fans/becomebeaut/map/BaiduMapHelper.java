package com.fans.becomebeaut.map;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import java.util.List;

public class BaiduMapHelper {

    public static int[] mMarkerResourceIds = new int[]{
//            R.mipmap.icon_dba,
//            R.mipmap.icon_dbb,
//            R.mipmap.icon_dbc,
//            R.mipmap.icon_dbd,
//            R.mipmap.icon_dbe,
//            R.mipmap.icon_dbf,
//            R.mipmap.icon_dbg,
//            R.mipmap.icon_dbh,
//            R.mipmap.icon_dbi,
//            R.mipmap.icon_dbj,
//            R.mipmap.icon_dbk,
//            R.mipmap.icon_dbl,
//            R.mipmap.icon_dbm,
//            R.mipmap.icon_dbn,
//            R.mipmap.icon_dbo,
//            R.mipmap.icon_dbp,
//            R.mipmap.icon_dbq,
//            R.mipmap.icon_dbr,
//            R.mipmap.icon_dbs,
//            R.mipmap.icon_dbt,
//            R.mipmap.icon_dbu,
//            R.mipmap.icon_dbv,
//            R.mipmap.icon_dbw,
//            R.mipmap.icon_dbx,
//            R.mipmap.icon_dby,
//            R.mipmap.icon_dbz
    };

    public static LocationClient mLocationClient = null;
    public static boolean isNeedLocation; //标识是否需要接受定位信息值 在接受定位信息成功后在外部关闭

    public void setIsNeedLocation(boolean flag) {
        isNeedLocation = flag;
        stopLoaction();
    }

    public void stopLoaction(){
        if(mLocationClient != null){
            mLocationClient.stop();
        }
    }

    /**
     * 启动定位功能
     *
     * @param mContext
     */
    public void startLocation(Context mContext, LocationCallBack locationCallBack) {
        isNeedLocation = true;
        mLocationClient = new LocationClient(mContext);
        initLocation(locationCallBack);
        mLocationClient.start();
    }

    /**
     * 初始化定位设置
     */
    public void initLocation(LocationCallBack locationCallBack) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new MyLocationListener(locationCallBack));
    }

    /**
     * 定位回调
     */
    public class MyLocationListener implements BDLocationListener {

        LocationCallBack locationCallBack;

        public MyLocationListener(LocationCallBack locationCallBack) {
            this.locationCallBack = locationCallBack;
        }

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }

            String address = location.getAddrStr();
            double lng = location.getLongitude();
            double lat = location.getLatitude();
            String titme = location.getTime();
            String city = location.getCity();
            String cityCode = location.getCityCode();
            String county = location.getCountry();
            String countyCode = location.getCountryCode();

//            if (!isNeedLocation) {
//                return;
//            }
            if (location!=null && !TextUtils.isEmpty(address)) {
                locationCallBack.onLocationSuccess(location);
            } else {
                locationCallBack.onLocationFail(sb.toString());
            }
        }
    }
}