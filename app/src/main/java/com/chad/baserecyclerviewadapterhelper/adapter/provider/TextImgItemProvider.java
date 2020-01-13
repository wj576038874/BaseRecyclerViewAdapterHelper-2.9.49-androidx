package com.chad.baserecyclerviewadapterhelper.adapter.provider;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chad.baserecyclerviewadapterhelper.R;
import com.chad.baserecyclerviewadapterhelper.adapter.DemoMultipleItemRvAdapter;
import com.chad.baserecyclerviewadapterhelper.entity.NormalMultipleEntity;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;

/**
 * https://github.com/chaychan
 * @author ChayChan
 * @description: Text Img ItemProvider
 * @date 2018/3/30  11:39
 */
public class TextImgItemProvider extends BaseItemProvider<NormalMultipleEntity,BaseViewHolder> {

    @Override
    public int viewType() {
        return DemoMultipleItemRvAdapter.TYPE_TEXT_IMG;
    }

    @Override
    public int layout() {
        return R.layout.item_img_text_view;
    }

    @Override
    public void convert(BaseViewHolder helper, NormalMultipleEntity data, int position) {
        helper.setText(R.id.tv, data.content);
        if (position % 2 == 0) {
            helper.setImageResource(R.id.iv, R.mipmap.animation_img1);
        }else{
            helper.setImageResource(R.id.iv, R.mipmap.animation_img2);
        }
        helper.addOnClickListener(R.id.tv);
    }

    @Override
    public void onCreateItemProvider(BaseViewHolder helper, @NonNull ViewGroup parent, int viewType) {
        Log.e("asd" ,"onCreateItemProvider");
    }

    @Override
    public void onClick(BaseViewHolder helper, NormalMultipleEntity data, int position) {
        Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, NormalMultipleEntity data, int position) {
        Toast.makeText(mContext, "longClick", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onChildClick(BaseViewHolder helper, NormalMultipleEntity data, int position, View childView) {
        if (childView.getId() == R.id.tv){
            TextView textView = (TextView) childView;
            Toast.makeText(mContext, "onChildClick---"+position, Toast.LENGTH_SHORT).show();
        }
    }
}
