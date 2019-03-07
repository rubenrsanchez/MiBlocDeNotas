package com.example.leizan.miblocdenotas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mainRV;
    private NotasAdapter adapter;
    private ArrayList<Nota> notasList; // ArrayList de las notas guardadas
    private ArrayList<CategoriaNota> categoriasList; // ArrayList de todas las categorias creadas
    private ArrayList<Nota> notasFiltrar;
    private ArrayList<CategoriaNota> categoriasFiltrar;
    private File archivoNota;  //file para el archivo donde meteremos las notas y categorias
    private File directorioGuardar;  //file para la ruta donde creamos un directorio para meter el archivo

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
                if (categoriasList.size() == 0) { // Si no hay categorias, muestra un Toast al usuario para que cree al menos una. Sin categorias no se pueden crear notas
                    Toast.makeText(MainActivity.this, "ERROR: Para crear una nota, primero tienes que crear una nueva categoría", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, ActivityNota2.class);
                    intent.putExtra("categoriasList", categoriasList);
                    startActivityForResult(intent, 1); // si se llama al otro activity con 'startActivityForResult', tiene que devolver algo obligatoriamente. Lo que devuelve se gestiona en la funcion onActivityResult de esta misma clase
                }
            }
        });
        notasList = new ArrayList<>();
        notasFiltrar = new ArrayList<>();
        categoriasList = new ArrayList<>();
        categoriasFiltrar = new ArrayList<>();
        mainRV = findViewById(R.id.rvMain);
        adapter = new NotasAdapter(MainActivity.this, notasList, categoriasList);
        mainRV.setAdapter(adapter); // asigna el adapter al recyclerView
        mainRV.setLayoutManager(new LinearLayoutManager(this)); // asigna un layoutManager al recyclerView (obligatorio)
        directorioGuardar = new File("/data/data/com.example.leizan.miblocdenotas/guardar/");
        archivoNota = new File(directorioGuardar + "notasGuardadas.txt");
        try {
            leerNotasArchivo();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void leerNotasArchivo() throws FileNotFoundException {
        ObjectInputStream leerNota = null;
        ObjectInputStream leercategoria = null;

        if (archivoNota.exists() && directorioGuardar.exists()) {
            try {
                leerNota = new ObjectInputStream(new FileInputStream("/data/data/com.example.leizan.miblocdenotas/guardar/notasGuardadas.txt"));
                leercategoria = new ObjectInputStream(new FileInputStream("/data/data/com.example.leizan.miblocdenotas/guardar/notasGuardadas.txt"));

                // Se lee el primer objeto y casteamos para que identifique si esuna nota o una categoria
                Object objeto = null;
                try {
                    objeto = leerNota.readObject();
                    if (objeto instanceof Nota) {
                        notasList.add((Nota) objeto);
                    } else if (objeto instanceof CategoriaNota) {
                        categoriasList.add((CategoriaNota) objeto);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();

                }

// Mientras haya objetos
                while (objeto != null)
                    try {
                        objeto = leerNota.readObject();
                        if (objeto instanceof Nota) {

                            notasList.add((Nota) objeto);
                        } else if (objeto instanceof CategoriaNota) {

                            categoriasList.add((CategoriaNota) objeto);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                leercategoria.close();
                leerNota.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            try {
                directorioGuardar.mkdir();
                archivoNota.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void escribirNotaArchivo() throws FileNotFoundException {
        try {
            ObjectOutputStream escribe = new ObjectOutputStream(new FileOutputStream("/data/data/com.example.leizan.miblocdenotas/guardar/notasGuardadas.txt"));

            for (int i = 0; i < notasList.size(); i++) {
                escribe.writeObject(notasList.get(i));
            }

            for (int i = 0; i < categoriasList.size(); i++) {
                escribe.writeObject(categoriasList.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            escribirNotaArchivo();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); // Extraemos el id del MenuItem que ha seleccionado el usuario

        // Si el usuario hace click en 'Añadir categoría', se abre un intent a la activity 'Nueva_categoria'.
        // La segunda activity tiene que devolver si o sí un objeto CategoriaNota
        if (id == R.id.action_añadirCategoria) {
            Intent intent = new Intent(this, Nueva_categoria.class);
            startActivityForResult(intent, 3);
        }
        // Si el usuario hace click en 'Filtrar', se genera un AlertDialog donde se muestran todas las categorías que el propio usuario ha creado, para que pueda filtrar sus notas
        else if (id == R.id.action_filtrarNotas) {
            categoriasFiltrar.clear(); // Limpiamos el arrayList cada vez que se quiera filtrar, para reiniciar los filtros
            new AlertDialog.Builder(this)
                    .setTitle("Escoge una categoría") // Título del AlertDialog
                    .setMultiChoiceItems(nombresCategorias(), null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) { // Cuando el usuario checkea una opción, se añade la categoría que ha elegido al arrayList 'categoriasFiltrar'
                                categoriasFiltrar.add(categoriasList.get(which));
                            } else if (categoriasFiltrar.contains(categoriasList.get(which))) { // Si la descheckea, se tiene que eliminar del arrayList
                                categoriasFiltrar.remove(buscaCategoria(categoriasList.get(which).getId()));
                            }
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { // Se ejecuta cuando el usuario hace click en 'OK'
                            // Escogemos las notas que el usuario quiere ver con el método filtrarNotas() e instanciamos un nuevo Adapter, al que le enviaremos el arrayList de
                            // notas filtradas junto al arrayList de todas las categorías. Posteriormente asignamos al recyclerView este adapter.
                            filtrarNotas();
                            adapter = new NotasAdapter(MainActivity.this, notasFiltrar, categoriasList);
                            mainRV.setAdapter(adapter);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { // Si el usuario clicka en 'Cancelar', no se ejecuta ningún cambio en el recyclerView ni se filtra ninguna nota
                            dialog.cancel();
                        }
                    }).show();
        }
        // Si el usuario hace click en 'Quitar filtro', instanciamos de nuevo nuestro adapter pero pasándole TODAS las notas y categorías (si tenía un filtro activado, el adapter
        // tiene solo las notas que quiere ver el usuario). Posteriormente se asigna este adapter al recyclerView.
        else if (id == R.id.action_quitarFiltro) {
            categoriasFiltrar.clear();
            notasFiltrar.clear();
            adapter = new NotasAdapter(this, notasList, categoriasList);
            mainRV.setAdapter(adapter);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param id    ID de la categoría que tenemos que buscar en el arrayList 'categoriasFiltrar'
     * @return      La posición en el arrayList de la categoría que buscamos
     */
    public int buscaCategoria(int id){
        for (int i = 0; i < categoriasFiltrar.size(); i++) {
            if (categoriasFiltrar.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    /**
     * Recorre el arrayList 'categoriasList' y extrae todos los nombres de las categorias en un array de String
     * @return  array de String con los nombres de todas las categorias creadas por el usuario
     */
    public String[] nombresCategorias() {
        String[] nombresCat = new String[categoriasList.size()];
        for (int i = 0; i < categoriasList.size(); i++) {
            nombresCat[i] = categoriasList.get(i).getNombre();
        }
        return nombresCat;
    }

    /**
     * Rellena el arrayList 'notasFiltrar' solo con las notas que tengan las categorías determinadas por el usuario (guardadas en 'categoriasFiltrar').
     * Previamente tenemos que limpiar el arrayList por si contiene información de algún filtro anterior.
     * Con el primer for, recorremos el arrayList 'notasList'. En cada iteración se comprueba si la nota pertenece a una de las categorías escogidas previamente.
     * Si los id de las categorías coinciden, se añade la nota a 'notasFiltrar'.
     */
    public void filtrarNotas() {
        notasFiltrar.clear();
        for (int i = 0; i < notasList.size(); i++) {
            for (int j = 0; j < categoriasFiltrar.size(); j++) {
                if (notasList.get(i).getCategoria().getId() == categoriasFiltrar.get(j).getId()) {
                    notasFiltrar.add(notasList.get(i));
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) { // el requestCode se define en los startActivityForResult
            case 1: // CREACIÓN DE NUEVAS NOTAS
                if (resultCode == Activity.RESULT_OK) {
                    notasList.add((Nota) data.getSerializableExtra("nota")); // extrae la nota creada en la otra activity y la añade al arrayList
                    adapter.notifyDataSetChanged(); // avisa al adapter que se ha añadido una nota nueva al arrayList para actualizar la vista
                }
                break;
            case 2: // EDICIÓN DE NOTAS CREADAS ANTERIORMENTE
                if (resultCode == Activity.RESULT_OK) {
                    Nota n = (Nota) data.getSerializableExtra("nota"); // extrae la nota editada en la otra activity...
                    notasList.set(data.getIntExtra("posicion", -1), n); // ... y machaca la nota original con la nota editada. También le llega la posición que ocupa en el arrayList
                    adapter.notifyDataSetChanged(); // avisa al adapter que se ha modificado una nota para actualizar la vista
                }
                break;
            case 3: // CREACIÓN DE NUEVAS CATEGORÍAS
                if (resultCode == Activity.RESULT_OK) {
                    categoriasList.add((CategoriaNota) data.getSerializableExtra("CategoriaNota")); // inserta en el arrayList de categorias, el nuevo objeto CategoriaNota creado en la activity 'Nueva_categoria'
                }
                break;
        }
    }
}