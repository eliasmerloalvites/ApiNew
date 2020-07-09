package cfsuman.android.chaskii.com.apinew.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cfsuman.android.chaskii.com.apinew.ui.favorito.Favoritos;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorFServicio;
import cfsuman.android.chaskii.com.apinew.modelo.MFamilia;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.perfil.Perfil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Inicio extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private BottomNavigationView navView;
    List<SlideModel> slideModels;
    ArrayList<MFamilia> listaFamilia;
    ArrayList<MoCServicio> listaSevicio;
    ArrayList<MoCServicio> listaPromocion;
    AdaptadorFServicio adaptadorFamilia;
    RecyclerView recyclerFamilia;
    TextView icobuscador,icoCerrarbuscador;
    EditText edtbuscador;
    String Usuario;
    LinearLayout linlay;
    Byte EstadoBuscado = 0; //0 = escrito y 1 audio
    ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        Usuario = preferences.getString("name","");
        listaFamilia = new ArrayList<>();
        listaSevicio = new ArrayList<>();
        listaPromocion = new ArrayList<>();
        recyclerFamilia = findViewById(R.id.recicleFamilia);
        recyclerFamilia.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adaptadorFamilia = (AdaptadorFServicio) recyclerFamilia.getAdapter();
        ListarCategoria();
        imageSlider=findViewById(R.id.slider);
        linlay = findViewById(R.id.lilaBuscadorFam);
        icoCerrarbuscador = findViewById(R.id.icoCerrarCat);
        icobuscador = findViewById(R.id.icoBuscadorFam);
        edtbuscador = findViewById(R.id.edtBuscadorFam);
        edtbuscador.setHint("Bienvenido, "+Usuario);
        navView = findViewById(R.id.nav_viewB); //Instanciamos BotonBar del formulario con nuestra Variable navView
        navView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this); //asignamos una funcion a cumplir si se selecciona
        imageSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //TODO Slider con imagenes y videos
        /*PosterSlider posterSlider =  findViewById(R.id.poster_slider);
        List<Poster> posters=new ArrayList<>();
        //add poster using remote url
        posters.add(new RemoteImage("https://blog.aulaformativa.com/wp-content/uploads/2016/03/ejemplos-slider-de-imagenes-basados-twitter-bootstrap-SimpleBootstrapCarouselWithTextAndOverlay.jpg"));
        //add remote video using uri
        posters.add(new RemoteVideo(Uri.parse("http://subdominio.maprocorp.com/images/videoplayback%20(2).mp4")));
        posterSlider.setPosters(posters);*/

        icobuscador.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (EstadoBuscado == 0 ) {
                    habilitarEditText(edtbuscador);
                    linlay.setBackgroundResource(R.drawable.formato_buscador);
                    edtbuscador.requestFocus();
                    edtbuscador.setHint("Buscar ...");
                    edtbuscador.setTypeface(Typeface.DEFAULT);
                    edtbuscador.setHintTextColor(getColor(R.color.colorTextSecu));
                    habilitarEditText(edtbuscador);
                    EstadoBuscado = 1;
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_micro).mutate();
                    drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    icobuscador.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    Drawable drawable1 = getResources().getDrawable(R.drawable.ic_close).mutate();
                    drawable1.setColorFilter(getResources().getColor(R.color.colorTextSecu), PorterDuff.Mode.SRC_ATOP);
                    icoCerrarbuscador.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable1, null);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edtbuscador, InputMethodManager.SHOW_IMPLICIT);
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

        icoCerrarbuscador.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                linlay.setBackgroundResource(R.drawable.formato_buscador_transparente);
                edtbuscador.setText("");
                edtbuscador.setHint("Bienvenido, "+Usuario);
                edtbuscador.setTypeface(Typeface.DEFAULT_BOLD);
                edtbuscador.setHintTextColor(getColor(R.color.edtColorWhite));
                disableEditText(edtbuscador);
                EstadoBuscado = 0;
                Drawable drawable = getResources().getDrawable(R.drawable.ic_search).mutate();
                drawable.setColorFilter(getResources().getColor(R.color.edtColorWhite), PorterDuff.Mode.SRC_ATOP);
                icobuscador.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                Drawable drawable1 = getResources().getDrawable(R.drawable.ic_close).mutate();
                drawable1.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                icoCerrarbuscador.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable1, null);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(icobuscador.getWindowToken(), 0);
            }
        });

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
    private void habilitarEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
    }
    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }
    private void ListarCategoria() {
        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String idUsuario = preferences.getString("id","");
        RequestBody formBody = new FormBody.Builder() //manda parametros
                .add("USU_Id", idUsuario )
                .build();

        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/listaFamilia")  //url
                .post(formBody)
                .addHeader("Authorization", "Bearer " + token)
                //.post(formBody)       //parametros
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

                    slideModels = new ArrayList<>();
                    JSONObject json = new JSONObject(myresponse);
                    JSONObject json1 = json.getJSONObject("success");
                    JSONArray arraypromo = json.getJSONArray("promocion");
                    if (arraypromo.length()>0)
                    {
                       // listaFamilia.add(new MFamilia("0","Promociones"));
                    }

                    JSONArray array0 = json1.getJSONArray("familia");

                    for (int it = 0 ; it<array0.length();it++)
                    {
                        listaFamilia.add(new MFamilia(array0.getJSONObject(it).getString("FAG_Id"),array0.getJSONObject(it).getString("FAG_Nombre")));
                        JSONArray array1 = json1.getJSONArray(array0.getJSONObject(it).getString("FAG_Id"));
                        for (int i = 0 ; i<array1.length();i++) {

                            Integer activador = 0;
                            JSONArray arrayfavoritos = json1.getJSONArray("Favorito");
                            for (int j = 0; j < arrayfavoritos.length(); j++) {
                                String serFav = arrayfavoritos.getJSONObject(j).getString("SER_Id");
                                String serSer = array1.getJSONObject(i).getString("SER_Id");
                                if (arrayfavoritos.getJSONObject(j).getString("SER_Id").equals(array1.getJSONObject(i).getString("SER_Id")))
                                {
                                    listaSevicio.add(new MoCServicio(array1.getJSONObject(i).getString("SER_Id"), array1.getJSONObject(i).getString("SER_Nombre"), array1.getJSONObject(i).getString("SER_Descripcion"), array1.getJSONObject(i).getString("SER_Imagen"), array1.getJSONObject(i).getString("SER_CostoHora"), array0.getJSONObject(it).getString("FAG_Id"),true));
                                    activador = 1 ;
                                }
                            }
                            if (activador == 0)
                            {
                                listaSevicio.add(new MoCServicio(array1.getJSONObject(i).getString("SER_Id"), array1.getJSONObject(i).getString("SER_Nombre"), array1.getJSONObject(i).getString("SER_Descripcion"), array1.getJSONObject(i).getString("SER_Imagen"), array1.getJSONObject(i).getString("SER_CostoHora"), array0.getJSONObject(it).getString("FAG_Id"),false));
                            }
                        }
                    }


                    for (int i = 0 ; i<arraypromo.length();i++)
                    {
                       // listaSevicio.add(new MoCServicio(arraypromo.getJSONObject(i).getString("SER_Id"),arraypromo.getJSONObject(i).getString("SER_Nombre"),arraypromo.getJSONObject(i).getString("SER_Descripcion"),arraypromo.getJSONObject(i).getString("SER_Imagen"),arraypromo.getJSONObject(i).getString("SER_CostoHora"),"0",false));

                        slideModels.add(new SlideModel("http://subdominio.maprocorp.com/images/servicio/" + arraypromo.getJSONObject(i).getString("SER_Imagen")));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageSlider .setImageList(slideModels,true);
                    adaptadorFamilia= new AdaptadorFServicio(listaFamilia,listaSevicio,getApplicationContext(),edtbuscador);
                    recyclerFamilia.setAdapter(adaptadorFamilia);
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
