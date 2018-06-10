package com.am.onlinerestaurant.ui.buychat;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.base.BaseViewHolder;
import com.am.onlinerestaurant.base.BaseViewHolderFirebase;
import com.am.onlinerestaurant.ui.buychat.model.MessageCart;
import com.am.onlinerestaurant.ui.buychat.model.MessageCartItem;

import java.util.List;

public class MessageCartHolder extends BaseViewHolderFirebase<MessageCart> {
    private RecyclerView recyclerView;
    private TextView tvTotal;
    public MessageCartHolder(View itemView) {
        super(itemView);
        recyclerView=itemView.findViewById(R.id.recyclerView);
        tvTotal=itemView.findViewById(R.id.tvTotal);
    }

    @Override
    public void onBind(MessageCart item, int position) {
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        recyclerView.setAdapter(new MessageCartAdapter(item.getItems()));
        double sum =0;
        for (MessageCartItem cartItem:
             item.getItems()) {
            sum += cartItem.getItemPrice() * cartItem.getQuantity();
        }
        tvTotal.setText(sum+"");
    }
    class MessageCartAdapter extends RecyclerView.Adapter<BaseViewHolder>{
        private List<MessageCartItem> list;

        public MessageCartAdapter(List<MessageCartItem> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_cart_item, parent, false);
            return new MessageCartItemHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            holder.onBind(position);
        }

        @Override
        public int getItemCount() {
            return list!=null?list.size():0;
        }
        class MessageCartItemHolder extends BaseViewHolder{
            private TextView tvName,tvQuantity;
            public MessageCartItemHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvQuantity = itemView.findViewById(R.id.tvQuantity);
            }

            @Override
            public void onBind(int position) {
                tvName.setText(list.get(position).getName());
                tvQuantity.setText("Count: "+list.get(position).getQuantity());
            }
        }
    }
}
