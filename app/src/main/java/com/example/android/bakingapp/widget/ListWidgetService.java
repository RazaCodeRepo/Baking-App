package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.bake.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kontrol on 11/16/2017.
 */

public class ListWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
        List<Ingredient> ingredientList;

        public ListRemoteViewsFactory(Context applicationContext){
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            String temp = PreferenceManager.getDefaultSharedPreferences(mContext).getString("SELECTED_INGREDIENT", "NO show");
            Gson gson = new Gson();
            //List<Ingredient> templist = new ArrayList<Ingredient>();
            Type type = new TypeToken<List<Ingredient>>() {}.getType();
            ingredientList = gson.fromJson(temp, type);

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_list_item);
            rv.setTextViewText(R.id.tv_ing_name, ingredientList.get(i).getName());

            rv.setTextViewText(R.id.tv_ing_quantity, String.valueOf(ingredientList.get(i).getQuantity()));
            rv.setTextViewText(R.id.tv_ing_metric, ingredientList.get(i).getMetric());

            Bundle bundle = new Bundle();
            bundle.putString("ING_NAME", ingredientList.get(i).getName());
            bundle.putInt("ING_QUANTITY", ingredientList.get(i).getQuantity());
            bundle.putString("ING_METRIC", ingredientList.get(i).getMetric());

            Intent fillInIntent = new Intent();
            fillInIntent.putExtra("EXTRA_WIDGET_BUNDLE", bundle );
            rv.setOnClickFillInIntent(R.id.ingredient_list_item, fillInIntent);

            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
