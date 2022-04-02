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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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
            viewMain.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // TODO Auto-generated method stub
                    return true;
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
        ToDoItem Item = ItemList.get(holder.getAdapterPosition());
        String toDo = ItemList.get(position).getToDO();

        holder.toDoText.setText(toDo);
        if(Item.getStatus()==1){
            holder.cbCheckBox.setChecked(true);
        }else{
            holder.cbCheckBox.setChecked(false);
        }

        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(ParentContext)
                        .setTitle("Delete Item?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d("test", String.valueOf(holder.getAdapterPosition()));
                                Data.deleteData(Item);
                                ItemList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        AlertDialog.Builder alertDialogBuilder1 =
                new AlertDialog.Builder(ParentContext)
                        .setTitle("End Date");
        TextView tvDate = new TextView(alertDialogBuilder1.getContext());
        tvDate.setText(Item.getEndDate().toString());
        alertDialogBuilder1.setView(tvDate);
        tvDate.setPadding(100, 0, 0, 0);

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

        viewHolder.imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder1.show();
            }
        });

        viewHolder.cbCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) viewHolder.cbCheckBox).isChecked();

                if (checked) {
                    Log.d("check", "checked!");
                    Item.setStatus(1);

                }
                else{
                    Log.d("check", "not checked!");

                    Item.setStatus(0);
                }
                Data.updateData(Item);

                // Check which checkbox was clicked
            }
        });

        EditText editTextToDo = new EditText(ParentContext);
        DatePicker datePicker = new DatePicker(ParentContext);

        LinearLayout container = new LinearLayout(ParentContext);
        container.addView(editTextToDo);
        container.addView(datePicker);
        container.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder alertDialogBuilder2 =
                new AlertDialog.Builder(ParentContext)
                        .setTitle("Change To Do")
                        .setView(container)
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if(editTextToDo.getText() == null){
                                    return;
                                }
                                Item.setToDO(editTextToDo.getText().toString());

                                int day = datePicker.getDayOfMonth();
                                int month = datePicker.getMonth()+1;
                                int year = datePicker.getYear();

                                LocalDate date = LocalDate.of(year, month, day);

                                Item.setEndDate(date);

                                Data.updateData(Item);
                                notifyItemChanged(holder.getAdapterPosition());

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        viewHolder.toDoText.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                Log.d("tv", "clicked");
                alertDialogBuilder2.show();
                return true;
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
