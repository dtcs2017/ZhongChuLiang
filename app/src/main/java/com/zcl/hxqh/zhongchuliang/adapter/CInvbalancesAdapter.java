package com.zcl.hxqh.zhongchuliang.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Invbalances;
import com.zcl.hxqh.zhongchuliang.view.activity.CInvbalancesDetailActivity;

import java.util.ArrayList;

/**
 * Created by apple on 15/12/12.
 * 库存余量
 */
public class CInvbalancesAdapter extends RecyclerView.Adapter<CInvbalancesAdapter.ViewHolder> {

    private static final String TAG = "CInvbalancesAdapter";
    Context mContext;
    ArrayList<Invbalances> mItems = new ArrayList<Invbalances>();
    private String location; //位置

    public CInvbalancesAdapter(Context context, String location) {
        this.mContext = context;
        this.location=location;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cinvbalances_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final Invbalances item = mItems.get(position);
        viewHolder.itemNumTitle.setText(mContext.getString(R.string.invbalances_binnum_title));
        viewHolder.itemDescTitle.setText(mContext.getString(R.string.invbalances_lotnum_title));
        viewHolder.itemNum.setText(item.binnum);
        viewHolder.itemDesc.setText(item.lotnum);


        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, CInvbalancesDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("invbalances", item);
                bundle.putString("location", location);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
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
         * 编号-title*
         */
        public TextView itemNumTitle;
        /**
         * 编号*
         */
        public TextView itemNum;
        /**
         * 描述-title*
         */
        public TextView itemDescTitle;
        /**
         * 描述*
         */
        public TextView itemDesc;


        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_container);
            itemNumTitle = (TextView) view.findViewById(R.id.item_num_title);
            itemNum = (TextView) view.findViewById(R.id.item_num_text);
            itemDescTitle = (TextView) view.findViewById(R.id.item_desc_title);
            itemDesc = (TextView) view.findViewById(R.id.item_desc_text);

        }
    }
}
