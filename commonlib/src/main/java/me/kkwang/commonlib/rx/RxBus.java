package me.kkwang.commonlib.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by kw on 2016/3/7.
 * 使用参考
 * https://github.com/kaushikgopal/RxJava-Android-Samples
 */
public class RxBus {

    //private final PublishSubject<Object> _bus = PublishSubject.create();

    // If multiple threads are going to emit events to this
    // then it must be made thread-safe like this instead
    private final Subject<Object, Object>
            _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return _bus;
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }
}
