package cfsuman.android.chaskii.com.apinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorClase;
import cfsuman.android.chaskii.com.apinew.modelo.MClase;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetalleServicio extends AppCompatActivity {
    private static String  servicio = "",imagenes="",descripcion="";
    TextView txtdescripcion;
    ImageView imagen ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_servicio);

        MyApp varGlobal = (MyApp) getApplicationContext();
        servicio = varGlobal.getServicioId();

        imagen = findViewById(R.id.imgServicio);
        txtdescripcion = findViewById(R.id.descripcionservicio);
        ListarClase();
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
                    JSONObject json = new JSONObject(myresponse);
                    JSONObject json1 = json.getJSONObject("success");


                    JSONArray array0 = json1.getJSONArray("Servicio");
                    for (int it = 0 ; it<array0.length();it++)
                    {
                        imagenes = array0.getJSONObject(it).getString("SER_Imagen");
                        descripcion = array0.getJSONObject(it).getString("SER_Descripcion");
                    //        listaSevicio.add(new MoCServicio(array1.getJSONObject(i).getString("SER_Id"),array1.getJSONObject(i).getString("SER_Nombre"),array1.getJSONObject(i).getString("SER_Descripcion"),array1.getJSONObject(i).getString("SER_Imagen"),array1.getJSONObject(i).getString("SER_CostoHora"),array0.getJSONObject(it).getString("CLA_Id")));

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
