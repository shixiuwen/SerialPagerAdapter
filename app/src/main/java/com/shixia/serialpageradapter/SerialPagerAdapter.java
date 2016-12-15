package com.shixia.serialpageradapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShiXiuwen on 2016/12/08.
 * <p>
 * Description: 对应主界面自动滑动的ViewPager
 * <p>
 * 为了实现连续滑动，将数组count放大10000倍，实际上未放大1000View的个数，只是用了除数取余的方法
 * 由于ViewPager会缓存前后两个View,所以导致在个数小于3的时候会出现重复添加的情况，故如果内容页
 * 小于等于三，将数组放大到大于等于三即可（4,6），如果内容页大于3，则可直接使用不好理解，详见今
 * 后发出的blog链接
 */

public class SerialPagerAdapter extends PagerAdapter {

    private List<ImageView> imageViewList = new ArrayList<>();

    private int oldViewCount = 0;
    private int newViewCount;  //如果内容页小于等于三，放大倍数到大于3

    public SerialPagerAdapter(Context context, List<Bitmap> list) {
        oldViewCount = list.size();
        List<Bitmap> newList = plusImageViewList(list);//将图片数量增加到>=4
        newViewCount = newList.size();
        for (int i = 0; i < newList.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageBitmap(newList.get(i));
            imageViewList.add(imageView);
        }
    }

    /**
     * 扩大图片列表数量到 >=4 的状态
     */
    private List<Bitmap> plusImageViewList(List<Bitmap> list) {
        if (list.size() == 1) {   //添加到4个数据
            for (int i = 0; i < 3; i++) {
                list.add(list.get(0));
            }
        }
        if (list.size() == 2) {   //添加到4个数据
            list.add(list.get(0));
            list.add(list.get(1));
        }
        if(list.size() == 3){   //添加到6个数据
            list.add(list.get(0));
            list.add(list.get(1));
            list.add(list.get(2));
        }
        return list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("amos->add", "position:" + position + " " + "position % newViewCount:" + position % newViewCount);
        container.addView(imageViewList.get(position % newViewCount), 0);
        return imageViewList.get(position % newViewCount);
    }

    @Override
    public int getCount() {
        return oldViewCount * 10000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.e("amos->remove", "position:" + position + " " + "position % newViewCount:" + position % newViewCount);
        container.removeView(imageViewList.get(position % newViewCount));
    }
}
