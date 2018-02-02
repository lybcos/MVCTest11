package com.example.administrator.mvctest;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Administrator on 2018/2/1.
 */

public class AssetsFileLoader extends AsyncTaskLoader<String> {
    String mContent=null;
    private Context mContext;
    public AssetsFileLoader(Context context) {
        super(context);
        mContext=context;

    }

    @Override
    public String loadInBackground() {
        return AssetFileRead.read(mContext,"netease.txt");
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
        if (isReset()) {
            return ;
        }
        mContent=data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mContent != null) {
            deliverResult(mContent);
        }
        if (mContent == null||takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public void stopLoading() {
        super.stopLoading();
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        mContent=null;
    }
}
