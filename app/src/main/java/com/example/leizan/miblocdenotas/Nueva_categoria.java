package com.example.leizan.miblocdenotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Nueva_categoria extends AppCompatActivity {

    public static final String vcategoria = "com.example.android.MiBlocDeNotas.vcategoria";
    public static final String vcolor = "com.example.android.MiBlocDeNotas.vcolor";
    private EditText categoria;
    private EditText color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_categoria);

        categoria= findViewById(R.id.tvNuevaC);
        color= findViewById(R.id.NColor);

    }


    public void guardarCat(View view) {
        Intent intento = new Intent(this,MainActivity.class);
        String cat= categoria.getText().toString();
        String num=color.getText().toString();
        intento.putExtra(vcategoria,cat);
        intento.putExtra(vcolor,num);
        startActivity(intento);

    }

    public void cancel(View view) {
        Intent intento = new Intent(this,MainActivity.class);
        startActivity(intento);
    }
}
