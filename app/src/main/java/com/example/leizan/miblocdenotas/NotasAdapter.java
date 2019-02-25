package com.example.leizan.miblocdenotas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.NotasViewHolder> {

    private final ArrayList<Nota> notas;
    private final ArrayList<CategoriaNota> categorias;
    private LayoutInflater inflater;

    public NotasAdapter(Context context, ArrayList notas, ArrayList categorias){
        this.notas = notas;
        this.categorias = categorias;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NotasAdapter.NotasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.element_nota, viewGroup, false);
        return new NotasViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(NotasAdapter.NotasViewHolder holder, int i) {
        Nota currentNota = notas.get(i);
        // Add the data to the view holder.
        holder.titulo.setText(currentNota.getText());

    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    class NotasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView titulo;
        final NotasAdapter adapter;

        public NotasViewHolder(View itemView, NotasAdapter adapter){
            super(itemView);
            titulo = itemView.findViewById(R.id.TVNota);
            this.adapter = adapter;
        }
        @Override
        public void onClick(View v) {

        }
    }
}
