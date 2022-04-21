package com.example.recipeapplication;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecipeViewHolder> {


    class RecipeViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        private final String extra = "RECIPE_DATA";

        public final TextView recipeItemViewTitle;
        public final TextView recipeItemViewDescription;
        public Recipe item;
        final RecyclerAdapter recyclerAdapter;

        public RecipeViewHolder(View itemView, RecyclerAdapter adapter){
            super(itemView);
            recipeItemViewTitle = itemView.findViewById(R.id.list_title);
            recipeItemViewDescription =itemView.findViewById(R.id.list_description);
            this.recyclerAdapter = adapter;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Context context = this.itemView.getContext();
            Intent intent = new Intent(context, ItemActivity.class);
            if(item != null){
                intent.putExtra(extra, item);
            }
            context.startActivity(intent);
        }
    }

    LinkedList<Recipe> dataRecipe = new LinkedList<>();
    private LayoutInflater mInflater;


    public RecyclerAdapter(Context context, LinkedList<Recipe> recipe){
        mInflater = LayoutInflater.from(context);
        this.dataRecipe = recipe;
    }


    @NonNull
    @Override
    public RecyclerAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new RecipeViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = dataRecipe.get(position);
        holder.item = recipe;
        holder.recipeItemViewTitle.setText(recipe.name);
        holder.recipeItemViewDescription.setText(recipe.description);

    }


    @Override
    public int getItemCount() {
        return dataRecipe.size();
    }
}
