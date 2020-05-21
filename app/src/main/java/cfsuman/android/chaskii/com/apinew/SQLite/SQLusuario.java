package cfsuman.android.chaskii.com.apinew.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import cfsuman.android.chaskii.com.apinew.modelo.MUsuario;

public class SQLusuario extends SQLiteOpenHelper {

    private static final String NOMBRE_BD="servicio.bd";
    private static final int VERSION_BD=1;
    private static final String TABLA_USUARIO="CREATE TABLE USUARIO(CODUSUARIO TEXT,NOMBRE TEXT,EMAIL TEXT,TOKEN TEXT)";

    public SQLusuario(Context context){
        super(context,NOMBRE_BD,null,VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIO);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +TABLA_USUARIO);
        db.execSQL(TABLA_USUARIO);
    }

    public void agregarUsuario(String idUsu, String nombre, String email, String token){
        SQLiteDatabase bd=getWritableDatabase();
        if(bd!=null){
            bd.execSQL("INSERT INTO USUARIO VALUES('"+idUsu+"','"+nombre+"','"+email+"','"+token+"')");
            bd.close();
        }
    }
    //-------------------------------------------------------
    public List<MUsuario> mostrarUsuario(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM USUARIO ",null);
        List<MUsuario> cursos=new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                cursos.add(new MUsuario(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return cursos;
    }

    public void vaciarUsuario(){
        SQLiteDatabase bd=getWritableDatabase();
        bd.execSQL("DELETE FROM USUARIO");
    }

    public void crearReserva(){
        SQLiteDatabase bd=getWritableDatabase();
        bd.execSQL("CREATE TABLE USUARIO(idUsu TEXT,nombre TEXT,email TEXT,token TEXT)");

        }

    }

