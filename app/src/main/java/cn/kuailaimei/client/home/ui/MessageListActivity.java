package cn.kuailaimei.client.home.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.List;

import cn.kuailaimei.client.Constants;
import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.ApiFactory;
import cn.kuailaimei.client.api.entity.Message;
import cn.kuailaimei.client.api.request.NullDataRequest;
import cn.kuailaimei.client.api.request.Request;
import cn.kuailaimei.client.common.ui.AppBarActivity;
import cn.kuailaimei.client.home.adapter.MessagesAdapter;

/**
 * Created by lu on 2016/7/24.
 */
public class MessageListActivity extends AppBarActivity {
    private ListView messages;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_messages;
    }

    @Override
    protected void initView() {
        setTitle("消息");
        messages = (ListView) findViewById(R.id.messages);
    }

    @Override
    protected void initData() {
        ApiFactory.getMessages(new Request(new NullDataRequest())).subscribe(new ProgressSubscriber<ApiResponse<List<Message>>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<List<Message>> listApiResponse) {
                MessagesAdapter adapter = new MessagesAdapter(getContext());
                adapter.setList(listApiResponse.getData());
                messages.setAdapter(adapter);
            }
        });
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MessageListActivity.class);
        context.startActivity(intent);
    }
}
