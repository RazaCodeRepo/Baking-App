package com.example.android.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.bake.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kontrol on 9/28/2017.
 */

public class IngredientRecyclerAdapter extends RecyclerView.Adapter<IngredientRecyclerAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;



    public IngredientRecyclerAdapter(List<Ingredient> ingredients){
        ingredientList = ingredients;

    }

    @Override
    public IngredientRecyclerAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_item, parent, false);
        return new IngredientViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(IngredientRecyclerAdapter.IngredientViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }


    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ing_name) TextView ingredientName;
        @BindView(R.id.tv_ing_metric) TextView ingredientMetric;
        @BindView(R.id.tv_ing_quantity) TextView ingredientQuantity;


        public IngredientViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void bind(int listIndex){
            ingredientName.setText(ingredientList.get(listIndex).getName());
            ingredientMetric.setText(ingredientList.get(listIndex).getMetric());
            ingredientQuantity.setText(String.valueOf(ingredientList.get(listIndex).getQuantity()));
        }


    }

}
