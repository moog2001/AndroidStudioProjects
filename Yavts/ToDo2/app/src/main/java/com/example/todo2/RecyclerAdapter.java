package com.example.todo2;

import android.content.ClipData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<ToDoItem> ItemList;
    private MainActivity Parent;
    private int ItemIndex;

    public RecyclerAdapter(ArrayList<ToDoItem> itemList, MainActivity parent ){
        Parent = parent;
        ItemList = itemList;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView toDoText;
        public MyViewHolder(final View view){
            super(view);
            toDoText = view.findViewById(R.id.tvItem);
            MyViewHolder item = this;


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("position recycle", String.valueOf(item.getLayoutPosition()));
                    ItemIndex = item.getLayoutPosition();
                }
            });


        }


    }


    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String toDo = ItemList.get(position).getToDO();
        holder.toDoText.setText(toDo);
    }

    @Override
    public int getItemCount() {
        if(ItemList == null){
            return 0;
        }
        return ItemList.size();
    }


}
