package cn.kuailaimei.client.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;
import com.zitech.framework.transform.CropCircleTransformation;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.client.R;
import cn.kuailaimei.client.api.entity.Employee;
import cn.kuailaimei.client.api.entity.Evaluation;
import cn.kuailaimei.client.api.entity.ExchangeItem;
import cn.kuailaimei.client.common.ListAdapter;
import cn.kuailaimei.client.common.utils.ViewHolderUtil;

/**
 * Created by ludaiqian on 16/8/2.
 */
public class EvaluationListAdaper extends ListAdapter<Evaluation> implements IDataAdapter<List<Evaluation>> {

    public EvaluationListAdaper(Context context) {
        super(context);
        setList(new ArrayList<Evaluation>());
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop_evaluation, null);
        }
        final Evaluation evaluation = mList.get(position);
        TextView satisfaction = (TextView) ViewHolderUtil.get(convertView, R.id.satisfaction);
        RemoteImageView avatar = (RemoteImageView) ViewHolderUtil.get(convertView, R.id.avatar);
        TextView nickname = (TextView) ViewHolderUtil.get(convertView, R.id.nickname);
        TextView content = (TextView) ViewHolderUtil.get(convertView, R.id.content);
        TextView date = (TextView) ViewHolderUtil.get(convertView, R.id.date);
        avatar.setBitmapTransformation(new CropCircleTransformation(mContext));
        avatar.setImageUri(R.mipmap.ic_avatar_small, evaluation.getPortrait());

        nickname.setText(evaluation.getUname());
//        commentType
        satisfaction.setText(evaluation.getSatisfaction());
        date.setText(evaluation.getDate());
        content.setText(evaluation.getContent());
        return convertView;
    }


    @Override
    public void notifyDataChanged(List<Evaluation> evaluations, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
        }
        mList.addAll(evaluations);
        notifyDataSetChanged();
    }

    @Override
    public List<Evaluation> getData() {
        return mList;
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }


}
