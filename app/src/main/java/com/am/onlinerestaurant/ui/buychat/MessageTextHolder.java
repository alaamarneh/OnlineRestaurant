package com.am.onlinerestaurant.ui.buychat;

import android.view.View;
import android.widget.TextView;

import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.base.BaseViewHolderFirebase;
import com.am.onlinerestaurant.ui.buychat.model.MessageText;


public class MessageTextHolder extends BaseViewHolderFirebase<MessageText> {
    private TextView tvMessage;
    public MessageTextHolder(View itemView) {
        super(itemView);
        tvMessage=itemView.findViewById(R.id.tvMessage);
    }

    @Override
    public void onBind(MessageText item, int position) {
        tvMessage.setText(item.getText());
    }
}
