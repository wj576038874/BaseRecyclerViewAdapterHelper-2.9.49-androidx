package com.chad.library.adapter.base;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chad.library.adapter.base.util.ProviderDelegate;
import java.util.List;

/**
 * https://github.com/chaychan
 *
 * @author ChayChan
 * @description: When there are multiple entries, avoid too much business logic in convert(),Put the logic of each item in the corresponding ItemProvider
 * 当有多种条目的时候，避免在convert()中做太多的业务逻辑，把逻辑放在对应的ItemProvider中
 * @date 2018/3/21  9:55
 * <p>
 * modify by wenjie 2019-12-10 16:16
 */
@SuppressWarnings("unchecked")
public abstract class MultipleItemRvAdapter<T, V extends BaseViewHolder> extends BaseQuickAdapter<T, V> {

    private SparseArray<BaseItemProvider> mItemProviders;
    protected ProviderDelegate mProviderDelegate;

    public MultipleItemRvAdapter(@Nullable List<T> data) {
        super(data);
    }

    public MultipleItemRvAdapter() {
        this(null);
    }

    /**
     * 用于adapter构造函数完成参数的赋值后调用
     * Called after the assignment of the argument to the adapter constructor
     */
    public void finishInitialize() {
        mProviderDelegate = new ProviderDelegate();

        setMultiTypeDelegate(new MultiTypeDelegate<T>() {

            @Override
            protected int getItemType(@NonNull T item) {
                return getViewType(item);
            }
        });

        registerItemProvider();

        mItemProviders = mProviderDelegate.getItemProviders();

        for (int i = 0; i < mItemProviders.size(); i++) {
            int key = mItemProviders.keyAt(i);
            BaseItemProvider provider = mItemProviders.get(key);
            provider.setAdapter(this);
            getMultiTypeDelegate().registerItemType(key, provider.layout());
        }
    }

    protected abstract int getViewType(@NonNull T item);

    public abstract void registerItemProvider();


    @Override
    protected void onItemClick(@NonNull V holder, @NonNull T item, int position) {
        int itemViewType = holder.getItemViewType();
        BaseItemProvider provider = mItemProviders.get(itemViewType);
        provider.onItemClick(holder, item, position);
    }

    @Override
    protected boolean onItemLongClick(@NonNull V holder, @NonNull T item, int position) {
        int itemViewType = holder.getItemViewType();
        BaseItemProvider provider = mItemProviders.get(itemViewType);
        return provider.onItemLongClick(holder, item, position);
    }

    @Override
    protected boolean onChildLongClick(@NonNull V holder, @NonNull T item, int position, @NonNull View view) {
        int itemViewType = holder.getItemViewType();
        BaseItemProvider provider = mItemProviders.get(itemViewType);
        return provider.onChildLongClick(holder, item, position, view);
    }

    @Override
    protected void onChildClick(@NonNull V holder, @NonNull T item, int position, @NonNull View view) {
        int itemViewType = holder.getItemViewType();
        BaseItemProvider provider = mItemProviders.get(itemViewType);
        provider.onChildClick(holder, item, position, view);
    }

    @Override
    protected V onCreateDefViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateDefViewHolder(parent, viewType);
    }

    @Override
    protected void onViewHolderCreated(@NonNull V holder, @NonNull ViewGroup parent, int viewType) {
        BaseItemProvider provider = mItemProviders.get(viewType);
        provider.mContext = parent.getContext();
        provider.mData = mData;
        provider.onViewHolderCreated(holder, parent, viewType);
    }

    @Override
    protected void convert(@NonNull V holder, @NonNull T item) {
        int itemViewType = holder.getItemViewType();
        BaseItemProvider provider = mItemProviders.get(itemViewType);
        int position = holder.getLayoutPosition() - getHeaderLayoutCount();
        //执行子类的convert方法
        provider.convert(holder, item, position);
    }

//    /**
//     * 绑定itemView点击事件和长按事件
//     *
//     * @param holder   holder
//     * @param provider provider
//     */
//    private void bindClick(@NonNull final V holder, final BaseItemProvider provider) {
//        OnItemClickListener clickListener = getOnItemClickListener();
//        OnItemLongClickListener longClickListener = getOnItemLongClickListener();
//
//        if (clickListener != null && longClickListener != null) {
//            //如果已经设置了子条目点击监听和子条目长按监听
//            // If you have set up a sub-entry click monitor and sub-entries long press listen
//            return;
//        }
//
//        View itemView = holder.itemView;
//
//        if (clickListener == null) {
//            //如果没有设置点击监听，则回调给itemProvider
//            //Callback to itemProvider if no click listener is set
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = holder.getAdapterPosition();
//                    if (position == RecyclerView.NO_POSITION) {
//                        return;
//                    }
//                    position -= getHeaderLayoutCount();
//                    provider.onItemClick(holder, getItem(position), position);
//                }
//            });
//        }
//
//        if (longClickListener == null) {
//            //如果没有设置长按监听，则回调给itemProvider
//            // If you do not set a long press listener, callback to the itemProvider
//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    int position = holder.getAdapterPosition();
//                    if (position == RecyclerView.NO_POSITION) {
//                        return false;
//                    }
//                    position -= getHeaderLayoutCount();
//                    return provider.onItemLongClick(holder, getItem(position), position);
//                }
//            });
//        }
//    }
//
//
//    /**
//     * 绑定子view的点击事件和长按事件
//     *
//     * @param holder   viewHolder
//     * @param provider itemProvider
//     */
//    private void bindChildClick(@NonNull final V holder, final BaseItemProvider provider) {
//        OnItemChildClickListener onItemChildClickListener = getOnItemChildClickListener();
//        OnItemChildLongClickListener onItemChildLongClickListener = getOnItemChildLongClickListener();
//
//        if (onItemChildClickListener != null && onItemChildLongClickListener != null) {
//            return;
//        }
//
//        if (onItemChildClickListener == null) {
//            //如果没有设置点击监听，则回调给itemProvider
//            HashSet<Integer> ids = holder.getChildClickViewIds();
//            for (Integer viewId : ids) {
//                final View view = holder.getView(viewId);
//                if (view != null) {
//                    if (!view.isClickable()) {
//                        view.setClickable(true);
//                    }
//                    view.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            int position = holder.getAdapterPosition();
//                            if (position == RecyclerView.NO_POSITION) {
//                                return;
//                            }
//                            position -= getHeaderLayoutCount();
//                            provider.onChildClick(holder, getItem(position), position, v);
//                        }
//                    });
//                }
//            }
//        }
//
//        if (onItemChildLongClickListener == null) {
//            //如果没有设置点击监听，则回调给itemProvider
//            HashSet<Integer> ids = holder.getItemChildLongClickViewIds();
//            for (Integer viewId : ids) {
//                final View view = holder.getView(viewId);
//                if (view != null) {
//                    if (!view.isClickable()) {
//                        view.setClickable(true);
//                    }
//                    view.setOnLongClickListener(new View.OnLongClickListener() {
//                        @Override
//                        public boolean onLongClick(View v) {
//                            int position = holder.getAdapterPosition();
//                            if (position == RecyclerView.NO_POSITION) {
//                                return false;
//                            }
//                            position -= getHeaderLayoutCount();
//                            return provider.onChildLongClick(holder, getItem(position), position, v);
//                        }
//                    });
//                }
//            }
//        }
//    }
}