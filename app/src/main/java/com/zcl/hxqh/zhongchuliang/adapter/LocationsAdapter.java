package com.zcl.hxqh.zhongchuliang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Locations;

import java.util.ArrayList;

/**
 * Created by apple on 15/6/4.
 * 库存转移
 */
public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    private static final String TAG = "LocationsAdapter";
    Context mContext;
    ArrayList<Locations> mItems = new ArrayList<Locations>();
    int mark=0; //库房标识
    public LocationsAdapter(Context context, int mark) {
        mContext = context;
        this.mark=mark;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Locations item = mItems.get(i);
        viewHolder.itemNumTitle.setText(mContext.getString(R.string.locations_location_title));
        viewHolder.itemDescTitle.setText(mContext.getString(R.string.item_desc_title));
        viewHolder.itemNum.setText(item.location);
        viewHolder.itemDesc.setText(item.description);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent();
//                if(mark==0){
//                    intent.setClass(mContext, LocationsDetailActivity.class);
//                }else{
//                    intent.setClass(mContext, CInvbalancesActivity.class);
//                }
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("locations", item);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
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
