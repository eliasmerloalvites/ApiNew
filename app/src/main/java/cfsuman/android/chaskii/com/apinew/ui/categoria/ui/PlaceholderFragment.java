package cfsuman.android.chaskii.com.apinew.ui.categoria.ui;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorCategoria;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorCategoriaLista;
import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.home.Inicio;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    private static final int REQ_CODE_SPEECH_INPUT = 101;
    private static String  categorias = "";
    private static String  categoriasNombre = "";
    ArrayList<MCategoria> listaCategoria;
    ArrayList<MoCServicio> listaSevicio;
    private static AdaptadorCategoria adaptadorCategoria = null;
    private static AdaptadorCategoriaLista adaptadorCategorialista = null;
    RecyclerView recyclerCategoria;
    TextView icobuscador,icoCerrarbuscador;
    ImageView icoModoLista;
    EditText edtbuscador;
    LinearLayout linlay;
    Byte EstadoBuscado = 0,EstadoCerrar = 0, EstadoLista = 0; //0 = escrito y 1 audio
    MyApp varGlobal;


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categoria, container, false);
       // final TextView textView = root.findViewById(R.id.section_label);
        varGlobal = (MyApp) getApplicationContext();
        pageViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                varGlobal.setFamiliaId(String.valueOf(s));
              //  textView.setText(String.valueOf(s));
            }
        });

        categorias = varGlobal.getFamiliaId();
        categoriasNombre = varGlobal.getFamiliaNombre();

        recyclerCategoria = root.findViewById(R.id.recicleCategoriafragment);
        listaCategoria = new ArrayList<>();
        listaSevicio = new ArrayList<>();
        recyclerCategoria = root.findViewById(R.id.recicleCategoriafragment);
        recyclerCategoria.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        adaptadorCategoria = (AdaptadorCategoria) recyclerCategoria.getAdapter();
        linlay = getActivity().findViewById(R.id.lilaBuscadorCat);
        icoCerrarbuscador = getActivity().findViewById(R.id.icoAtrasCat);
        icobuscador = getActivity().findViewById(R.id.icoBuscadorCat);
        icoModoLista = getActivity().findViewById(R.id.modoListaCategoria);
        edtbuscador = getActivity().findViewById(R.id.edtBuscadorCat);
        edtbuscador.setHint(categoriasNombre);
        ListarCategoria();
        icobuscador.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (EstadoBuscado == 0 ) {
                    habilitarEditText(edtbuscador);
                    linlay.setBackgroundResource(R.drawable.formato_buscador);
                    edtbuscador.setHint("Buscar ...");
                    edtbuscador.setTypeface(Typeface.DEFAULT);
                    edtbuscador.setHintTextColor(getActivity().getColor(R.color.colorTextSecu));
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
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
                    edtbuscador.setHint(categoriasNombre);
                    edtbuscador.setTypeface(Typeface.DEFAULT_BOLD);
                    edtbuscador.setHintTextColor(getActivity().getColor(R.color.edtColorWhite));
                    disableEditText(edtbuscador);
                    EstadoBuscado = 0;
                    EstadoCerrar = 0;
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_search).mutate();
                    drawable.setColorFilter(getResources().getColor(R.color.edtColorWhite), PorterDuff.Mode.SRC_ATOP);
                    icobuscador.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    Drawable drawable1 = getResources().getDrawable(R.drawable.ic_atras).mutate();
                    drawable1.setColorFilter(getResources().getColor(R.color.edtColorWhite), PorterDuff.Mode.SRC_ATOP);
                    icoCerrarbuscador.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable1, null);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(icobuscador.getWindowToken(), 0);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),Inicio.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        icoModoLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EstadoLista == 0 ) {

                    icoModoLista.setImageResource(R.drawable.ic_menu_cuadro);
                    EstadoLista = 1;
                    ListarCategoria1();
                }
                else
                {
                    icoModoLista.setImageResource(R.drawable.ic_menu_black_24dp);
                    EstadoLista = 0;
                    ListarCategoria();
                }
            }
        });
        edtbuscador.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(adaptadorCategorialista != null) {
                    adaptadorCategorialista.getFilter().filter(s);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        return  root;
       }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        SharedPreferences preferences = getContext().getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        RequestBody formBody = new FormBody.Builder() //manda parametros
                .add("idfamilia", categorias)
                .build();
        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/listaCategoria")  //url
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
                    listaCategoria.clear();
                    listaSevicio.clear();
                    JSONObject json = new JSONObject(myresponse);
                    JSONObject json1 = json.getJSONObject("success");

                    JSONArray array0 = json1.getJSONArray("categoria");
                    for (int it = 0 ; it<array0.length();it++)
                    {
                        listaCategoria.add(new MCategoria(array0.getJSONObject(it).getString("CAT_Id"),array0.getJSONObject(it).getString("CAT_Nombre"),""));
                        JSONArray array1 = json1.getJSONArray(array0.getJSONObject(it).getString("CAT_Nombre"));
                        for (int i = 0 ; i<array1.length();i++)
                        {
                            listaSevicio.add(new MoCServicio(array1.getJSONObject(i).getString("SER_Id"),array1.getJSONObject(i).getString("SER_Nombre"),array1.getJSONObject(i).getString("SER_Descripcion"),array1.getJSONObject(i).getString("SER_Imagen"),array1.getJSONObject(i).getString("SER_CostoHora"),array0.getJSONObject(it).getString("CAT_Id"),false));
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adaptadorCategoria = new AdaptadorCategoria(listaCategoria, listaSevicio, getApplicationContext(), edtbuscador);
                            recyclerCategoria.setAdapter(adaptadorCategoria);
                        }
                    });



                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    private void ListarCategoria1() {
        SharedPreferences preferences =getContext().getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        RequestBody formBody = new FormBody.Builder() //manda parametros
                .add("idfamilia", categorias)
                .build();
        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/listaCategoria")  //url
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
                    listaCategoria.clear();
                    listaSevicio.clear();
                    JSONObject json = new JSONObject(myresponse);
                    JSONObject json1 = json.getJSONObject("success");

                    JSONArray array0 = json1.getJSONArray("categoria");
                    for (int it = 0 ; it<array0.length();it++)
                    {
                        listaCategoria.add(new MCategoria(array0.getJSONObject(it).getString("CAT_Id"),array0.getJSONObject(it).getString("CAT_Nombre"),""));
                        JSONArray array1 = json1.getJSONArray(array0.getJSONObject(it).getString("CAT_Nombre"));
                        for (int i = 0 ; i<array1.length();i++)
                        {
                            listaSevicio.add(new MoCServicio(array1.getJSONObject(i).getString("SER_Id"),array1.getJSONObject(i).getString("SER_Nombre"),array1.getJSONObject(i).getString("SER_Descripcion"),array1.getJSONObject(i).getString("SER_Imagen"),array1.getJSONObject(i).getString("SER_CostoHora"),array0.getJSONObject(it).getString("CAT_Id"),false));
                        }
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adaptadorCategorialista= new AdaptadorCategoriaLista(listaCategoria,listaSevicio,getApplicationContext(),"caetegoria");
                            recyclerCategoria.setAdapter(adaptadorCategorialista);
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