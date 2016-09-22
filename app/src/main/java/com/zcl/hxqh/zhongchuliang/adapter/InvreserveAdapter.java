package com.zcl.hxqh.zhongchuliang.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Invreserve;
import com.zcl.hxqh.zhongchuliang.view.activity.InvreserveDetailActivity;

import java.util.ArrayList;

/**
 * Created by apple on 15/6/4.
 * 出库管理
 */
public class InvreserveAdapter extends RecyclerView.Adapter<InvreserveAdapter.ViewHolder> {

    private static final String TAG = "InvreserveAdapter";
    Context mContext;
    ArrayList<Invreserve> invreserves = new ArrayList<Invreserve>();
    private String wonum;
    public InvreserveAdapter(Context context, String wonum) {
        mContext = context;
        this.wonum=wonum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Invreserve invreserve = invreserves.get(i);

        viewHolder.itemNumTitle.setText(mContext.getString(R.string.item_num_title));
        viewHolder.itemDescTitle.setText(mContext.getString(R.string.item_desc_title));
        viewHolder.itemNum.setText(invreserve.itemnum);
        viewHolder.itemDesc.setText(invreserve.description);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InvreserveDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("invreserve", invreserve);
                bundle.putString("wonum", wonum);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return invreserves.size();
    }

    public void update(ArrayList<Invreserve> data, boolean merge) {
        if (merge && invreserves.size() > 0) {
            for (int i = 0; i < invreserves.size(); i++) {
                Invreserve obj = invreserves.get(i);
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
        invreserves = data;

        notifyDataSetChanged();
    }

    public void adddate(ArrayList<Invreserve> data){
        if(data.size()>0){
            for(int i = 0;i < data.size();i++){
                if(!invreserves.contains(data.get(i))){
                    invreserves.add(data.get(i));
                }
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
         * 编号标题*
         */
        public TextView itemNumTitle;
        /**
         * 编号*
         */
        public TextView itemNum;
        /**
         * 描述标题*
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
