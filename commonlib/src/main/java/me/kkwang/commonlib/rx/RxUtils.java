package me.kkwang.commonlib.rx;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/3/10.
 */
public class RxUtils {

    public static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            T observed = func.call();
                            if (observed != null) { // to make defaultIfEmpty work
                                subscriber.onNext(observed);
                            }
                            subscriber.onCompleted();
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                    }
                });
    }
}
