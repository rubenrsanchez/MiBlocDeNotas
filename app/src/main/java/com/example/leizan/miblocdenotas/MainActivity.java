package com.example.leizan.miblocdenotas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mainRV;
    private NotasAdapter adapter;
    private ArrayList<Nota> notasList; // ArrayList de las notas guardadas
    private ArrayList<CategoriaNota> categoriasList; // ArrayList de todas las categorias creadas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // listener onClick del boton para añadir notas
                // cuando se activa, crea un intent para abrir la activity 'ActivityNota2'. Cuando se cierra ese segundo activity, devuelve la nota creada (solo si se guarda)
                if (categoriasList.size() == 0){ // Si no hay categorias, muestra un Toast al usuario para que cree al menos una. Sin categorias no se pueden crear notas
                    Toast.makeText(MainActivity.this, "ERROR: Para crear una nota, primero tienes que crear una nueva categoría", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, ActivityNota2.class);
                    intent.putExtra("categoriasList", categoriasList);
                    startActivityForResult(intent, 1); // si se llama al otro activity con 'startActivityForResult', tiene que devolver algo obligatoriamente. Lo que devuelve se gestiona en la funcion onActivityResult de esta misma clase
                }
            }
        });
        notasList = new ArrayList<>();
        categoriasList = new ArrayList<>();
        mainRV = findViewById(R.id.rvMain);
        adapter = new NotasAdapter(this, notasList, categoriasList); // crea el adapter
        mainRV.setAdapter(adapter); // asigna el adapter al recyclerView
        mainRV.setLayoutManager(new LinearLayoutManager(this)); // asigna un layoutManager al recyclerView (obligatorio)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_añadirCategoria) { // Si el usuario hace click en 'Añadir categoría', se abre un intent a la activity 'Nueva_categoria'.
                                                // La segunda activity tiene que devolver si o sí un objeto CategoriaNota
            Intent intent = new Intent(this, Nueva_categoria.class);
            startActivityForResult(intent, 3);
        } else if (id == R.id.action_filtrarNotas){
            Log.e("click", "click en el filtro");

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){ // el requestCode se define en los startActivityForResult
            case 1: // CREACIÓN DE NUEVAS NOTAS
                if(resultCode == Activity.RESULT_OK){
                    notasList.add((Nota)data.getSerializableExtra("nota")); // extrae la nota creada en la otra activity y la añade al arrayList
                    adapter.notifyDataSetChanged(); // avisa al adapter que se ha añadido una nota nueva al arrayList para actualizar la vista
                }
                break;
            case 2: // EDICIÓN DE NOTAS CREADAS ANTERIORMENTE
                if(resultCode == Activity.RESULT_OK){
                    Nota n = (Nota)data.getSerializableExtra("nota"); // extrae la nota editada en la otra activity...
                    notasList.set(data.getIntExtra("posicion", -1), n); // ... y machaca la nota original con la nota editada. También le llega la posición que ocupa en el arrayList
                    adapter.notifyDataSetChanged(); // avisa al adapter que se ha añadido una nota nueva al arrayList para actualizar la vista
                }
                break;
            case 3: // CREACIÓN DE NUEVAS CATEGORÍAS
                if(resultCode == Activity.RESULT_OK){
                    categoriasList.add((CategoriaNota)data.getSerializableExtra("CategoriaNota")); // inserta en el arrayList de categorias, el nuevo objeto CategoriaNota creado en la activity 'Nueva_categoria'
                }
                break;
        }
    }
}
