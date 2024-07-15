// ConsultaActivity.java
package com.goldenfenix.universidadumaee;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ConsultaActivity extends AppCompatActivity {

    EditText editTextConsulta;
    Button buttonConsultar;
    LinearLayout resultadosLayout;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        editTextConsulta = findViewById(R.id.editTextConsulta);
        buttonConsultar = findViewById(R.id.buttonConsultar);
        resultadosLayout = findViewById(R.id.resultadosLayout);

        dbHelper = new DBHelper(this);

        buttonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarRegistros();
            }
        });
    }

    private void consultarRegistros() {
        String consulta = editTextConsulta.getText().toString().trim();

        if (consulta.isEmpty()) {
            Toast.makeText(this, "Ingresa un valor para consultar", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.Alumnos._ID,
                DBHelper.Alumnos.COLUMN_NOMBRE,
                DBHelper.Alumnos.COLUMN_APELLIDO_MATERNO,
                DBHelper.Alumnos.COLUMN_APELLIDO_PATERNO
        };

        // Aquí definimos la condición de consulta según lo ingresado por el usuario
        String selection = DBHelper.Alumnos._ID + " = ? OR " +
                DBHelper.Alumnos.COLUMN_NOMBRE + " LIKE ? OR " +
                DBHelper.Alumnos.COLUMN_APELLIDO_MATERNO + " LIKE ? OR " +
                DBHelper.Alumnos.COLUMN_APELLIDO_PATERNO + " LIKE ?";

        // Aquí definimos los argumentos para la consulta
        String[] selectionArgs = {consulta, "%" + consulta + "%", "%" + consulta + "%", "%" + consulta + "%"};

        Cursor cursor = db.query(
                DBHelper.Alumnos.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        mostrarResultados(cursor);

        db.close();
    }

    private void mostrarResultados(Cursor cursor) {
        resultadosLayout.removeAllViews();

        List<String> resultadosList = new ArrayList<>();
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.Alumnos._ID));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.Alumnos.COLUMN_NOMBRE));
            String apellidoMaterno = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.Alumnos.COLUMN_APELLIDO_MATERNO));
            String apellidoPaterno = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.Alumnos.COLUMN_APELLIDO_PATERNO));

            String alumnoInfo = "ID: " + itemId + ", Nombre: " + nombre + ", Apellido Materno: " +
                    apellidoMaterno + ", Apellido Paterno: " + apellidoPaterno;

            resultadosList.add(alumnoInfo);
        }

        if (resultadosList.isEmpty()) {
            Toast.makeText(this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
        } else {
            for (String resultado : resultadosList) {
                TextView textView = new TextView(this);
                textView.setText(resultado);
                textView.setTextColor(getResources().getColor(android.R.color.white));
                resultadosLayout.addView(textView);
            }
        }

        cursor.close();
    }
}
