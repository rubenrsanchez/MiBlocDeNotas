package com.example.leizan.miblocdenotas;

import android.graphics.Color;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Nota implements Serializable {
    private String titulo;
    private String text;
    private Calendar calendar;
    private CategoriaNota categoria;

    public Nota(String titulo, String text, CategoriaNota categoria){
        this.titulo = titulo;
        this.text = text;
        this.categoria = categoria;
        calendar = Calendar.getInstance();
    }

    public String getTitulo(){
        return titulo;
    }

    public String getText() {
        return text;
    }

    public Calendar getCalendar(){
        return calendar;
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