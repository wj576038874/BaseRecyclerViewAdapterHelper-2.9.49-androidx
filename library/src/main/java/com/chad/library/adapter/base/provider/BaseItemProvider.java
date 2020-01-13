package com.chad.library.adapter.base.provider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * https://github.com/chaychan
 *
 * @author ChayChan
 * @description: The base class of ItemProvider
 * @date 2018/3/21  10:41
 * modify by wenjie 2020-01-13 16:16
 * 新增子view的点击事件 以及长按事件 以及viewholder创建的回调方法onCreateItemProvider
 */
public abstract class BaseItemProvider<T, V extends BaseViewHolder> {

    public Context mContext;
    public List<T> mData;

    /**
     * add adapter ItemProvider有需要用到adapter可以调用getAdapter获取
     */
    private WeakReference<MultipleItemRvAdapter<T, V>> weakAdapter;

    //子类须重写该方法返回viewType
    //Rewrite this method to return viewType
    public abstract int viewType();

    //子类须重写该方法返回layout
    //Rewrite this method to return layout
    public abstract int layout();


    public void setAdapter(MultipleItemRvAdapter<T, V> adapter) {
        weakAdapter = new WeakReference<>(adapter);
    }

    @Nullable
    public MultipleItemRvAdapter<T, V> getAdapter() {
        if (weakAdapter == null) return null;
        return weakAdapter.get();
    }

    /**
     * add  初始化viewholder的时候会调用 子类可以重写然后做一些操作
     */
    public void onCreateItemProvider(V helper, @NonNull ViewGroup parent, int viewType) {

    }

    public abstract void convert(V helper, T data, int position);

    //子类若想实现条目点击事件则重写该方法
    //Subclasses override this method if you want to implement an item click event
    public void onClick(V helper, T data, int position) {

    }


    //子类若想实现条目长按事件则重写该方法
    //Subclasses override this method if you want to implement an item long press event
    public boolean onLongClick(V helper, T data, int position) {
        return false;
    }

    /**
     * add 子类若想实现条目的子view点击事件则重写该方法
     *
     * @param helper    viewHolder
     * @param data      数据
     * @param position  position
     * @param childView 点击的子view
     */
    public void onChildClick(V helper, T data, int position, View childView) {

    }

    /**
     * add  子类若想实现条目的子view长按事件则重写该方法
     *
     * @param helper    viewHolder
     * @param data      数据
     * @param position  position
     * @param childView 长按的子view
     * @return 注意这个方法在适配器中设置了时间之后不会回调，做过过滤
     */
    public boolean onChildLongClick(V helper, T data, int position, View childView) {
        return false;
    }
}
