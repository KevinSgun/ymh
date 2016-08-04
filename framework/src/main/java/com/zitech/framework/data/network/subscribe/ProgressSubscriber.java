package com.zitech.framework.data.network.subscribe;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.zitech.framework.data.network.IContext;
import com.zitech.framework.widget.LoadingDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> {

    private LoadingDialog mDialog;

    private IContext context;

    public ProgressSubscriber(IContext context) {
        this(context, LoadingDialog.newInstance());
    }

    public ProgressSubscriber(IContext context, LoadingDialog dialog) {
        this.context = context;
        mDialog = dialog;
        mDialog.setOnDialogCancelListener(new LoadingDialog.OnDialogCancelListener() {
            @Override
            public void onCancel() {
                if (!isUnsubscribed()) {
                    unsubscribe();
                }
            }
        });
    }

    private void showProgressDialog() {
        Context ct = context.getContext();
        if (mDialog!=null&&ct != null && ct instanceof FragmentActivity)
            mDialog.show(((FragmentActivity) ct).getSupportFragmentManager(), context.getClass().getName());
    }

    private void dismissProgressDialog() {
        mDialog.dismiss();
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
       // Toast.makeText(context.getContext(), "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context.getContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context.getContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dismissProgressDialog();

    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
//        onNext(context.isActive(), t);
        if (context.isActive()) {
            onNextInActive(t);
        } else {
            onNextAfterDestoryed(t);
        }


    }

    /**
     * 在{@link IContext#isActive()}为{@code true}时返回
     *
     * @param t
     */
    protected abstract void onNextInActive(T t);

    /**
     * 在{@link IContext#isActive()}为{@code false}时返回
     *
     * @param t
     */
    protected void onNextAfterDestoryed(T t) {

    }
}