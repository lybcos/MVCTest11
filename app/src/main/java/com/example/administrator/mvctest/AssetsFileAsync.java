package com.example.administrator.mvctest;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/2/1.
 */

public class AssetsFileAsync extends AsyncTask<String,Void,String> {
    private Context mContext;
    public AssetsFileAsync(Context context) {
        this.mContext=context;
    }

    @Override
    protected String doInBackground(String... filename) {
        String fileName=null;
        if (filename!=null&&filename.length>0){
            fileName=filename[0];
        }
        if (filename == null) {
            return null;
        }
        return AssetFileRead.read(mContext,fileName);
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(mContext, s!=null?s:"null", Toast.LENGTH_SHORT).show();
    }
}
