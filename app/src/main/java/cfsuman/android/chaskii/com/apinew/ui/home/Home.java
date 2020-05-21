package cfsuman.android.chaskii.com.apinew.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.SQLite.SQLusuario;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorFamilia;
import cfsuman.android.chaskii.com.apinew.modelo.MFamilia;
import cfsuman.android.chaskii.com.apinew.ui.perfil.Perfil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    ArrayList<MFamilia> listaFamilia;
    RecyclerView recycler1,recycler2;
    SQLusuario sqLusuario;
    AdaptadorFamilia adaptador;
    Toolbar toolbar;

    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");

        listaFamilia=new ArrayList<>();
        recycler1= findViewById(R.id.recicleHome1);
        recycler2= findViewById(R.id.recicleHome2);
        recycler1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recycler2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adaptador = (AdaptadorFamilia) recycler1.getAdapter();
        navView = findViewById(R.id.nav_viewB); //Instanciamos BotonBar del formulario con nuestra Variable navView
        navView.setOnNavigationItemSelectedListener(this); //asignamos una funcion a cumplir si se selecciona
        ListarFamilia();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment;

        if (id == R.id.navigation_Inicio) {
            Intent intent = new Intent(this, Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else if (id == R.id.navigation_Mensaje) {

        }
        else if (id == R.id.navigation_busqueda) {


        }
        else if (id == R.id.navigation_notificaciones) {


        }
        else if (id == R.id.navigation_perfil) {
            Intent intent = new Intent(this, Perfil.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //logout();
            //cerrarSesion();

        }
        return true;
    }

    private void ListarFamilia() {

        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        RequestBody formBody = new FormBody.Builder() //manda parametros
                .build();

        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/listaFamilia")  //url
                .post(formBody)
                .addHeader("Authorization", "Bearer " + token)
                //.post(formBody)       //parametros
                .build();
        OkHttpClient client = new OkHttpClient();   //ok
        // Call call = client.newCall(request);
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
                    JSONArray array = json.getJSONArray("success");
                    for (int it = 0 ; it<array.length();it++)
                    {
                        listaFamilia.add(new MFamilia(array.getJSONObject(it).getString("FAG_Id"),array.getJSONObject(it).getString("FAG_Nombre"),array.getJSONObject(it).getString("FAG_Descripcion")));
                    }
                            adaptador = new AdaptadorFamilia(listaFamilia ,getApplicationContext());
                            recycler1.setAdapter(adaptador);
                }
                catch (Exception e){

                    e.printStackTrace();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_view);

        androidx.appcompat.widget.SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adaptador != null) {
                        adaptador.getFilter().filter(newText);
                }
                return false;
            }
        });
            return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.search_view){
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

}
