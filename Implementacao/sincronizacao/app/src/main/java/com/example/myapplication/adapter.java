package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {

    private ArrayList<contato> arrayList = new ArrayList<>();

    public adapter(ArrayList<contato> arrayList){
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemview, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

        viewHolder.name.setText(arrayList.get(i).getName());
        int syncStatus = arrayList.get(i).getSyncStatus();
        if(syncStatus == dbContato.SYNC_STATUS_OK){
            viewHolder.syncStatus.setImageResource(R.drawable.check);
        }else{
            viewHolder.syncStatus.setImageResource(R.drawable.direction);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView syncStatus;
        TextView name;

        public MyViewHolder (View itemView){
            super(itemView);
            syncStatus = (ImageView)itemView.findViewById(R.id.iv);
            name = itemView.findViewById(R.id.tv);

        }
    }

}
