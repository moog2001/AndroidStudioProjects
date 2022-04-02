package com.example.todo2;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<ToDoItem> ItemList;
    private DataBaseHandler Data;
    private int ItemIndex;
    public Context ParentContext;

    public RecyclerAdapter(ArrayList<ToDoItem> itemList, DataBaseHandler data, Context parentContext) {
        Data = data;
        ItemList = itemList;
        ParentContext = parentContext;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView toDoText;
        private ImageView imgDate;
        private ImageView imgDelete;
        private CheckBox cbCheckBox;

        public MyViewHolder(final View viewMain) {
            super(viewMain);
            cbCheckBox = viewMain.findViewById(R.id.cbStatus);
            toDoText = viewMain.findViewById(R.id.tvItem);
            imgDate = viewMain.findViewById(R.id.imgDate);
            imgDelete = viewMain.findViewById(R.id.imgDelete);

            MyViewHolder item = this;
            viewMain.setOnClickListener(new View.OnClickListener() {
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
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(ParentContext)
                        .setTitle("Delete Item?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d("test", String.valueOf(holder.getAdapterPosition()));
                                Data.deleteData(ItemList.get(holder.getAdapterPosition()));
                                ItemList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });



        MyViewHolder viewHolder = (MyViewHolder) holder;

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogBuilder.show();


//                Log.d("test", String.valueOf(holder.getAdapterPosition()));
//                Data.deleteData(ItemList.get(holder.getAdapterPosition()));
//                ItemList.remove(holder.getAdapterPosition());
//                notifyItemRemoved(holder.getAdapterPosition());


//                notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount());


            }
        });

    }

    @Override
    public int getItemCount() {
        if (ItemList == null) {
            return 0;
        }
        return ItemList.size();
    }


}
