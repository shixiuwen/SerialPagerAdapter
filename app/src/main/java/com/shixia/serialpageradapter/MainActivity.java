package com.shixia.serialpageradapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private PageChangeIndicatorView pciIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager vpBitmap = (ViewPager) findViewById(R.id.vp_Bitmap);
        pciIndicator = (PageChangeIndicatorView) findViewById(R.id.pci_indicator);

        vpBitmap.setAdapter(new SerialPagerAdapter(this, getBitmapList()));

        pciIndicator.setPageCount(getBitmapList().size());  //设置下标点的个数

        //设置自动切换
        Observable.interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        vpBitmap.setCurrentItem(vpBitmap.getCurrentItem() + 1, true);
                    }
                });

        //通过监听页面切换切换下标
        vpBitmap.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pciIndicator.onPageSelectedUpdate(position % getBitmapList().size());    //切换下标
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 创建一个bitmap列表模拟数据
     *
     * @return bitmap列表
     */
    public List<Bitmap> getBitmapList() {
        Bitmap bitmap01 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap01);
        Bitmap bitmap02 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap02);
        Bitmap bitmap03 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap03);
        Bitmap bitmap04 = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap04);
        List<Bitmap> bitmapList = new ArrayList<>();
        bitmapList.add(bitmap01);
        bitmapList.add(bitmap02);
        bitmapList.add(bitmap03);
        bitmapList.add(bitmap04);

        return bitmapList;
    }
}
