// DBHelper.java
package com.goldenfenix.universidadumaee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BD_UMAEE";

    public static class Alumnos implements BaseColumns {
        public static final String TABLE_NAME = "Alumnos";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_APELLIDO_MATERNO = "apellido_materno";
        public static final String COLUMN_APELLIDO_PATERNO = "apellido_paterno";
    }

    private static final String SQL_CREATE_ALUMNOS =
            "CREATE TABLE " + Alumnos.TABLE_NAME + " (" +
                    Alumnos._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Alumnos.COLUMN_NOMBRE + " TEXT," +
                    Alumnos.COLUMN_APELLIDO_MATERNO + " TEXT," +
                    Alumnos.COLUMN_APELLIDO_PATERNO + " TEXT)";

    private static final String SQL_DELETE_ALUMNOS =
            "DROP TABLE IF EXISTS " + Alumnos.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALUMNOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALUMNOS);
        onCreate(db);
    }
}
