package com.zcl.hxqh.zhongchuliang.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Matrectrans;
import com.zcl.hxqh.zhongchuliang.view.activity.InvbalancesActivity;
import com.zcl.hxqh.zhongchuliang.view.activity.MatrectransActivity;

import java.util.ArrayList;

/**
 * Created by apple on 15/12/12.
 */
public class MatrectransAdapter extends RecyclerView.Adapter<MatrectransAdapter.ViewHolder> {

    private static final String TAG = "MatrectransAdapter";
    InvbalancesActivity activity;
    String location;
    public ArrayList<Matrectrans> mItems = new ArrayList<Matrectrans>();

    public MatrectransAdapter(InvbalancesActivity activity, String location) {
        this.activity = activity;
        this.location = location;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Matrectrans item = mItems.get(i);


        viewHolder.itemNum_title.setText(R.string.item_num_text);
        viewHolder.itemNum.setText(item.itemnum == null ? "" : item.itemnum);
        viewHolder.itemDesc.setText(item.description);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MatrectransActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("matrectrans", item);
                bundle.putString("location", location);
                bundle.putInt("mark", activity.mark);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void update(ArrayList<Matrectrans> data, boolean merge) {
        if (merge && mItems.size() > 0) {
            for (int i = 0; i < mItems.size(); i++) {
                Log.i(TAG, "mItems=" + mItems.get(i).itemnum);
                Matrectrans obj = mItems.get(i);
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

    public void update(Matrectrans matrectrans) {
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).itemnum.equals(matrectrans.itemnum)) {
                mItems.set(i, matrectrans);
            }
        }
        notifyDataSetChanged();
    }

    public void adddate(ArrayList<Matrectrans> data) {
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (mItems.size() > 0) {
                    for (int j = 0; j < mItems.size(); j++) {
                        if (mItems.get(j).itemnum.equals(data.get(i).itemnum)) {
                            mItems.remove(j);
                        }
                    }
                }
            }
            mItems.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void remove(String itemnum) {
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).itemnum.equals(itemnum)) {
                mItems.remove(i);
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
        public RelativeLayout cardView;

        /**
         * 编号*
         */
        public TextView itemNum_title;

        /**
         * 项目*
         */
        public TextView itemNum;
        /**
         * 描述*
         */
        public TextView itemDesc;

        public ViewHolder(View view) {
            super(view);
            cardView = (RelativeLayout) view.findViewById(R.id.card_container);
            itemNum_title = (TextView) view.findViewById(R.id.item_num_title);
            itemNum = (TextView) view.findViewById(R.id.item_num_text);
            itemDesc = (TextView) view.findViewById(R.id.item_desc_text);
        }
    }
}
