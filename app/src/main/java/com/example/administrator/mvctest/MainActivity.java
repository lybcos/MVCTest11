package com.example.administrator.mvctest;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<String> {
    private static String NETEASE_DESC_FILENAME = "netease.txt";
    private static String WEIZHUANYE_DESC_FILENAME = "weizhuanye.txt";
    @BindView(R.id.thread_pool)
    Button threadPool;
    @BindView(R.id.btn_async)
    Button btnAsync;
    @BindView(R.id.btn_loader)
    Button btnLoader;
    @BindView(R.id.btn_rxj)
    Button btnRxj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        findViewById(R.id.thread_pool).setOnClickListener(this);
        findViewById(R.id.btn_async).setOnClickListener(this);
        findViewById(R.id.btn_loader).setOnClickListener(this);
        findViewById(R.id.btn_rxj).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thread_pool:
                ExecutorService executor = Executors.newFixedThreadPool(3);
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        final String content = AssetFileRead.read(MainActivity.this, NETEASE_DESC_FILENAME);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, content != null ? content : "null", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case R.id.btn_async:
                AssetsFileAsync fileAsync=new AssetsFileAsync(this);
                fileAsync.execute(WEIZHUANYE_DESC_FILENAME);
                break;
            case R.id.btn_loader:
                getLoaderManager().initLoader(1,null,MainActivity.this);//启动一个Loader
                break;
            case R.id.btn_rxj:
                Observable.just(NETEASE_DESC_FILENAME,WEIZHUANYE_DESC_FILENAME)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                return AssetFileRead.read(MainActivity.this,s);
                            }
                        })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(MainActivity.this, s!=null?s:"null", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {//如果该Loader的id已经存在,则后面的Loader直接复用已经存在的
        return new AssetsFileLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Toast.makeText(this,"LoadFinished"+ data!=null?data:"null", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Toast.makeText(this,"LoadReset", Toast.LENGTH_SHORT).show();
    }


}
