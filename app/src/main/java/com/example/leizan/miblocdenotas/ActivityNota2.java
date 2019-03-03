package com.example.leizan.miblocdenotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityNota2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText titulo;
    private EditText text;
    private Button guardar;
    private Button cancelar;

    private Nota nota;
    private CategoriaNota categoriaEscogida;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private int posicion;
    private String[] nombresCategorias;
    ArrayList<CategoriaNota> categoriasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota2);
        setTitle(R.string.title_ActivityNota2); // Asigna el título de la activity
        /*
            Instancia los componentes del layout
        */
        titulo = findViewById(R.id.editText_titulo);
        text = findViewById(R.id.editText_text);
        spinner = findViewById(R.id.spinner_categorias);
        guardar = findViewById(R.id.button_guardar);
        cancelar = findViewById(R.id.button_cancelar);
        /*
            Añade los listeners a los botones 'Guardar' y 'Cancelar' del layout
        */
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityNota2.this, MainActivity.class);
                /*
                    Si el objeto 'nota' es nulo, quiere decir que estamos creando una nota nueva, por lo que al clickar al botón de guardar
                    enviaremos al MainActivity la nota nueva que el usuario ha creado, y la guardará en el arrayList
                    Si no es nulo, quiere decir que estamos editando una nota ya creada anteriormente, por lo que al clickar al botón de
                    guardar actualizaremos los valores del objeto 'nota' en cuestión y la enviaremos al MainActivity junto a la posición
                    que ocupa en el arrayList.
                 */
                if (nota == null) {
                    intent.putExtra("nota", new Nota(titulo.getText().toString(), text.getText().toString(), categoriaEscogida)); // añade la nota creada por el usuario al intent
                } else {
                    nota.setTitulo(titulo.getText().toString());
                    nota.setText(text.getText().toString());
                    nota.setCategoria(categoriaEscogida);
                    intent.putExtra("nota", nota); // añade la nota creada por el usuario al intent
                    intent.putExtra("posicion", posicion);
                }
                setResult(RESULT_OK, intent); // envia el intent al mainActivity con la nota que hemos creado
                finish();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    Cierra la activity y vuelve a la anterior sin crear / modificar ninguna nota
                 */
                Intent intent = new Intent(ActivityNota2.this, MainActivity.class);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
        /*
            Comprueba si el intent que ha abierto este activity contiene los extras que nos interesan (solo le llegaran en el caso
            que queramos editar una nota).
            Si el intent contiene una nota y una posición, quiere decir que vamos a editarla y a devolverla al arrayList del MainActivity.
            Si no, es que el usuario va a crear una nueva nota.
        */
        Intent intent = getIntent();
        categoriasList = (ArrayList<CategoriaNota>) intent.getSerializableExtra("categoriasList"); // Extrae del intent la lista con todos los objetos CategoriaNota
        nombresCategorias = new String[categoriasList.size()]; // En este array se guardarán todos los nombres de las categorías, en función del arrayList anterior. Servirá para cargar el spinner
        for (int i = 0; i < categoriasList.size(); i++) {
            nombresCategorias[i] = categoriasList.get(i).getNombre();
        }
        spinner.setOnItemSelectedListener(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombresCategorias); // Crea el adapter con el array de nombres de las categorías
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (intent.hasExtra("notaEditar")) {
            nota = (Nota) intent.getSerializableExtra("notaEditar");
            titulo.setText(nota.getTitulo().toString());
            text.setText(nota.getText());
            spinner.setSelection(nota.getCategoria().getId());
        }
        if (intent.hasExtra("posicion")) {
            posicion = intent.getIntExtra("posicion", -1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoriaEscogida = categoriasList.get(position); // Guardamos la categoría escogida por el usuario
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // esto no tiene implementación de momento
    }
}
