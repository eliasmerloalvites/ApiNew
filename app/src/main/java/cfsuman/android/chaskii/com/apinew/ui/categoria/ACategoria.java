package cfsuman.android.chaskii.com.apinew.ui.categoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorCategoria;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorFamilia;
import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MoAlquiler;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.modelo.MoFrelancer;
import cfsuman.android.chaskii.com.apinew.modelo.MoPromocion;
import cfsuman.android.chaskii.com.apinew.modelo.MoServicio;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.security.AccessController.getContext;

public class ACategoria extends AppCompatActivity {

    ArrayList<MCategoria> listaCategoria;
    ArrayList<MoCServicio> listaSevicio;
    AdaptadorCategoria adaptadorCategoria;
    RecyclerView recycler0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acategoria);

        listaCategoria = new ArrayList<>();
        listaSevicio = new ArrayList<>();
        recycler0 = findViewById(R.id.recicleCategoria);
        recycler0.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adaptadorCategoria = (AdaptadorCategoria) recycler0.getAdapter();
        ListarCategoria();

        //ListarCategoria();
    }

    private void ListarCategoria() {

        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        RequestBody formBody = new FormBody.Builder() //manda parametros
                .add("idfamilia", "2")
                .build();
        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/listaCategoria")  //url
                .post(formBody)
                .addHeader("Authorization", "Bearer " + token)
                .post(formBody)       //parametros
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
                    JSONObject json1 = json.getJSONObject("success");

                    JSONArray array0 = json1.getJSONArray("categoria");
                    for (int it = 0 ; it<array0.length();it++)
                    {
                        listaCategoria.add(new MCategoria(array0.getJSONObject(it).getString("CAT_Id"),array0.getJSONObject(it).getString("CAT_Nombre"),""));
                        JSONArray array1 = json1.getJSONArray(array0.getJSONObject(it).getString("CAT_Nombre"));
                        for (int i = 0 ; i<array1.length();i++)
                        {
                            listaSevicio.add(new MoCServicio(array1.getJSONObject(i).getString("SER_Id"),array1.getJSONObject(i).getString("SER_Nombre"),array1.getJSONObject(i).getString("SER_Descripcion"),array1.getJSONObject(i).getString("SER_Imagen"),array1.getJSONObject(i).getString("SER_CostoHora")));
                        }
                    }

                    adaptadorCategoria= new AdaptadorCategoria(listaCategoria,listaSevicio);
                    recycler0.setAdapter(adaptadorCategoria);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
