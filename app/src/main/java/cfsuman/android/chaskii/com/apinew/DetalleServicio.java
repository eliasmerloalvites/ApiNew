package cfsuman.android.chaskii.com.apinew;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorDetallesTarea;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorSServicio;
import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MDetalleSubTarea;
import cfsuman.android.chaskii.com.apinew.modelo.MDetalleTarea;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.clase.Clases;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetalleServicio extends AppCompatActivity {
    private static String  servicio = "",imagenes="",descripcion="",precio="";
    TextView txtdescripcion,txttotal;
    ImageView imagen ;
    ArrayList<MDetalleTarea> listaTarea;
    ArrayList<MDetalleSubTarea> listaSubTarea;
    ArrayList<MCategoria> listaCategoria;
    ArrayList<MoCServicio> listaSevicio;
    private static AdaptadorDetallesTarea adaptadorDetallesTarea = null;
    private static AdaptadorSServicio adaptadorCategoria = null;
    RecyclerView recyclerCategoria,recyclerServicio;
    EditText edtbuscador;
    TextView icoCerrarbuscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_servicio);

        MyApp varGlobal = (MyApp) getApplicationContext();
        servicio = varGlobal.getServicioId();

        listaCategoria = new ArrayList<>();
        listaSevicio = new ArrayList<>();
        recyclerServicio = findViewById(R.id.reciclerdetallesugerencia);
        recyclerServicio.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false));
        adaptadorCategoria = (AdaptadorSServicio) recyclerServicio.getAdapter();

        listaTarea = new ArrayList<>();
        listaSubTarea = new ArrayList<>();
        recyclerCategoria = findViewById(R.id.reciclerdetalle);
        recyclerCategoria.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        adaptadorDetallesTarea = (AdaptadorDetallesTarea) recyclerCategoria.getAdapter();

        imagen = findViewById(R.id.imgServicio);
        txtdescripcion = findViewById(R.id.descripcionservicio);
        txttotal = findViewById(R.id.txtTotal);
        icoCerrarbuscador = findViewById(R.id.icoAtrasSer);
        edtbuscador = findViewById(R.id.edtBuscadorSer);
        ListarClase();
        icoCerrarbuscador.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), Clases.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
            }
        });
    }

    private void ListarClase() {
        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        RequestBody formBody = new FormBody.Builder() //manda parametros
                .add("idclase", servicio)
                .build();

        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/listaServicio")  //url
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
                    listaTarea.clear();
                    listaSubTarea.clear();
                    JSONObject json = new JSONObject(myresponse);
                    JSONObject json1 = json.getJSONObject("success");


                    JSONArray array0 = json1.getJSONArray("Servicio");
                    for (int it = 0 ; it<array0.length();it++)
                    {
                        imagenes = array0.getJSONObject(it).getString("SER_Imagen");
                        descripcion = array0.getJSONObject(it).getString("SER_Descripcion");
                    }
                    JSONArray arrayTa = json1.getJSONArray("DetalleTarea");
                    for (int it = 0 ; it<arrayTa.length();it++)
                    {
                        listaTarea.add(new MDetalleTarea(arrayTa.getJSONObject(it).getString("TAR_Id"),arrayTa.getJSONObject(it).getString("SER_Id"),arrayTa.getJSONObject(it).getString("DTS_Costo"),arrayTa.getJSONObject(it).getString("DTS_Tiempo"),arrayTa.getJSONObject(it).getString("DTS_Formato"),arrayTa.getJSONObject(it).getString("TAR_Nombre")));
                    }

                    JSONArray arrayca = json1.getJSONArray("categoria");
                    for (int it = 0 ; it<arrayca.length();it++)
                    {
                        listaCategoria.add(new MCategoria(arrayca.getJSONObject(it).getString("CAT_Id"),"Sugerencias",""));
                        JSONArray array1 = json1.getJSONArray(arrayca.getJSONObject(it).getString("CAT_Nombre"));
                        for (int i = 0 ; i<array1.length();i++)
                        {
                            listaSevicio.add(new MoCServicio(array1.getJSONObject(i).getString("SER_Id"),array1.getJSONObject(i).getString("SER_Nombre"),array1.getJSONObject(i).getString("SER_Descripcion"),array1.getJSONObject(i).getString("SER_Imagen"),array1.getJSONObject(i).getString("SER_CostoHora"),arrayca.getJSONObject(it).getString("CAT_Id"),false));
                        }
                    }

                    final int radius = 25;
                    final int margin = 0;
                    final Transformation transformation = new RoundedCornersTransformation(radius,margin);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtdescripcion.setText(descripcion);
                            Picasso.get()
                                    .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+imagenes))
                                    .transform(transformation)
                                    .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                                    .into(imagen);
                            adaptadorDetallesTarea = new AdaptadorDetallesTarea(listaTarea, listaSubTarea, getApplicationContext(),txttotal);
                            recyclerCategoria.setAdapter(adaptadorDetallesTarea);
                            adaptadorCategoria = new AdaptadorSServicio(listaSevicio, getApplicationContext(), "servicio");
                            recyclerServicio.setAdapter(adaptadorCategoria);

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
