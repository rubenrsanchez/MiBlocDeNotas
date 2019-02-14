package com.example.leizan.miblocdenotas;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.NotasViewHolder> {

    @NonNull
    @Override
    public NotasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NotasViewHolder notasViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NotasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public NotasViewHolder(View itemView){
            super(itemView);

        }
        @Override
        public void onClick(View v) {

        }
    }
}
