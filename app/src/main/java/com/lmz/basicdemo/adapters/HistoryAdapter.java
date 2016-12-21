package com.lmz.basicdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lmz.baselibrary.widget.ItemSlideHelper;
import com.lmz.basicdemo.R;
import com.lmz.basicdemo.model.HistoryModle;

import java.util.List;

/**
 * 作者：LMZ on 2016/12/20 0020 17:58
 */
public class HistoryAdapter extends BaseQuickAdapter<HistoryModle.DataEntity, BaseViewHolder> implements ItemSlideHelper.Callback {
    RecyclerView mRecycleView;

    public HistoryAdapter(List<HistoryModle.DataEntity> data) {
        super(R.layout.item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HistoryModle.DataEntity dataEntity) {
        baseViewHolder.setText(R.id.item_text, dataEntity.getText())
                .addOnClickListener(R.id.tv_delete);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecycleView = recyclerView;
        mRecycleView.addOnItemTouchListener(new ItemSlideHelper(mRecycleView.getContext(), this));
    }

    /**
     * 实现侧滑删除
     *
     * @param holder
     * @return
     */
    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            if (viewGroup.getChildCount() == 2) {
                return viewGroup.getChildAt(1).getLayoutParams().width;
            }
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecycleView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecycleView.findChildViewUnder(x, y);
    }
}
