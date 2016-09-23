package com.zcl.hxqh.zhongchuliang.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Invbalances;
import com.zcl.hxqh.zhongchuliang.view.activity.BinChooseActivity;

import java.util.ArrayList;

/**
 * Created by apple on 15/12/12.
 * 仓库库位号
 */
public class BinAdapter extends RecyclerView.Adapter<BinAdapter.ViewHolder> {

    private static final String TAG = "BinAdapter";
    ArrayList<Invbalances> mItems = new ArrayList<Invbalances>();
    BinChooseActivity activity;

    public BinAdapter(BinChooseActivity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item2, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Invbalances item = mItems.get(i);

        viewHolder.itemNum_title.setText(R.string.bin_batch);
        viewHolder.itemDesc_title.setText(R.string.bin_curbaltotal);
        viewHolder.itembin_title.setText(R.string.bin_item);
        viewHolder.avatar.setVisibility(View.GONE);
        viewHolder.itemNum.setText(item.lotnum.equals("") ? " " : item.lotnum);
        viewHolder.itemDesc.setText(item.curbal);
        viewHolder.itemBin.setText(item.binnum);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = activity.getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("binnum", item.binnum);
                bundle.putString("tolot", item.lotnum);
                intent.putExtras(bundle);
                if (activity.requestCode == 1) {
                    activity.setResult(1, intent);
                }else if(activity.requestCode == 3){
                    activity.setResult(3, intent);
                }
                activity.finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void update(ArrayList<Invbalances> data, boolean merge) {
        if (merge && mItems.size() > 0) {
            for (int i = 0; i < mItems.size(); i++) {
                Log.i(TAG, "mItems=" + mItems.get(i).itemnum);
                Invbalances obj = mItems.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).itemnum == obj.itemnum) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                data.add(obj);
            }
        }
        mItems = data;

        notifyDataSetChanged();
    }

    public void adddate(ArrayList<Invbalances> data) {
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (!mItems.contains(data.get(i))) {
                    mItems.add(data.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    public void removeAllData() {
        if (mItems.size() > 0) {
            mItems.removeAll(mItems);
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * CardView*
         */
        public CardView cardView;

        /**
         * 编号*
         */
        public TextView itemNum_title;
        public TextView itemDesc_title;
        public TextView itembin_title;

        /**
         * 批次*
         */
        public TextView itemNum;
        /**
         * 余量*
         */
        public TextView itemDesc;

        public TextView itemBin;

        public ImageView avatar;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_container);
            itemNum_title = (TextView) view.findViewById(R.id.item_num_title);
            itemDesc_title = (TextView) view.findViewById(R.id.item_desc_title);
            itembin_title = (TextView) view.findViewById(R.id.item_binnum_title);
            itemNum = (TextView) view.findViewById(R.id.item_num_text);
            itemDesc = (TextView) view.findViewById(R.id.item_desc_text);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            itemBin = (TextView) view.findViewById(R.id.item_binnum_text);
        }
    }
}
