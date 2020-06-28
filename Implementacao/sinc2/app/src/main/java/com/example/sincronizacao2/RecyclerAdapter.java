package com.example.sincronizacao2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<Contato> arrayList = new ArrayList<>();


    public RecyclerAdapter( ArrayList<Contato> arrayList){
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {

        viewHolder.name.setText(arrayList.get(i).getNome());
        int syncStatus = arrayList.get(i).getSyncStatus();
        if(syncStatus == DbContract.SYNC_STATUS_OK){
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
            syncStatus = itemView.findViewById(R.id.iv);
            name = itemView.findViewById(R.id.tv);

        }
    }
}
