package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.bake.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kontrol on 9/22/2017.
 */

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;

    Context mContext;

    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener{
        void onListItemClickListener(int clickedItemIndex);
    }

    public RecipeRecyclerAdapter(Context context, List<Recipe> mRecipeList, ListItemClickListener listener){
        recipeList = mRecipeList;
        mOnClickListener = listener;
        mContext = context;
    }

    @Override
    public RecipeRecyclerAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecipeRecyclerAdapter.RecipeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }



     public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         @BindView(R.id.tv_recipe_name_item) TextView recipeItemView;
         @BindView(R.id.tv_recipe_serving) TextView recipeServing;
         @BindView(R.id.recipe_image) ImageView recipeImageView;
         @BindView(R.id.card_view) CardView recipeCardView;


        public RecipeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

         void bind(int listIndex){
            recipeItemView.setText(recipeList.get(listIndex).getRecipeName());
            recipeServing.setText(String.valueOf(recipeList.get(listIndex).getRecipeServings()));
             int recipeId = recipeList.get(listIndex).getRecipeID();
             String recipeImage = recipeList.get(listIndex).getImageURL();
             if(!TextUtils.isEmpty(recipeImage)){
                 Picasso.with(mContext).load(recipeImage).into(recipeImageView);
             } else {
                 switch(recipeId){
                     case 1:
                         recipeImageView.setImageResource(R.drawable.nutella_pie);
                         break;
                     case 2:
                         recipeImageView.setImageResource(R.drawable.brownies);
                         break;
                     case 3:
                         recipeImageView.setImageResource(R.drawable.yellow_cake);
                         break;
                     case 4:
                         recipeImageView.setImageResource(R.drawable.cheese_cake);
                         break;
                 }

             }

        }

         @Override
         public void onClick(View view) {
             int clickedPosition = getAdapterPosition();
             mOnClickListener.onListItemClickListener(clickedPosition);
         }
     }
}
