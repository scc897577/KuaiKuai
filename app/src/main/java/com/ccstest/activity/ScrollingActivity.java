package com.ccstest.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ccstest.R;
import com.ccstest.adapter.MyAdapter;
import com.ccstest.refresh.RefreshLayout;
import com.ccstest.refresh.WaveView3;

import java.util.ArrayList;
import java.util.List;

/**
 * 1,沉浸式状态栏
 */
public class ScrollingActivity extends AppCompatActivity {

    private static final String TAG = "ScrollingActivity";

    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;
    private LinearLayout mLayout, lll;
    private float percentage;
    private RefreshLayout mRefreshLayout;
    private WaveView3 mWaveView3;
    private ListView mListView;
    private MyAdapter adapter;
    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.sw);
        //改变状态栏的颜色
        final Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mLayout = (LinearLayout) findViewById(R.id.ll);
        lll = (LinearLayout) findViewById(R.id.llllll);
        mWaveView3 = (WaveView3) findViewById(R.id.wave_view);
        mListView = (ListView) findViewById(R.id.lv);

        datas = new ArrayList<>();
        datas.add("number0");
        datas.add("number1");
        datas.add("number2");
        datas.add("number3");
        datas.add("number4");
        datas.add("number5");
        datas.add("number6");
        datas.add("number7");
        datas.add("number8");
        adapter = new MyAdapter(this,datas);
        mListView.setAdapter(adapter);




        mRefreshLayout.setRefreshHeader(LayoutInflater.from(this).inflate(R.layout.headerview,null));
        if (mRefreshLayout != null) {
            // 刷新状态的回调
            mRefreshLayout.setRefreshListener(new RefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mWaveView3.setVisibility(View.VISIBLE);
                    // 延迟3秒后刷新成功
                    mRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshLayout.refreshComplete();
//                            if (listView != null) {
//                                listView.setAdapter(new MainAdapter());
//                            }
                            datas.add(0,"新增");
                            adapter.notifyDataSetChanged();
                            mWaveView3.setVisibility(View.GONE);
                        }
                    }, 3000);
                }
            });

        }



        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                if (percentage == 0.0f) {
                    mToolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(getResources().getColor(R.color.transparent));
                    }
                    mRefreshLayout.setEnabled(true);
                } else {
                    mToolbar.setBackgroundColor(getResources().getColor(R.color.titleColor));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        int alpha = (int) (percentage * 255);
                        window.setStatusBarColor(Color.argb(alpha, 159, 95, 159));
                        mToolbar.setBackgroundColor(Color.argb(alpha, 159, 95, 159));
                    }
                    mRefreshLayout.setEnabled(false);
                }
                Log.i(TAG, "percentage = " + percentage);

            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void init() {
        datas = new ArrayList<>();
            datas.add("number0");
            datas.add("number1");
            datas.add("number2");
            datas.add("number3");
            datas.add("number4");
            datas.add("number5");
            datas.add("number6");
            datas.add("number7");
            datas.add("number8");
        adapter = new MyAdapter(this,datas);
        mListView.setAdapter(adapter);
    }

    private void setToolbarAlphaAnimation(float percentage, Toolbar toolbar) {
        int alpha = (int) (percentage * 255);
        toolbar.setBackgroundColor(Color.argb(alpha, 159, 95, 159));
    }


    //此处设置toolbar的渐隐渐显动画
    private void startAlphaAnimation(View view, int duration, int visiable, float percentage) {
        AlphaAnimation alphaAnimation = (visiable == View.VISIBLE)
                ? new AlphaAnimation(percentage, percentage)
                : new AlphaAnimation(percentage, percentage);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }
}
