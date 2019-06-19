package com.tuodanhuashu.app.course.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.course.bean.LoveGoodBean;
import com.tuodanhuashu.app.course.ui.OrderActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoveGoodAdapter extends RecyclerView.Adapter<LoveGoodAdapter.LoveGoodHolder> {

    private OrderActivity context;
    private List<LoveGoodBean> loveGoodBeanList;
    private Map<Integer, Boolean> map = new HashMap<>();

    public LoveGoodAdapter(OrderActivity context, List<LoveGoodBean> loveGoodBeanList) {
        this.context = context;
        this.loveGoodBeanList = loveGoodBeanList;

        for (int i = 0; i < loveGoodBeanList.size(); i++) {
            if (i == 0){
                map.put(i,true);
            }else {
                map.put(i,false);
            }
        }
    }

    @NonNull
    @Override
    public LoveGoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_love_good, parent, false);
        LoveGoodHolder holder = new LoveGoodHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LoveGoodHolder holder, int position) {
        final LoveGoodBean loveGoodBean = loveGoodBeanList.get(position);


        holder.tvGoodCount.setText(loveGoodBean.getLove_count() + "恋爱币");
        holder.tvGoodPrice.setText("￥" + loveGoodBean.getPrice());
        holder.tvGoodName.setText(loveGoodBean.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getLayoutPosition();
                context.setCurrentGoodId(loveGoodBean.getId());
                for (Integer key : map.keySet()) {
                    map.put(key, false);
                }
                map.put(currentPosition, true);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setSelected(map.get(position));
    }

    @Override
    public int getItemCount() {
        return loveGoodBeanList.size();
    }

    class LoveGoodHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_good_love_count)
        TextView tvGoodCount;
        @BindView(R.id.tv_good_name)
        TextView tvGoodName;
        @BindView(R.id.tv_good_price)
        TextView tvGoodPrice;

        public LoveGoodHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
