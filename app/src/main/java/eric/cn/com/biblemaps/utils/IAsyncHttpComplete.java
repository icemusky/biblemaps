package eric.cn.com.biblemaps.utils.interfaces;



import com.google.gson.internal.$Gson$Types;

import org.xutils.common.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 异步操作完成接口
 *   Created by liunan on 2017/5/9.
 */
public abstract class IAsyncHttpComplete<T> {
    public Type mType;
    public IAsyncHttpComplete()
    {
        mType = getSuperclassTypeParameter(getClass());
    }
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public abstract void onSuccess(T result);

    public abstract void onError(Throwable ex, boolean isOnCallback);

    public abstract void onCancelled(Callback.CancelledException cex);

    public abstract void onFinished();

    public abstract boolean onCache(String result);
}
