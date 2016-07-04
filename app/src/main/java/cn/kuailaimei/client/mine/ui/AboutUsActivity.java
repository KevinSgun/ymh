package cn.kuailaimei.client.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import com.zitech.framework.Session;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class AboutUsActivity extends AppBarActivity{
    private TextView visiontv;
    private Button checkupdatebtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        setTitle("关于我们");
        visiontv = (TextView) findViewById(R.id.vision_tv);
        checkupdatebtn = (Button) findViewById(R.id.check_update_btn);
        checkupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 检查版本更新
            }
        });
    }

    @Override
    protected void initData() {
        visiontv.setText("V"+Session.getInstance().getVersionName());
    }

    public static void launch(Activity act){
        Intent intent = new Intent(act,AboutUsActivity.class);
        act.startActivity(intent);
    }
}
