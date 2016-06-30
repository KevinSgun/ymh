package com.fans.becomebeaut.api;

import com.fans.becomebeaut.api.response.GetwayResponse;
import com.zitech.framework.utils.LogUtils;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by lu on 2016/6/15.
 */
public class DefaultRetryStrategy implements
        Func1<Observable<? extends Throwable>, Observable<?>> {


    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts
                .flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {

                        LogUtils.d("retryWhen:" + throwable.getClass().getSimpleName() + "" + throwable.getMessage());
                        if (throwable instanceof Exception) {
                            //
                            return ApiFactory.gatewayLogin("18268739467", "123456").
                                    doOnNext(new Action1<GetwayResponse>() {
                                        @Override
                                        public void call(GetwayResponse getwayResponse) {
                                            //

                                        }
                                    });
                        }
                        return Observable.error(throwable);
                    }
                });


    }
}