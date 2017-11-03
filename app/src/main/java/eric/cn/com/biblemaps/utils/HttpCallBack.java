package eric.cn.com.biblemaps.utils;



import com.google.gson.Gson;

import org.xutils.common.Callback;

import eric.cn.com.biblemaps.utils.interfaces.IAsyncHttpComplete;


/**
 * 将接收成功的字符串转换成bean返回
 * Created by liunan on 2017/5/9.
 */
public class HttpCallBack<T> implements Callback.CacheCallback<String> {
    public IAsyncHttpComplete<T> mCallBack;

    public HttpCallBack(IAsyncHttpComplete<T> callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public boolean onCache(String result) {
        mCallBack.onCache(result);
        return false;
    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        T model = gson.fromJson(result, mCallBack.mType);
        mCallBack.onSuccess(model);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        mCallBack.onError(ex,isOnCallback);
    }

    @Override
    public void onCancelled(CancelledException cex) {
        mCallBack.onCancelled(cex);
    }

    @Override
    public void onFinished() {
        mCallBack.onFinished();
    }
}