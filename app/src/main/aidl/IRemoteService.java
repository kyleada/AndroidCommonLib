/**
 * Created by Administrator on 2016/3/9.
 */
public interface IRemoteService {

    int getPid();

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                    double aDouble, String aString);
}
