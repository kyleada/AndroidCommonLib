package me.kkwang.commonlib.rx;

/**
 * Created by kw on 2016/3/7.
 */
public class RxBusManager {
    private static RxBus _rxBus = null;

    // This is better done with a DI Library like Dagger
    public static RxBus getRxBusSingleton() {
        if (_rxBus == null) {
            _rxBus = new RxBus();
        }
        return _rxBus;
    }
}
