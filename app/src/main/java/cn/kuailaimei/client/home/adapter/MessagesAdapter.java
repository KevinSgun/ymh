package cn.kuailaimei.client.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.Message;
import cn.kuailaimei.client.common.ListAdapter;
import cn.kuailaimei.client.common.utils.ViewHolderUtil;

/**
 * Created by lu on 2016/7/24.
 */
public class MessagesAdapter extends ListAdapter<Message> {

    public MessagesAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_message,null);
        }
        Message message=mList.get(position);
        TextView content=ViewHolderUtil.get(convertView,R.id.content);
        TextView date=ViewHolderUtil.get(convertView,R.id.date);
        content.setText(message.getContent());
        date.setText(message.getInTime());
        return convertView;
    }
}
