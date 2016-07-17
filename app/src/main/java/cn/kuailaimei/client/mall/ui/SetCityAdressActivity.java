package cn.kuailaimei.client.mall.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.AreaEntity;
import cn.kuailaimei.client.common.ui.AppBarActivity;

public class SetCityAdressActivity extends AppBarActivity {
	private ListView arealv;
	private ArrayList<AreaEntity> lt = null;
	private String pname;
//	private SharedPreferences userInfo;
	private boolean isNeedArea;

	@Override
	protected void initView() {
		setTitle(getString(R.string.address_city));
		// userInfo = getSharedPreferences("user_info", 0);
	}

	@Override
	protected void initData() {
		Intent city = getIntent();
		// lt = (ArrayList<AreaEntity>) city.getSerializableExtra("list");

		//获取城市列表信息，城市列表是通过省份关联
		isNeedArea = city.getBooleanExtra(Constants.ActivityExtra.IS_NEED_AREA, false);
		lt = city.getParcelableArrayListExtra("list");
		pname = city.getStringExtra("pname");
		arealv = (ListView) findViewById(R.id.userinfo_arealist);
		arealv.setAdapter(new MyAreaListAdapter());
		arealv.setOnItemClickListener(new MyEaraList());
	}



	@Override
	protected int getContentViewId() {
		return R.layout.activity_set_address;
	}

	class MyAreaListAdapter extends BaseAdapter {

		public int getCount() {
			return lt.size();
		}

		public Object getItem(int arg0) {
			return lt.get(arg0);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflate = LayoutInflater.from(SetCityAdressActivity.this);
			if (convertView == null)
				convertView = inflate.inflate(R.layout.item_address_set, null);
			TextView city = (TextView) convertView.findViewById(R.id.name);
			city.setText(lt.get(position).getCname());
			return convertView;
		}

	}

	class MyEaraList implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// String city = lt.get(arg2).getCname();
			AreaEntity areaList = lt.get(arg2);
			if (areaList != null && pname != null) {
				Intent intent = new Intent();
				intent.putExtra("pname", pname);
				intent.putExtra("cname", areaList.getCname());
				if(isNeedArea){
					intent.setClass(SetCityAdressActivity.this,SetAreaAdressActivity.class);
					intent.putExtra("arealist", areaList);
					startActivityForResult(intent, Constants.ActivityExtra.SELECT_AREA_NAME);
				}else{
					setResult(Activity.RESULT_OK, intent);
					finish();
				}
			}
		}

	}
	
	public static void luanch(Activity context, ArrayList<AreaEntity> c, String pName, boolean isNeedArea){
		Intent intent = new Intent(context, SetCityAdressActivity.class);
		intent.putParcelableArrayListExtra("list", c);
		intent.putExtra("pname", pName);
		intent.putExtra(Constants.ActivityExtra.IS_NEED_AREA, isNeedArea);
		context.startActivityForResult(intent, Constants.ActivityExtra.SELECT_CITY_NAME);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == Constants.ActivityExtra.SELECT_AREA_NAME) {
			String pname = data.getStringExtra("pname");
			String cname = data.getStringExtra("cname");
			String aname = data.getStringExtra("aname");
			Intent intent = new Intent();
			intent.putExtra("pname", pname);
			intent.putExtra("cname", cname);
			intent.putExtra("aname", aname);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	}
}
