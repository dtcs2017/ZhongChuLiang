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
import com.zcl.hxqh.zhongchuliang.model.Locations;
import com.zcl.hxqh.zhongchuliang.view.activity.MatrectransActivity;
import com.zcl.hxqh.zhongchuliang.view.activity.StorelocChooseActivity;

import java.util.ArrayList;

/**
 * Created by apple on 15/6/4.
 * 仓库选择
 */
public class StorelocChooseAdapter extends RecyclerView.Adapter<StorelocChooseAdapter.ViewHolder> {

    private static final String TAG = "LocationsAdapter";
    StorelocChooseActivity activity;
    ArrayList<Locations> mItems = new ArrayList<Locations>();

    public StorelocChooseAdapter(StorelocChooseActivity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Locations item = mItems.get(i);
        viewHolder.itemNumTitle.setText(activity.getString(R.string.locations_location_title));
        viewHolder.itemDescTitle.setText(activity.getString(R.string.item_desc_title));
        viewHolder.itemNum.setText(item.location);
        viewHolder.itemDesc.setText(item.description);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MatrectransActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("locations", item.location);
                intent.putExtras(bundle);
                activity.setResult(2, intent);
                activity.finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void update(ArrayList<Locations> data, boolean merge) {
        if (merge && mItems.size() > 0) {
            for (int i = 0; i < mItems.size(); i++) {
                Locations obj = mItems.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).locationsid == obj.locationsid) {
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

    public void adddate(ArrayList<Locations> data){
        if(data.size()>0){
            for(int i = 0;i < data.size();i++){
                if(!mItems.contains(data.get(i))){
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
        public RelativeLayout cardView;
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
            cardView = (RelativeLayout) view.findViewById(R.id.card_container);
            itemNumTitle = (TextView) view.findViewById(R.id.item_num_title);
            itemNum = (TextView) view.findViewById(R.id.item_num_text);
            itemDescTitle = (TextView) view.findViewById(R.id.item_desc_title);
            itemDesc = (TextView) view.findViewById(R.id.item_desc_text);
        }
    }
}
