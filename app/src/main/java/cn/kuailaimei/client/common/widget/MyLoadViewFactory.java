package cn.kuailaimei.client.common.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import cn.kuailaimei.client.R;
import com.shizhefei.mvc.ILoadViewFactory;

public class MyLoadViewFactory implements ILoadViewFactory {

	@Override
	public ILoadMoreView madeLoadMoreView() {
		return new LoadMoreHelper();
	}

	@Override
	public ILoadView madeLoadView() {
		return new LoadViewHelper();
	}

	private class LoadMoreHelper implements ILoadMoreView {

		protected TextView footView;

		protected View.OnClickListener onClickRefreshListener;

		@Override
		public void init(FootViewAdder footViewHolder, View.OnClickListener onClickRefreshListener) {
			footView = (TextView) footViewHolder.addFootView(R.layout.layout_listview_foot);
			this.onClickRefreshListener = onClickRefreshListener;
			showNormal();
		}

		@Override
		public void showNormal() {
			footView.setText("点击加载更多");
			footView.setOnClickListener(onClickRefreshListener);
		}

		@Override
		public void showLoading() {
			footView.setText("正在加载中..");
			footView.setOnClickListener(null);
		}

		@Override
		public void showFailed(Exception exception) {
			footView.setText("加载失败，点击重新加载");
			footView.setOnClickListener(onClickRefreshListener);
		}

		@Override
		public void showNomore() {
			footView.setText("已经加载完毕");
			footView.setOnClickListener(null);
		}

	}

	private class LoadViewHelper implements ILoadView {
//		private VaryViewHelper helper;
		private ContentViewHolder holder;
		private Context context;

		@Override
		public void init(View switchView, OnClickListener onClickRefreshListener) {
			this.context = switchView.getContext().getApplicationContext();
			holder=new ContentViewHolder(context);
			holder.setContent(switchView);
			holder.setRetryListener(onClickRefreshListener);
		}

		@Override
		public void showContent() {
			holder.showContent();
		}

		@Override
		public void showLoading() {
			holder.showLoading();
		}

		@Override
		public void onLoadMoreError(Exception exception) {
			//Toast.makeText(context, "网络加载失败", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void showFailed(Exception exception) {
			holder.showRetry();
		}

		@Override
		public void showEmpty() {
			holder.showEmpty();
		}

	}
}
