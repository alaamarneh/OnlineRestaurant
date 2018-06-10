package com.am.onlinerestaurant.adapters;

import android.view.View;
import android.widget.Checkable;
import android.widget.RadioButton;
import android.widget.TextView;

import com.am.onlinerestaurant.R;
import com.am.onlinerestaurant.common.abstractAdapters.Holder;
import com.am.onlinerestaurant.common.abstractAdapters.RecyclerAdapter;
import com.am.onlinerestaurant.models.Food;

import java.util.List;

public class FoodCatAdapter extends RecyclerAdapter<Food>{
    private CatHolder mCatHolderSelected;

    public Food getSelectedFoodCat(){
        return mCatHolderSelected == null?null:mCatHolderSelected.getItem();
    }
    public class CatHolder extends Holder<Food> implements Checkable{
        private RadioButton radioButton;
        private TextView tvName,tvPrice;
        private View layoutHolder;
        public CatHolder(View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            layoutHolder = itemView.findViewById(R.id.layoutHolder);

            layoutHolder.setOnClickListener(v -> {
                if(mCatHolderSelected != null)
                    mCatHolderSelected.setChecked(false);
                mCatHolderSelected  = CatHolder.this;
                mCatHolderSelected.setChecked(true);
            });
            radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked){
                    if(mCatHolderSelected != null)
                        mCatHolderSelected.setChecked(false);
                    mCatHolderSelected  = CatHolder.this;
                    mCatHolderSelected.setChecked(true);
                }

            });

        }

        @Override
        public void bind(Food item, int pos) {
            super.bind(item, pos);
            tvName.setText(item.getName());
            tvPrice.setText(item.getNewPrice() + "NIS");
        }

        public Food getItem(){
            return mItem;
        }

        @Override
        public void setChecked(boolean checked) {
            radioButton.setChecked(checked);
        }

        @Override
        public boolean isChecked() {
            return radioButton.isChecked();
        }

        @Override
        public void toggle() {

        }
    }
    public FoodCatAdapter(List<Food> items) {
        super(items);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_food_cat;
    }

    @Override
    public Holder getNewHolder(View v) {
        return new CatHolder(v);
    }
}
