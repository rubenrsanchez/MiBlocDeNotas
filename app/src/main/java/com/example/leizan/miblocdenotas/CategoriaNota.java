package com.example.leizan.miblocdenotas;

public class CategoriaNota {
    private int id;
    private String nombre;
    private int color;

    public CategoriaNota(int id, String nombre, int color){
        this.id = id;
        this.nombre = nombre;
        this.color = color;
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

