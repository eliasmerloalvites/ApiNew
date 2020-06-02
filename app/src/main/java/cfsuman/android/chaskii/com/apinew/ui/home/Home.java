package cfsuman.android.chaskii.com.apinew.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.Voice;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.SQLite.SQLusuario;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorFamilia;
import cfsuman.android.chaskii.com.apinew.modelo.MFamilia;
import cfsuman.android.chaskii.com.apinew.modelo.MoAlquiler;
import cfsuman.android.chaskii.com.apinew.modelo.MoFrelancer;
import cfsuman.android.chaskii.com.apinew.modelo.MoPromocion;
import cfsuman.android.chaskii.com.apinew.modelo.MoServicio;
import cfsuman.android.chaskii.com.apinew.ui.categoria.ACategoria;
import cfsuman.android.chaskii.com.apinew.ui.perfil.Perfil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    ArrayList<MoPromocion> listaFPromocion;
    ArrayList<MoServicio> listaFServicio;
    ArrayList<MoFrelancer> listaFFrelancer;
    ArrayList<MoAlquiler> listaFAlquiler;
    RecyclerView recycler0,recycler1,recycler2,recycler3;
    SQLusuario sqLusuario;
    AdaptadorFamilia adaptadorPromocion,adaptadorServicio,adaptadorFrelancer,adaptadorAlquiler;
    TextView icobuscador,btnDerecha,btnIzquierda;
    EditText edtbuscador;
    LinearLayout linlay;
    Byte EstadoBuscado = 0; //0 = escrito y 1 audio
    Toolbar toolbar;
    RecyclerView.LayoutManager mlayoutmanager;

    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*toolbar = findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Bienvenido, Juan Pérez");*/

        listaFPromocion = new ArrayList<>();
        listaFServicio = new ArrayList<>();
        listaFFrelancer = new ArrayList<>();
        listaFAlquiler = new ArrayList<>();
        btnDerecha = findViewById(R.id.txtDerechoServicio);
        btnIzquierda = findViewById(R.id.txtIzquierdaServicio);
        linlay = findViewById(R.id.lilaBuscador);
        icobuscador = findViewById(R.id.icoBuscador);
        edtbuscador = findViewById(R.id.edtBuscador);
        recycler0 = findViewById(R.id.recicleHomePromocion);
        recycler1 = findViewById(R.id.recicleHomeServicio);
        recycler2= findViewById(R.id.recicleHomeFrelancer);
        recycler3= findViewById(R.id.recicleHomeAlquiler);
        recycler0.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recycler1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recycler2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recycler3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        adaptadorPromocion = (AdaptadorFamilia) recycler0.getAdapter();
        adaptadorServicio = (AdaptadorFamilia) recycler1.getAdapter();
        adaptadorFrelancer = (AdaptadorFamilia) recycler2.getAdapter();
        adaptadorAlquiler = (AdaptadorFamilia) recycler3.getAdapter();
        navView = findViewById(R.id.nav_viewB); //Instanciamos BotonBar del formulario con nuestra Variable navView
        navView.setOnNavigationItemSelectedListener(this); //asignamos una funcion a cumplir si se selecciona
        ListarFamilia();
        icobuscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EstadoBuscado == 0 ) {
                    linlay.setBackgroundResource(R.drawable.formato_buscador);
                    edtbuscador.requestFocus();
                    edtbuscador.setHint("Buscar ...");
                    edtbuscador.setHintTextColor(Color.rgb(182, 182, 182));
                    EstadoBuscado = 1;
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_micro).mutate();
                    drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

                    icobuscador.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

                    Toast.makeText(getApplicationContext(), "HOLA NO PASO NADA", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent inten = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    inten.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    inten.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                    inten.putExtra(RecognizerIntent.EXTRA_PROMPT,"HOLA COMO PUEDO AYUDARTE MI AMO");
                    try {
                        startActivityForResult(inten,REQ_CODE_SPEECH_INPUT);
                    }catch (ActivityNotFoundException e)
                    {
                        System.out.println(e);
                    }
                }
            }
        });
        edtbuscador.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(adaptadorPromocion != null) {
                    adaptadorPromocion.getFilterPr().filter(s);
                }
                if(adaptadorServicio != null) {
                    adaptadorServicio.getFilter().filter(s);
                }
                if(adaptadorFrelancer != null) {
                    adaptadorFrelancer.getFilterFr().filter(s);
                }
                if(adaptadorAlquiler != null) {
                    adaptadorAlquiler.getFilterAl().filter(s);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        btnDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycler1.scrollBy(150, 0);
            }
        });
        btnIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycler1.scrollBy(-150, 0);
            }
        });
    }
    public void categoriaAct(View view)
    {
        Intent intent = new Intent(this, ACategoria.class);
        startActivity(intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQ_CODE_SPEECH_INPUT :{
                if (resultCode==RESULT_OK && data!=null)
                {
                    ArrayList<String> result  = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edtbuscador.setText(result.get(0));
                }
                break;
            }
        }
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

                    JSONArray array0 = json.getJSONArray("promocion");
                    for (int it = 0 ; it<array0.length();it++)
                    {
                        listaFPromocion.add(new MoPromocion(array0.getJSONObject(it).getString("SER_Id"),array0.getJSONObject(it).getString("SER_Nombre"),array0.getJSONObject(it).getString("SER_Descripcion"),array0.getJSONObject(it).getString("SER_Imagen"),array0.getJSONObject(it).getString("SER_CostoHora")));
                    }
                    JSONArray array1 = json.getJSONArray("servicio");
                    for (int it = 0 ; it<array1.length();it++)
                    {
                        listaFServicio.add(new MoServicio(array1.getJSONObject(it).getString("SER_Id"),array1.getJSONObject(it).getString("SER_Nombre"),array1.getJSONObject(it).getString("SER_Descripcion"),array1.getJSONObject(it).getString("SER_Imagen"),array1.getJSONObject(it).getString("SER_CostoHora")));
                    }
                    JSONArray array2 = json.getJSONArray("delivery");
                    for (int it = 0 ; it<array2.length();it++)
                    {
                        listaFFrelancer.add(new MoFrelancer(array2.getJSONObject(it).getString("SER_Id"),array2.getJSONObject(it).getString("SER_Nombre"),array2.getJSONObject(it).getString("SER_Descripcion"),array2.getJSONObject(it).getString("SER_Imagen"),array2.getJSONObject(it).getString("SER_CostoHora")));
                    }
                    JSONArray array3 = json.getJSONArray("alquiler");
                    for (int it = 0 ; it<array3.length();it++)
                    {
                        listaFAlquiler.add(new MoAlquiler(array3.getJSONObject(it).getString("SER_Id"),array3.getJSONObject(it).getString("SER_Nombre"),array3.getJSONObject(it).getString("SER_Descripcion"),array3.getJSONObject(it).getString("SER_Imagen"),array3.getJSONObject(it).getString("SER_CostoHora")));
                    }
                            adaptadorPromocion = new AdaptadorFamilia(listaFPromocion ,listaFServicio ,listaFFrelancer,listaFAlquiler,getApplicationContext(),0);
                            adaptadorServicio = new AdaptadorFamilia(listaFPromocion ,listaFServicio ,listaFFrelancer,listaFAlquiler,getApplicationContext(),1);
                            adaptadorFrelancer = new AdaptadorFamilia(listaFPromocion ,listaFServicio ,listaFFrelancer,listaFAlquiler,getApplicationContext(),2);
                            adaptadorAlquiler = new AdaptadorFamilia(listaFPromocion ,listaFServicio ,listaFFrelancer,listaFAlquiler,getApplicationContext(),3);
                            recycler0.setAdapter(adaptadorPromocion);
                            recycler1.setAdapter(adaptadorServicio);
                            recycler2.setAdapter(adaptadorFrelancer);
                            recycler3.setAdapter(adaptadorAlquiler);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

/*    @Override
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
    }*/

}
