package com.example.leizan.miblocdenotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ActivityNota2 extends AppCompatActivity {

    private EditText titulo;
    private EditText text;
    private Button guardar;
    private Button cancelar;
//    private ArrayList notasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota2);
//        notasList = (ArrayList<Nota>) getIntent().getSerializableExtra("notas");
        titulo = findViewById(R.id.editText_titulo);
        text = findViewById(R.id.editText_text);
        guardar = findViewById(R.id.button_guardar);
        guardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // aqui crea una nota y la devuelve al arraylist del mainactivity
                Intent intent = new Intent(ActivityNota2.this, MainActivity.class);
                intent.putExtra("nota" , new Nota(titulo.getText().toString(), text.getText().toString(), new CategoriaNota(1,"hola", 2)));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        cancelar = findViewById(R.id.button_cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cierra la activity y vuelve a la anterior sin crear ninguna nota
            }
        });

    }
}
