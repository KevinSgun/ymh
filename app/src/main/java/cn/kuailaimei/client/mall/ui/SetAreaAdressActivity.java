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

import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.AreaEntity;
import cn.kuailaimei.client.common.ui.AppBarActivity;

public class SetAreaAdressActivity extends AppBarActivity {
	private ListView arealv;
	private String pname;
//	private SharedPreferences userInfo;
	private AreaEntity areaList;
	private List<String> list;
	private String cname;

	@Override
	protected void initView() {
		setTitle("选择区\\县");
	}

	@Override
	protected void initData() {

		// userInfo = getSharedPreferences("user_info", 0);
		Intent area = getIntent();
		// lt = (ArrayList<AreaEntity>) city.getSerializableExtra("list");

		//获取地区信息列表areaList
		areaList = area.getParcelableExtra("arealist");
		list = areaList.getAname();
		pname = area.getStringExtra("pname");
		cname = area.getStringExtra("cname");
		arealv = (ListView) this.findViewById(R.id.userinfo_arealist);
		arealv.setAdapter(new myAreaListAdapter());
		arealv.setOnItemClickListener(new MyEaraList());
	}

	@Override
	protected int getContentViewId() {
		return R.layout.activity_set_address;
	}

	class myAreaListAdapter extends BaseAdapter {

		public int getCount() {
			return list.size();
		}

		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflate = LayoutInflater.from(SetAreaAdressActivity.this);
			if (convertView == null)
				convertView = inflate.inflate(R.layout.item_address_set, null);
			TextView area = (TextView) convertView.findViewById(R.id.name);
			area.setText(list.get(position));
			return convertView;
		}

	}

	class MyEaraList implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// String city = lt.get(arg2).getCname();
			String area = list.get(arg2);
			if (area != null && pname != null) {
				Intent intent = new Intent();
				intent.putExtra("pname", pname);
				intent.putExtra("aname", area);
				intent.putExtra("cname", cname);
				setResult(Activity.RESULT_OK, intent);
				finish();
				// Intent intent = new Intent();
				// intent.putExtra("pname", pname);
				// intent.putExtra("cname", city);
				// setResult(Activity.RESULT_OK, intent);
				// finish();
			}
		}

	}
}
