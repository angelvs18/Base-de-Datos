package com.goldenfenix.universidadumaee;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextNombre, editTextApellidoMaterno, editTextApellidoPaterno;
    Button buttonAgregar, buttonConsultar;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellidoMaterno = findViewById(R.id.editTextApellidoMaterno);
        editTextApellidoPaterno = findViewById(R.id.editTextApellidoPaterno);
        buttonAgregar = findViewById(R.id.buttonAgregar);
        buttonConsultar = findViewById(R.id.buttonConsultar);

        dbHelper = new DBHelper(this);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarAlumno();
            }
        });

        buttonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirConsultaActivity();
            }
        });
    }

    private void agregarAlumno() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.Alumnos.COLUMN_NOMBRE, editTextNombre.getText().toString());
        values.put(DBHelper.Alumnos.COLUMN_APELLIDO_MATERNO, editTextApellidoMaterno.getText().toString());
        values.put(DBHelper.Alumnos.COLUMN_APELLIDO_PATERNO, editTextApellidoPaterno.getText().toString());

        long newRowId = db.insert(DBHelper.Alumnos.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Alumno agregado con ID: " + newRowId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al agregar alumno", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void abrirConsultaActivity() {
        Intent intent = new Intent(this, ConsultaActivity.class);
        startActivity(intent);
    }
}
