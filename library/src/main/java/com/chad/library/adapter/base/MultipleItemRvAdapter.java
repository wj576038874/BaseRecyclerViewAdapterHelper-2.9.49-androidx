package com.chad.library.adapter.base;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chad.library.adapter.base.util.ProviderDelegate;

import java.util.HashSet;
import java.util.List;

/**
 * https://github.com/chaychan
 *
 * @author ChayChan
 * @description: When there are multiple entries, avoid too much business logic in convert(),Put the logic of each item in the corresponding ItemProvider
 * 当有多种条目的时候，避免在convert()中做太多的业务逻辑，把逻辑放在对应的ItemProvider中
 * @date 2018/3/21  9:55
 *
 * modify by wenjie 2019-12-10 16:16
 */
@SuppressWarnings("unchecked")
public abstract class MultipleItemRvAdapter<T, V extends BaseViewHolder> extends BaseQuickAdapter<T, V> {

    private SparseArray<BaseItemProvider> mItemProviders;
    protected ProviderDelegate mProviderDelegate;

    public MultipleItemRvAdapter(@Nullable List<T> data) {
        super(data);
    }

    /**
     * 用于adapter构造函数完成参数的赋值后调用
     * Called after the assignment of the argument to the adapter constructor
     */
    public void finishInitialize() {
        mProviderDelegate = new ProviderDelegate();

        setMultiTypeDelegate(new MultiTypeDelegate<T>() {

            @Override
            protected int getItemType(T t) {
                return getViewType(t);
            }
        });

        registerItemProvider();

        mItemProviders = mProviderDelegate.getItemProviders();

        for (int i = 0; i < mItemProviders.size(); i++) {
            int key = mItemProviders.keyAt(i);
            BaseItemProvider provider = mItemProviders.get(key);
            provider.mData = mData;
            provider.setAdapter(this);
            getMultiTypeDelegate().registerItemType(key, provider.layout());
        }
    }

    protected abstract int getViewType(T t);

    public abstract void registerItemProvider();


    @Override
    protected void onViewHolderCreated(@NonNull V holder, @NonNull ViewGroup parent, int viewType) {
        BaseItemProvider provider = mItemProviders.get(viewType);
        provider.mContext = parent.getContext();
        provider.onViewHolderCreated(holder, parent, viewType);
    }

    @Override
    protected V onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateDefViewHolder(parent, viewType);
    }

    @Override
    protected void convert(@NonNull V holder, T item) {
        int itemViewType = holder.getItemViewType();
        BaseItemProvider provider = mItemProviders.get(itemViewType);

        int position = holder.getLayoutPosition() - getHeaderLayoutCount();
        //执行子类的convert方法
        provider.convert(holder, item, position);
        //绑定item的点击事件
        bindClick(holder, item, position, provider);
        //绑定item的子view点击事件
        bindChildClick(holder, item, position, provider);
    }

    private void bindClick(@NonNull final V holder, final T item, final int position, final BaseItemProvider provider) {
        OnItemClickListener clickListener = getOnItemClickListener();
        OnItemLongClickListener longClickListener = getOnItemLongClickListener();

        if (clickListener != null && longClickListener != null) {
            //如果已经设置了子条目点击监听和子条目长按监听
            // If you have set up a sub-entry click monitor and sub-entries long press listen
            return;
        }

        View itemView = holder.itemView;

        if (clickListener == null) {
            //如果没有设置点击监听，则回调给itemProvider
            //Callback to itemProvider if no click listener is set
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    provider.onClick(holder, item, position);
                }
            });
        }

        if (longClickListener == null) {
            //如果没有设置长按监听，则回调给itemProvider
            // If you do not set a long press listener, callback to the itemProvider
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return provider.onLongClick(holder, item, position);
                }
            });
        }
    }


    /**
     * 绑定子view的点击事件和长按事件  注意这里做了非null判断 如果用户调用了adapter的事件
     * 那么 onItemChildClickListener onItemChildLongClickListener不会为null且执行用户的回调
     *
     * @param holder   viewHolder
     * @param item     data
     * @param position position
     * @param provider itemProvider
     */
    private void bindChildClick(@NonNull final V holder, final T item, final int position, final BaseItemProvider provider) {
        OnItemChildClickListener onItemChildClickListener = getOnItemChildClickListener();
        OnItemChildLongClickListener onItemChildLongClickListener = getOnItemChildLongClickListener();

        if (onItemChildClickListener != null && onItemChildLongClickListener != null) {
            return;
        }

        if (onItemChildClickListener == null) {
            //如果没有设置点击监听，则回调给itemProvider
            HashSet<Integer> ids = holder.getChildClickViewIds();
            for (Integer viewId : ids) {
                final View view = holder.getView(viewId);
                if (view != null) {
                    if (!view.isClickable()) {
                        view.setClickable(true);
                    }
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            provider.onChildClick(holder, item, position, v);
                        }
                    });
                }
            }
        }

        if (onItemChildLongClickListener == null) {
            //如果没有设置点击监听，则回调给itemProvider
            HashSet<Integer> ids = holder.getItemChildLongClickViewIds();
            for (Integer viewId : ids) {
                final View view = holder.getView(viewId);
                if (view != null) {
                    if (!view.isClickable()) {
                        view.setClickable(true);
                    }
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return provider.onChildLongClick(holder, item, position, v);
                        }
                    });
                }
            }
        }

    }
}
