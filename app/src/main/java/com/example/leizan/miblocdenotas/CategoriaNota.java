package com.example.leizan.miblocdenotas;

import java.io.Serializable;

public class CategoriaNota implements Serializable {
    private int id;
    private String nombre;
    private String color;
    public static int idSuma = 0;

    public CategoriaNota(String nombre, String color){
        this.nombre = nombre;
        this.color = color;
        id = idSuma;
        idSuma++;
    }

    public int getId() {
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getColor(){
        return color;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setColor(String color){
        this.color = color;
    }
}

