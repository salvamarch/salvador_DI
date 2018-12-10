package com.example.salvamarch.salvador_di;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

class mibd extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tele.db";
    public static final String tabla1 = "series";
    public static final String tabla2 = "capitulos";

    static final String COL_1 = "nombre";
    static final String COL_2 = "año";
    static final String COL_3 = "cadena";
    static final String COL_4 = "temporadas";
    static final String COL_5 = "id";
    static final String COL_6 = "temporada";
    static final String COL_7 = "titulo";
    static final String COL_8 = "numero";

    public mibd(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + tabla1 + "(" + COL_1 + " TEXT PRIMARY KEY, " + COL_2 + " INTEGER, " + COL_3  + " TEXT, " + COL_4 + " INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE " + tabla2 + "(" + COL_5 + " INTEGER PRIMARY KEY, " + COL_6 + " INTEGER, " + COL_7  + " TEXT, " + COL_8 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+tabla1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+tabla2);
    }

    public boolean insertarserie(Editable nombre, Editable año, Editable cadena, Editable temporadas){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, nombre.toString());
        contentValues.put(COL_2, año.toString());
        contentValues.put(COL_3, cadena.toString());
        contentValues.put(COL_4, temporadas.toString());
        long result = db.insert(tabla1,null, contentValues);
        db.close();
        if (result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insertarcapitulo(Editable id, String temporada, String tiulo, Editable numero){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5, id.toString());
        contentValues.put(COL_6, temporada);
        contentValues.put(COL_7, tiulo);
        contentValues.put(COL_8, numero.toString());
        long result = db.insert(tabla2,null, contentValues);
        db.close();
        if (result==-1){
            return false;
        }else{
            return true;
        }
    }

    Cursor listarserie(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor datos=db.rawQuery("SELECT * FROM " + tabla1, null);
        return datos;
    }

    Cursor listarcapitulo(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor datos=db.rawQuery("SELECT * FROM " + tabla2, null);
        return datos;
    }

    public boolean borrarserie(String nombre){
        SQLiteDatabase db=this.getWritableDatabase();
        int filasborradas=db.delete(tabla1, COL_1 + "=?", new  String[]{nombre});
        db.close();
        return (filasborradas>0);
    }

    public boolean borrarcapitulo(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        int filasborradas=db.delete(tabla2, COL_5 + "=?", new  String[]{id});
        db.close();
        return (filasborradas>0);
    }

    public boolean actualizarserie(String nombre, Editable año, Editable cadena, Editable temporadas){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues;
        contentValues=new ContentValues();
        contentValues.put(COL_2, año.toString());
        contentValues.put(COL_3, cadena.toString());
        contentValues.put(COL_4, temporadas.toString());
        int filasafectadas=db.update(tabla1, contentValues, COL_1+"=?", new String[]{nombre});
        db.close();
        return (filasafectadas>0);
    }

    public boolean actualizarcapitulo(String id, Editable temporada, Editable titulo, Editable numero){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues;
        contentValues=new ContentValues();
        contentValues.put(COL_6, temporada.toString());
        contentValues.put(COL_7, titulo.toString());
        contentValues.put(COL_8, numero.toString());
        int filasafectadas=db.update(tabla2, contentValues, COL_5+"=?", new String[]{id});
        db.close();
        return (filasafectadas>0);
    }
}
