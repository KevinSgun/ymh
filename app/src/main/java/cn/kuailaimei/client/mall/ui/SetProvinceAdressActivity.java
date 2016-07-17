package cn.kuailaimei.client.mall.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.AreaEntity;
import cn.kuailaimei.client.api.entity.CityEntity;
import cn.kuailaimei.client.common.ui.AppBarActivity;

public class SetProvinceAdressActivity extends AppBarActivity {
    private ListView arealist;
    private List<CityEntity> listCentity = new ArrayList<CityEntity>();
    private boolean isNeedArea;

    @Override
    protected void initView() {
        setTitle(getString(R.string.address_province));
        isNeedArea = getIntent().getBooleanExtra(Constants.ActivityExtra.IS_NEED_AREA, false);
        arealist = (ListView) findViewById(R.id.userinfo_arealist);

    }

    @Override
    protected void initData() {
        try {
            ParseXml(this.getAssets().open(isNeedArea ? "address_city.xml" : "city.xml"));
//			if (listCentity != null && listCentity.size() > 0) {
//				for (int i = 0; i < listCentity.size(); i++) {
//					CityEntity ce = listCentity.get(i);
//					Log.v("zz", "省" + ce.getPname());
//					for (int k = 0; k < ce.getCname().size(); k++) {
//						Log.v("zz", "市" + ce.getCname().get(k));
//					}
//				}
//			} else {
//			}
        } catch (IOException e) {
            Log.e("MainActivity", "onCreate ParseXml error is " + e.getMessage());
        }
        arealist.setAdapter(new myAreaListAdapter(listCentity));
        arealist.setOnItemClickListener(new MyEaraList());
    }

    private List<CityEntity> ParseXml(java.io.InputStream inputStream) {

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inputStream, "UTF-8");

            boolean done = false;
            int eventType = parser.getEventType();
            CityEntity centity = null;
            //
            AreaEntity aentity = null;
            while (!done) {
                String name = parser.getName();

                switch (eventType) {

                    case XmlPullParser.START_TAG:
                        if (name.equals("province")) {
                            centity = new CityEntity();
                            centity.setPname(parser.getAttributeValue(null, "name"));
                        } else if (name.equals("city")) {
                            aentity = new AreaEntity();
                            aentity.setCname(parser.getAttributeValue(null, "name"));
                        } else if (name.equals("area")) {
                            aentity.getAname().add(parser.getAttributeValue(null, "name"));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("city")) {
                            centity.getAname().add(aentity);
                        }
                        if (name.equals("province")) {
                            listCentity.add(centity);
                        }
                        if (name.equals("area")) {
                            // Logger.i("cityXML",aentity.getAname().get(0));
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        done = true;
                        break;

                }
                if (!done)
                    try {
                        eventType = parser.next();
                    } catch (IOException e) {
                    }
            }
        } catch (XmlPullParserException e) {
        }

        return listCentity;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_set_address;
    }

    class myAreaListAdapter extends BaseAdapter {

        private List<CityEntity> listCentity;

        public myAreaListAdapter(List<CityEntity> listCentity) {
            this.listCentity = listCentity;
        }

        public int getCount() {
            return listCentity.size();
        }

        public Object getItem(int arg0) {
            return listCentity.get(arg0);
        }

        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflate = LayoutInflater.from(SetProvinceAdressActivity.this);
            if (convertView == null)
                convertView = inflate.inflate(R.layout.item_address_set, null);
            TextView province = (TextView) convertView.findViewById(R.id.name);
            String pName = listCentity.get(position).getPname();
            province.setText(pName);
            return convertView;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Constants.ActivityExtra.SELECT_CITY_NAME) {
            String pname = data.getStringExtra("pname");
            String cname = data.getStringExtra("cname");
            Intent intent = new Intent();
            if (isNeedArea) {
                String aname = data.getStringExtra("aname");
                intent.putExtra("aname", aname);
            }
            intent.putExtra("pname", pname);
            intent.putExtra("cname", cname);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

    }

    class MyEaraList implements OnItemClickListener {

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            ArrayList<AreaEntity> c = (ArrayList<AreaEntity>) listCentity.get(arg2).getAname();
            String pName = listCentity.get(arg2).getPname();
            SetCityAdressActivity.luanch(SetProvinceAdressActivity.this, c, pName, isNeedArea);
        }
    }

    /***
     * @param context
     * @param isNeedArea 是否需要返回地区信息
     */
    public static void luanchForResult(Activity context, boolean isNeedArea) {
        Intent intent = new Intent(context, SetProvinceAdressActivity.class);
        intent.putExtra(Constants.ActivityExtra.IS_NEED_AREA, isNeedArea);
        context.startActivityForResult(intent, Constants.ActivityExtra.SELECT_PROVINCE_NAME);
    }

}
