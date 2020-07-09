package cfsuman.android.chaskii.com.apinew.ui.clase;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorClaServicio;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorClaseLista;
import cfsuman.android.chaskii.com.apinew.modelo.MClase;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.categoria.ACategoria;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Clases extends AppCompatActivity {
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
    LinearLayout linlay;
    Byte EstadoBuscado = 0,EstadoCerrar = 0, EstadoLista = 0; //0 = escrito y 1 audio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase);
        MyApp varGlobal = (MyApp) getApplicationContext();

        clases = varGlobal.getCategoriaId();
        clasesNombre = varGlobal.getCategoriaNombre();

        listaClase = new ArrayList<>();
        listaSevicio = new ArrayList<>();
        recyclerClase = findViewById(R.id.recicleClase);
        recyclerClaseLista = findViewById(R.id.recicleClase);
        glm = new GridLayoutManager(this, 3);
        ListarClase();
       // adaptadorClase = (AdaptadorClase) recyclerClase.getAdapter();
        linlay = findViewById(R.id.lilaBuscadorCla);
        icoCerrarbuscador = findViewById(R.id.icoAtrasCla);
        icobuscador = findViewById(R.id.icoBuscadorCla);
        icoModoLista = findViewById(R.id.modoListaClase);
        edtbuscador = findViewById(R.id.edtBuscadorCla);
        edtbuscador.setHint(clasesNombre);
        //  disableEditText(edtbuscador);

        icobuscador.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (EstadoBuscado == 0 ) {
                    habilitarEditText(edtbuscador);
                    linlay.setBackgroundResource(R.drawable.formato_buscador);
                    edtbuscador.setHint("Buscar ...");
                    edtbuscador.setTypeface(Typeface.DEFAULT);
                    edtbuscador.setHintTextColor(getColor(R.color.colorTextSecu));
                    habilitarEditText(edtbuscador);
                    EstadoBuscado = 1;
                    EstadoCerrar = 1;
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_micro).mutate();
                    drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    icobuscador.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    Drawable drawable1 = getResources().getDrawable(R.drawable.ic_close).mutate();
                    drawable1.setColorFilter(getResources().getColor(R.color.colorTextSecu), PorterDuff.Mode.SRC_ATOP);
                    icoCerrarbuscador.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable1, null);
                    edtbuscador.requestFocus();
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
                if(EstadoCerrar == 1) {
                    linlay.setBackgroundResource(R.drawable.formato_buscador_transparente);
                    edtbuscador.setText("");
                    edtbuscador.setHint(clasesNombre);
                    edtbuscador.setTypeface(Typeface.DEFAULT_BOLD);
                    edtbuscador.setHintTextColor(getColor(R.color.edtColorWhite));
                    disableEditText(edtbuscador);
                    EstadoBuscado = 0;
                    EstadoCerrar = 0;
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_search).mutate();
                    drawable.setColorFilter(getResources().getColor(R.color.edtColorWhite), PorterDuff.Mode.SRC_ATOP);
                    icobuscador.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    Drawable drawable1 = getResources().getDrawable(R.drawable.ic_atras).mutate();
                    drawable1.setColorFilter(getResources().getColor(R.color.edtColorWhite), PorterDuff.Mode.SRC_ATOP);
                    icoCerrarbuscador.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable1, null);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(icobuscador.getWindowToken(), 0);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), ACategoria.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
        icoModoLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EstadoLista == 0 ) {

                    icoModoLista.setImageResource(R.drawable.ic_menu_cuadro);
                    EstadoLista = 1;
                    ListarClase1();
                }
                else
                {
                    icoModoLista.setImageResource(R.drawable.ic_menu_black_24dp);
                    EstadoLista = 0;
                    ListarClase();
                }
            }
        });
        edtbuscador.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(adaptadorClaselista != null) {
                    adaptadorClaselista.getFilter().filter(s);
                }
                    if(adaptadorClaselista1 != null) {
                    adaptadorClaselista1.getFilter().filter(s);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
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

    private void ListarClase() {
        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        RequestBody formBody = new FormBody.Builder() //manda parametros
                .add("idcategoria", clases)
                .build();

        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/listaClase")  //url
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

                    JSONArray array0 = json1.getJSONArray("clase");
                    for (int it = 0 ; it<array0.length();it++)
                    {
                        listaClase.add(new MClase(array0.getJSONObject(it).getString("CLA_Id"),array0.getJSONObject(it).getString("CLA_Nombre"),""));
                        JSONArray array1 = json1.getJSONArray(array0.getJSONObject(it).getString("CLA_Nombre"));
                        for (int i = 0 ; i<array1.length();i++)
                        {
                            listaSevicio.add(new MoCServicio(array1.getJSONObject(i).getString("SER_Id"),array1.getJSONObject(i).getString("SER_Nombre"),array1.getJSONObject(i).getString("SER_Descripcion"),array1.getJSONObject(i).getString("SER_Imagen"),array1.getJSONObject(i).getString("SER_CostoHora"),array0.getJSONObject(it).getString("CLA_Id"),false));
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    //adaptadorClase= new AdaptadorClase(listaClase,listaSevicio,getApplicationContext(),edtbuscador);
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

    private void ListarClase1() {
        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        RequestBody formBody = new FormBody.Builder() //manda parametros
                .add("idcategoria", clases)
                .build();

        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/listaClase")  //url
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
                listaClase.clear();
                listaSevicio.clear();

                try {
                    JSONObject json = new JSONObject(myresponse);
                    JSONObject json1 = json.getJSONObject("success");

                    JSONArray array0 = json1.getJSONArray("clase");
                    for (int it = 0 ; it<array0.length();it++)
                    {
                        listaClase.add(new MClase(array0.getJSONObject(it).getString("CLA_Id"),array0.getJSONObject(it).getString("CLA_Nombre"),""));
                        JSONArray array1 = json1.getJSONArray(array0.getJSONObject(it).getString("CLA_Nombre"));
                        for (int i = 0 ; i<array1.length();i++)
                        {
                            listaSevicio.add(new MoCServicio(array1.getJSONObject(i).getString("SER_Id"),array1.getJSONObject(i).getString("SER_Nombre"),array1.getJSONObject(i).getString("SER_Descripcion"),array1.getJSONObject(i).getString("SER_Imagen"),array1.getJSONObject(i).getString("SER_CostoHora"),array0.getJSONObject(it).getString("CLA_Id"),false));
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerClaseLista.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));

                            adaptadorClaselista= new AdaptadorClaseLista(listaClase,listaSevicio,getApplicationContext(),edtbuscador,"clase");
                            recyclerClaseLista.setAdapter(adaptadorClaselista);
                        }
                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}
