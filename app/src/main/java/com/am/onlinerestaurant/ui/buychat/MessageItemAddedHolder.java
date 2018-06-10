package com.am.onlinerestaurant.ui.buychat;

import android.view.View;
import android.widget.TextView;

import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.base.BaseViewHolderFirebase;
import com.am.onlinerestaurant.ui.buychat.model.MessageItemAdded;

public class MessageItemAddedHolder extends BaseViewHolderFirebase<MessageItemAdded> {
    private TextView tvName,tvQuantity;
    public MessageItemAddedHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        tvQuantity = itemView.findViewById(R.id.tvQuantity);
    }

    @Override
    public void onBind(MessageItemAdded item, int position) {
        tvName.setText(item.getName());
        tvQuantity.setText("Quantity: "+item.getQuantity());
    }
}
