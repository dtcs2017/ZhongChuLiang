package com.zcl.hxqh.zhongchuliang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Po;

import java.util.ArrayList;

/**
 * Created by apple on 15/6/4.
 */
public class PoAdapter extends RecyclerView.Adapter<PoAdapter.ViewHolder> {

    private static final String TAG = "PoAdapter";
    Context mContext;
    ArrayList<Po> mPos = new ArrayList<Po>();

    public PoAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Po po = mPos.get(i);


        viewHolder.itemNum.setText(po.ponum);
        viewHolder.itemDesc.setText(po.description);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, PodetailsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("po", po);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mPos.size();
    }

    public void update(ArrayList<Po> data, boolean merge) {
        if (merge && mPos.size() > 0) {
            for (int i = 0; i < mPos.size(); i++) {
                Log.i(TAG, "mItems=" + mPos.get(i).poid);
                Po obj = mPos.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).poid == obj.poid) {
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

    public void adddate(ArrayList<Po> data) {
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (!mPos.contains(data.get(i))) {
                    mPos.add(data.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    public void update(Po po) {
        for (int i = 0; i < mPos.size(); i++) {
            if (mPos.get(i).ponum.equals(po.ponum)) {
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
