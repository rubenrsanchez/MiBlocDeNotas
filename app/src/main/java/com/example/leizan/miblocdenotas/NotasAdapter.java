package com.example.leizan.miblocdenotas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
        // en esta funcion se define la informacion de cada cardView
        Nota currentNota = notas.get(i);
        // Añade la información al viewHolder
        holder.titulo.setText(currentNota.getTitulo()); //titulo de la currentNota
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        holder.dateTime.setText(sdf.format(currentNota.getCalendar().getTime())); //hora de creación de la currentNota
        holder.layout.setBackgroundColor(Color.parseColor(notas.get(i).getCategoria().getColor())); //color de la currentNota
     }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    class NotasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titulo;
        public TextView dateTime;
        public NotasAdapter adapter;
        public RelativeLayout layout;

        public NotasViewHolder(View itemView, NotasAdapter adapter){ // se instancian los elementos de cada cardView
            super(itemView);
            titulo = itemView.findViewById(R.id.TVNota);
            dateTime = itemView.findViewById(R.id.tv_dateTime);
            layout = itemView.findViewById(R.id.relativeLayout_nota);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            // onClick de cada cardView, la idea es que cuando se haga click se abra 'ActivityNota2' con la info de la nota cargada para poder editarla
            Log.i("info", "click en el cardview");
            int posicionElemento = getLayoutPosition();
            Intent intent = new Intent(v.getContext(), ActivityNota2.class);
            intent.putExtra("notaEditar", notas.get(posicionElemento));
            intent.putExtra("posicion", posicionElemento);
            intent.putExtra("categoriasList", categorias);
            ((Activity)v.getContext()).startActivityForResult(intent, 2);
        }
    }
}