package com.example.leizan.miblocdenotas;

import android.graphics.Color;

import org.w3c.dom.Text;

public class Nota {
    private String titulo;
    private String text;
    private CategoriaNota categoria;

    public Nota(String titulo, String text, CategoriaNota categoria){
        this.titulo = titulo;
        this.text = text;
        this.categoria = categoria;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getText() {
        return text;
    }

    public CategoriaNota getCategoria() {
        return categoria;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCategoria(CategoriaNota categoria) {
        this.categoria = categoria;
    }
}