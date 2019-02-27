package com.example.leizan.miblocdenotas;

import java.io.Serializable;

public class CategoriaNota implements Serializable {
    private int id;
    private String nombre;
    private int color;
    private static int idsuma=0;

    public CategoriaNota( String nombre, int color){

        this.nombre = nombre;
        this.color = color;
        idsuma ++;
        id=idsuma;
    }

    public int getId() {
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public int getColor(){
        return color;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setColor(int color){
        this.color = color;
    }
}

