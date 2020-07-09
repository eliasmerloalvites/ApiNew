package cfsuman.android.chaskii.com.apinew.ui.favorito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorClaServicio;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorClaseLista;
import cfsuman.android.chaskii.com.apinew.modelo.MClase;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.home.Inicio;
import cfsuman.android.chaskii.com.apinew.ui.perfil.Perfil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Favoritos extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navView;
    private static final int REQ_CODE_SPEECH_INPUT = 101;
    private static String  clases = "";
    private static String  clasesNombre = "";
    ArrayList<MClase> listaClase;
    ArrayList<MoCServicio> listaSevicio;
    AdaptadorClaseLista adaptadorClaselista = null;
    AdaptadorClaServicio adaptadorClaselista1 = null;
    RecyclerView recyclerClase,recyclerClaseLista;
    private GridLayoutManager glm;
    TextView icobuscador,icoCerrarbuscador;
    ImageView icoModoLista;
    EditText edtbuscador;
    LinearLayout linlay, linlayfav;
    Byte EstadoBuscado = 0,EstadoCerrar = 0, EstadoLista = 0; //0 = escrito y 1 audio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        navView = findViewById(R.id.nav_viewB); //Instanciamos BotonBar del formulario con nuestra Variable navView
        navView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this); //asignamos una funcion a cumplir si se selecciona
        navView.getMenu().getItem(1).setChecked(true);

        listaClase = new ArrayList<>();
        listaSevicio = new ArrayList<>();
        recyclerClase = findViewById(R.id.recicleFavoritos);
        recyclerClaseLista = findViewById(R.id.recicleFavoritos);
        glm = new GridLayoutManager(this, 3);
        ListarFavoritos("listar","");
        linlayfav = findViewById(R.id.idFavoritoVacio);
        linlay = findViewById(R.id.lilaBuscadorCla);
        icoCerrarbuscador = findViewById(R.id.icoAtrasCla);
        icobuscador = findViewById(R.id.icoBuscadorCla);
        icoModoLista = findViewById(R.id.modoListaClase);
        edtbuscador = findViewById(R.id.edtBuscadorCla);
    }

    public void ListarFavoritos(String formato,String idServicio) {
        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String idUsuario = preferences.getString("id","");
        RequestBody formBody = new FormBody.Builder() //manda parametros
                .add("formato",String.valueOf(formato) )
                .add("USU_Id", idUsuario )
                .add("SER_Id", String.valueOf(idServicio))
                .build();

        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/listaFavoritos")  //url
                .post(formBody)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        OkHttpClient client = new OkHttpClient();   //ok
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String myresponse = response.body().string();
                System.out.println(myresponse);
                try {
                    JSONObject json = new JSONObject(myresponse);
                    JSONObject json1 = json.getJSONObject("success");
                    listaClase.clear();
                    listaSevicio.clear();
                    JSONArray arrayFavorito = json1.getJSONArray("Favorito");
                    if (arrayFavorito.length() > 0 ) {
                        for (int j = 0 ; j<arrayFavorito.length();j++)
                        {
                            JSONArray array0 = json1.getJSONArray(arrayFavorito.getJSONObject(j).getString("SER_Id"));
                            for (int i = 0; i < array0.length(); i++) {
                                listaSevicio.add(new MoCServicio(array0.getJSONObject(i).getString("SER_Id"), array0.getJSONObject(i).getString("SER_Nombre"), array0.getJSONObject(i).getString("SER_Descripcion"), array0.getJSONObject(i).getString("SER_Imagen"), array0.getJSONObject(i).getString("SER_CostoHora"), array0.getJSONObject(i).getString("CLA_Id"),false));
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //adaptadorClase= new AdaptadorClase(listaClase,listaSevicio,getApplicationContext(),edtbuscador);
                            if (listaSevicio.size() >0)
                            {
                                linlayfav.setVisibility(View.GONE);
                            }
                            else
                            {
                                linlayfav.setVisibility(View.VISIBLE);
                            }

                            recyclerClase.setLayoutManager(glm);
                            adaptadorClaselista1= new AdaptadorClaServicio(listaSevicio,getApplicationContext(),"clase");
                            recyclerClase.setAdapter(adaptadorClaselista1);
                        }
                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navigation_Inicio) {
            Intent intent = new Intent(this, Inicio.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if (id == R.id.navigation_notificaciones) {
            Intent intent = new Intent(this, Favoritos.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if (id == R.id.navigation_Mensaje) {

        }

        else if (id == R.id.navigation_perfil) {
            Intent intent = new Intent(this, Perfil.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return true;
    }
}
