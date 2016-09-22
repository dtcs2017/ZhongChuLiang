package com.zcl.hxqh.zhongchuliang.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Poline;
import com.zcl.hxqh.zhongchuliang.view.activity.PoLineActivity;
import com.zcl.hxqh.zhongchuliang.view.activity.PolineDetailActivity;

import java.util.ArrayList;

/**
 * Created by think on 15/12/16.
 */
public class PolineAdapter extends RecyclerView.Adapter<PolineAdapter.ViewHolder> {

    private static final String TAG = "PolineAdapter";
    PoLineActivity activity;
    ArrayList<Poline> mPos = new ArrayList<Poline>();

    public PolineAdapter(PoLineActivity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Poline poline = mPos.get(i);


        viewHolder.itemNum.setText(poline.itemnum);
        viewHolder.itemDesc.setText(poline.description);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PolineDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("poline", poline);
                bundle.putString("ponum",activity.ponum);
                bundle.putInt("mark",activity.mark);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mPos.size();
    }

    public void update(ArrayList<Poline> data, boolean merge) {
        if (merge && mPos.size() > 0) {
            for (int i = 0; i < mPos.size(); i++) {
                Poline obj = mPos.get(i);
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
        mPos = data;

        notifyDataSetChanged();
    }

    public void adddate(ArrayList<Poline> data) {
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (!mPos.contains(data.get(i))) {
                    mPos.add(data.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    public void update(Poline po) {
        for (int i = 0; i < mPos.size(); i++) {
            if (mPos.get(i).itemnum.equals(po.itemnum)) {
                mPos.set(i, po);
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * CardView*
         */
        public RelativeLayout cardView;

        /**
         * 编号*
         */
        public TextView itemNum;
        /**
         * 描述*
         */
        public TextView itemDesc;

        public ViewHolder(View view) {
            super(view);
            cardView = (RelativeLayout) view.findViewById(R.id.card_container);
            itemNum = (TextView) view.findViewById(R.id.item_num_text);
            itemDesc = (TextView) view.findViewById(R.id.item_desc_text);
        }
    }
}
