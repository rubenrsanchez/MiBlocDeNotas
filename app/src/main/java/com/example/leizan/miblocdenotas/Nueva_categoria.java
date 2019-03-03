package com.example.leizan.miblocdenotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Nueva_categoria extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText nombreCategoria;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private String colorElegido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_categoria);
        setTitle(R.string.title_nuevaCategoriaActivity);

        nombreCategoria = findViewById(R.id.textView_nuevaCategoria);
        spinner = findViewById(R.id.spinner_colors);
        if(spinner != null){
            spinner.setOnItemSelectedListener(this);
        }
        adapter = ArrayAdapter.createFromResource(this, R.array.labels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spinner != null){
            spinner.setAdapter(adapter);
        }
    }

    public void guardarCat(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String[] coloresHex = getResources().getStringArray(R.array.coloresHexadecimal); // Los colores están definidos en un array dentro de strings.xml
        switch (colorElegido){ // En función del item que haya seleccionado el usuario en el spinner, haremos un putExtra para pasarle al mainActivity el nuevo objeto CategoriaNota
            case "Rojo":
                intent.putExtra("CategoriaNota", new CategoriaNota(nombreCategoria.getText().toString(), coloresHex[0]));
                break;
            case "Azul":
                intent.putExtra("CategoriaNota", new CategoriaNota(nombreCategoria.getText().toString(), coloresHex[1]));
                break;
            case "Verde":
                intent.putExtra("CategoriaNota", new CategoriaNota(nombreCategoria.getText().toString(), coloresHex[2]));
                break;
            case "Amarillo":
                intent.putExtra("CategoriaNota", new CategoriaNota(nombreCategoria.getText().toString(), coloresHex[3]));
                break;
            case "Rosa":
                intent.putExtra("CategoriaNota", new CategoriaNota(nombreCategoria.getText().toString(), coloresHex[4]));
                break;
            case "Violeta":
                intent.putExtra("CategoriaNota", new CategoriaNota(nombreCategoria.getText().toString(), coloresHex[5]));
                break;
            case "Naranja":
                intent.putExtra("CategoriaNota", new CategoriaNota(nombreCategoria.getText().toString(), coloresHex[6]));
                break;
            default:
                intent.putExtra("CategoriaNota", new CategoriaNota(nombreCategoria.getText().toString(), "-1"));
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        colorElegido = parent.getItemAtPosition(position).toString(); // Guardamos el color elegido por el usuario (el String que selecciona en el spinner)
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // sin implementación por ahora
    }
}
