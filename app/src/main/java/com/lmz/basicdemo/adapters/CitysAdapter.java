package com.lmz.basicdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lmz.basicdemo.R;
import com.lmz.basicdemo.model.CitysModle;

import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * 作者：LMZ on 2016/12/22 0022 10:09
 */
public class CitysAdapter extends RecyclerView.Adapter<CitysAdapter.ViewHolder> implements StickyHeaderAdapter<CitysAdapter.HeaderHolder>{

    LayoutInflater mInfllater;
    private List<CitysModle> namelist;

    private char lastChar = '\u0000';
    private int DisplayIndex = 1;

    public CitysAdapter(Context context, List<CitysModle> namelist) {
        mInfllater = LayoutInflater.from(context);
        this.namelist = namelist;
    }

    @Override
    public CitysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInfllater.inflate(R.layout.item_contacts_item, parent, false);
        return new ViewHolder(view);
    }

    //条目文本填充
    @Override
    public void onBindViewHolder(CitysAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            holder.item.setText("杭州");
        } else {
            holder.item.setText(namelist.get(position - 1).name);
        }
    }

    @Override
    public int getItemCount() {
        return namelist.size();
    }
    public long getHeaderId(int position) {

        //这里面的是如果当前position与之前position重复（内部判断）  则不显示悬浮标题栏  如果不一样则显示标题栏
        if (position == 0) {
            return 0;
        } else {
            char ch = namelist.get(position - 1).pinyin.charAt(0);

            if (lastChar == '\u0000') {

                lastChar = ch;

                return DisplayIndex;
            } else {

                if (lastChar == ch) {

                    return DisplayIndex;

                } else {
                    lastChar = ch;
                    DisplayIndex++;
                    return DisplayIndex;
                }

            }
        }

    }
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInfllater.inflate(R.layout.item_contacts_head, parent, false);
        return new HeaderHolder(view);
    }

    //悬浮标题栏填充文本
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        if (position == 0) {
            viewholder.head.setText("热门城市");
        } else {
            //从A开始
            viewholder.head.setText(namelist.get(position - 1).pinyin.charAt(0) + "");
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView;
        }
    }

    /**
     * 悬浮头部
     */
    static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView head;

        public HeaderHolder(View itemView) {
            super(itemView);
            head = (TextView) itemView;
        }
    }
}
