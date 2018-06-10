package com.am.onlinerestaurant.ui.buychat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.base.BaseViewHolderFirebase;
import com.am.onlinerestaurant.data.network.FirebaseEndPoint;
import com.am.onlinerestaurant.models.Cart;
import com.am.onlinerestaurant.models.CartItem;
import com.am.onlinerestaurant.models.Restaurant;
import com.am.onlinerestaurant.ui.buychat.model.BuyMessage;
import com.am.onlinerestaurant.ui.buychat.model.MessageCart;
import com.am.onlinerestaurant.ui.buychat.model.MessageCartItem;
import com.am.onlinerestaurant.ui.buychat.model.MessageItemAdded;
import com.am.onlinerestaurant.ui.buychat.model.MessageText;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private Button btnSend;
    private EditText txtMessage;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<BuyMessage,BaseViewHolderFirebase<BuyMessage>> adapter;
    private Restaurant mRestaurant;
    public static Intent getAndSendCart(Context context, Restaurant restaurant){
        Intent i = new Intent(context,ChatActivity.class);
        i.putExtra("restaurant",restaurant);
        i.putExtra("action","sendCart");
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.recyclerView);
        btnSend= findViewById(R.id.btnSend);
        txtMessage= findViewById(R.id.txtMessage);

        mRestaurant = getIntent().getParcelableExtra("restaurant");



        String key  = "r1u1";
        Query query = FirebaseDatabase.getInstance().getReference().child("chat").child(key);


        SnapshotParser<BuyMessage> parser = new SnapshotParser<BuyMessage>() {
            @NonNull
            @Override
            public BuyMessage parseSnapshot(@NonNull DataSnapshot snapshot) {
                Log.d("tag",snapshot.toString());
                Log.d("tag",snapshot.child("type").getValue().toString());
                switch (snapshot.child("type").getValue().toString()) {
                    case BuyMessage.TYPE_TEXT + "":
                        Log.d("tag", "text");
                        return snapshot.getValue(MessageText.class);
                    case BuyMessage.TYPE_CART + "":
                        return snapshot.getValue(MessageCart.class);
                    case BuyMessage.TYPE_ITEM_ADDED + "":
                        return snapshot.getValue(MessageItemAdded.class);
                }
                    MessageText messageText = new MessageText();
                    messageText.setType(BuyMessage.TYPE_TEXT);
                    messageText.setText("test");
                    messageText.setUid("uid");
                    messageText.setDate(1234);

                return messageText;
            }
        };
        FirebaseRecyclerOptions<BuyMessage> options =
                new FirebaseRecyclerOptions.Builder<BuyMessage>()
                        .setQuery(query,parser)
                        .build();

        adapter = new FirebaseRecyclerAdapter<BuyMessage, BaseViewHolderFirebase<BuyMessage>>(options) {
            static final int TYPE_TEXT = 1;
            static final int TYPE_TEXT_SELF = 2;
            static final int TYPE_CART = 3;
            static final int TYPE_ITEM_ADDED = 4;

            @Override
            protected void onBindViewHolder(@NonNull BaseViewHolderFirebase<BuyMessage> holder, int position, @NonNull BuyMessage model) {
                holder.onBind(model,position);
            }

            @Override
            public int getItemViewType(int position) {
                switch (getItem(position).getType()){
                    case BuyMessage.TYPE_TEXT:{
                        if (((MessageText)getItem(position)).getUid().equals("u1"))
                            return TYPE_TEXT_SELF;
                        return TYPE_TEXT;
                    }
                    case BuyMessage.TYPE_CART:
                        return TYPE_CART;
                    case BuyMessage.TYPE_REPORT:
                        return TYPE_CART;
                    case BuyMessage.TYPE_ITEM_ADDED:
                        return TYPE_ITEM_ADDED;
                }
                return TYPE_TEXT;
            }

            @NonNull
            @Override
            public BaseViewHolderFirebase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = null;
                switch (viewType){
                    case TYPE_TEXT:{
                        view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_message_text, parent, false);
                    return new MessageTextHolder(view);
                    }
                    case TYPE_TEXT_SELF:{
                        view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_message_text_self, parent, false);
                        return new MessageTextHolder(view);
                    }
                    case TYPE_CART:{
                        view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_message_cart, parent, false);
                        return new MessageCartHolder(view);
                    }
                    case TYPE_ITEM_ADDED:{
                        view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_message_item_added, parent, false);
                        return new MessageItemAddedHolder(view);
                    }
                }
                return new MessageTextHolder(view);
            }
        };

        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = adapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);

        btnSend.setOnClickListener(v -> {
            MessageText messageText = new MessageText();
            messageText.setDate(System.currentTimeMillis());
            messageText.setText(txtMessage.getText().toString().trim());
            messageText.setUid("u1");
            messageText.setType(BuyMessage.TYPE_TEXT);
            FirebaseEndPoint.getChatRef(mRestaurant.getId(),"u1")
                    .push().setValue(messageText);
        });



        if(getIntent().getStringExtra("action").equals("sendCart")){
            sendCart();
        }

    }

    private void sendCart() {
        Cart cart = Cart.getCart(this);
        List<MessageCartItem> messageCartItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()){
            MessageCartItem item = new MessageCartItem();
            item.setName(cartItem.getFood().getName());
            item.setItemPrice(cartItem.getFood().getNewPrice());
            item.setQuantity(cartItem.getQuantity());
            item.setMessage(cartItem.getMessage());
            messageCartItems.add(item);
        }

        MessageCart messageCart = new MessageCart();
        messageCart.setItems(messageCartItems);
        messageCart.setType(BuyMessage.TYPE_CART);

        String userId= "u1";
        FirebaseEndPoint.getChatRef(cart.getRestaurant().getId(),userId)
                .push()
                .setValue(messageCart);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
