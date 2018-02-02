package com.example.administrator.mvctest;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018/1/31.
 */

public class AssetFileRead {
    public static String read(Context context,String filename){
        InputStream is=null;
            try {
                is=context.getAssets().open(filename);
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader reader=new BufferedReader(isr);
                StringBuffer str = new StringBuffer();
                String line=null;
                while ((line=reader.readLine())!=null) {
                    str.append(line);
                }
                return str.toString();
            } catch (IOException e) {
                e.printStackTrace();
              }finally {
                stopThread(is);
            }
        return null;
    }

    public static void stopThread(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
