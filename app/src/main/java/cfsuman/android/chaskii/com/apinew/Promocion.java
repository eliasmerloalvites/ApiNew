package cfsuman.android.chaskii.com.apinew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorDetallesTarea;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorFServicio;
import cfsuman.android.chaskii.com.apinew.adaptador.SliderAdapterPromocion;
import cfsuman.android.chaskii.com.apinew.adaptador.SliderAdapterPromocionDetalle;
import cfsuman.android.chaskii.com.apinew.modelo.MDetalleImagen;
import cfsuman.android.chaskii.com.apinew.modelo.MDetalleSubTarea;
import cfsuman.android.chaskii.com.apinew.modelo.MDetalleTarea;
import cfsuman.android.chaskii.com.apinew.modelo.MFamilia;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.modelo.MoPromocion;
import cfsuman.android.chaskii.com.apinew.ui.home.Inicio;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Promocion extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mSliderViewPager;
    private LinearLayout mLinearLayoutH,Contenido;
    private ConstraintLayout General;
    private SliderAdapterPromocionDetalle sliderAdapter;
    private int mCurrentPage;
    private TextView[] mText;
    private TextView ini,fin,proveedor,nombre,descripcion,txttotal,CantMenos,CantMas,HorMenos,HorMas,Cerrar;
    private EditText Cantidad,Horario;
    private MyApp varGlobal;
    private Integer Icantidad = 1 ,Itiempo;
    ArrayList<MoPromocion> listaPromocion;
    ArrayList<MDetalleImagen> listaImagen;
    ArrayList<MDetalleTarea> listaTarea;
    ArrayList<MDetalleSubTarea> listaSubTarea;
    RecyclerView recyclerCategoria,recyclerServicio;
    private static AdaptadorDetallesTarea adaptadorDetallesTarea = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocion);

        listaTarea = new ArrayList<>();
        listaSubTarea = new ArrayList<>();
        recyclerCategoria = findViewById(R.id.reciclerdetalle);
        recyclerCategoria.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        adaptadorDetallesTarea = (AdaptadorDetallesTarea) recyclerCategoria.getAdapter();

        listaPromocion = new ArrayList<>();
        listaImagen = new ArrayList<>();
        mSliderViewPager = findViewById(R.id.idViewPagerPromo);
        mLinearLayoutH = findViewById(R.id.idLinearLayourConteo);
        ini = findViewById(R.id.idInicio);
        fin = findViewById(R.id.idFin);
        nombre = findViewById(R.id.idNombre);
        descripcion = findViewById(R.id.idDescripcion);
        txttotal = findViewById(R.id.txtTotal);
        Cantidad = findViewById(R.id.idCantidadPromocion);
        Horario = findViewById(R.id.idTiempoPromocion);
        CantMenos = findViewById(R.id.idCantidadMenos);
        CantMas = findViewById(R.id.idCantidadMas);
        HorMenos = findViewById(R.id.idHorarioMenos);
        HorMas = findViewById(R.id.idHorarioMas);
        Cerrar = findViewById(R.id.idCerrar);
        Contenido = findViewById(R.id.idcontenido);
        General = findViewById(R.id.idgeneral);
        proveedor = findViewById(R.id.idProveedor);
        varGlobal = (MyApp) getApplicationContext();
        ListarPromocion();
        mSliderViewPager.addOnPageChangeListener(viewListener);
        CantMenos.setOnClickListener(this);
        CantMas.setOnClickListener(this);
        HorMenos.setOnClickListener(this);
        HorMas.setOnClickListener(this);
        Cerrar.setOnClickListener(this);
        General.setOnClickListener(this);
        Contenido.setOnClickListener(this);


    }
    private void ListarPromocion() {
        SharedPreferences preferences = getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String idUsuario = preferences.getString("id","");
        RequestBody formBody = new FormBody.Builder() //manda parametros
                .add("PROM_Id", String.valueOf(varGlobal.getPromocionId()) )
                .build();

        Request request = new Request.Builder()
                .url("http://subdominio.maprocorp.com/api/listaPromocion")  //url
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
                    JSONObject json = new JSONObject(myresponse);
                    JSONObject json1 = json.getJSONObject("success");

                    JSONArray arrayImagen = json1.getJSONArray("DetalleImagen");
                    JSONArray arrayPromocion = json1.getJSONArray("Promocion");

                    listaPromocion.add(new MoPromocion(arrayPromocion.getJSONObject(0).getString("PROM_Id"),arrayPromocion.getJSONObject(0).getString("PROM_Nombre"),arrayPromocion.getJSONObject(0).getString("PROM_Descripcion"),arrayPromocion.getJSONObject(0).getString("PROM_Imagen"),arrayPromocion.getJSONObject(0).getString("PROM_CostoHora"),arrayPromocion.getJSONObject(0).getString("PROM_Formato"),arrayPromocion.getJSONObject(0).getString("PROM_TiempoHora"),arrayPromocion.getJSONObject(0).getString("PROV_RazonSocial")));
                    for (int i = 0 ; i<arrayImagen.length();i++)
                    {
                        listaImagen.add(new MDetalleImagen(arrayImagen.getJSONObject(i).getString("DIS_Id"),arrayImagen.getJSONObject(i).getString("PROM_Id"),arrayImagen.getJSONObject(i).getString("DIS_Nombre")));
                    }

                    JSONArray arrayTa = json1.getJSONArray("DetalleTarea");
                    for (int it = 0 ; it<arrayTa.length();it++)
                    {
                        listaTarea.add(new MDetalleTarea(arrayTa.getJSONObject(it).getString("TAR_Id"),arrayTa.getJSONObject(it).getString("PROM_Id"),arrayTa.getJSONObject(it).getString("DTS_Costo"),arrayTa.getJSONObject(it).getString("DTS_Tiempo"),arrayTa.getJSONObject(it).getString("DTS_Formato"),arrayTa.getJSONObject(it).getString("TAR_Nombre")));
                    }
                    varGlobal.setCantCategoriaDetalle(String.valueOf(listaImagen.size()) );
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            proveedor.setText(listaPromocion.get(0).getProveedor());
                            nombre.setText(listaPromocion.get(0).getNombre());
                            descripcion.setText(listaPromocion.get(0).getDescripcion());
                            Itiempo = Integer.valueOf(listaPromocion.get(0).getTiempo()) ;
                            Horario.setText(listaPromocion.get(0).getTiempo());
                            sliderAdapter = new SliderAdapterPromocionDetalle(getApplicationContext(),listaImagen);
                            mSliderViewPager.setAdapter(sliderAdapter);
                            adaptadorDetallesTarea = new AdaptadorDetallesTarea(listaTarea, listaSubTarea, getApplicationContext(),txttotal);
                            recyclerCategoria.setAdapter(adaptadorDetallesTarea);
                            addTextLayoutIndicador(0);
                         //   segundo();
                        }
                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    private void segundo()
    {
        CountDownTimer countDownTimer1 = new CountDownTimer(Integer.valueOf(varGlobal.getCantCategoriaDetalle())*3*1000, 3000) {
            public void onTick(long millisUntilFinished) {
                if (mCurrentPage == mText.length - 1 )
                {
                    mCurrentPage = 0;
                    mSliderViewPager.setCurrentItem(mCurrentPage);
                }
                else
                {
                    mSliderViewPager.setCurrentItem(mCurrentPage + 1);
                }

            }

            public void onFinish() {
                segundo();
            }
        }.start();
    }
    public void addTextLayoutIndicador(int pos)
    {
        mText = new TextView[Integer.valueOf(varGlobal.getCantCategoriaDetalle()) ];
        //mLinearLayoutH.removeAllViews();
        /*for (int i = 0 ; i <mText.length; i++)
        {
            mText[i]= new TextView(this);
            mText[i].setText(String.valueOf(varGlobal.getCantCategoria()));
            mText[i].setTextSize(12);
            mText[i].setTextColor(getResources().getColor(R.color.edtColorBlack));

            mLinearLayoutH.addView(mText[i]);
        }*/
        fin.setText(String.valueOf(varGlobal.getCantCategoriaDetalle()));
        if (mText.length>0)
        {
            ini.setText(String.valueOf(pos+1));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addTextLayoutIndicador(position);
            mCurrentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.idCantidadMenos:
                if (Icantidad>1)
                {
                    Icantidad = Icantidad - 1;
                    Cantidad.setText(String.valueOf(Icantidad));
                }

                break;
            case R.id.idCantidadMas:

                Icantidad = Icantidad + 1;
                Cantidad.setText(String.valueOf(Icantidad));

                break;
            case R.id.idHorarioMenos:
                if (Itiempo>1)
                {
                    Itiempo = Itiempo - 1;
                    Horario.setText(String.valueOf(Itiempo));
                }

                break;
            case R.id.idHorarioMas:

                Itiempo = Itiempo + 1;
                Horario.setText(String.valueOf(Itiempo));

                break;
            case R.id.idCerrar:

                onBackPressed();

                break;
            case R.id.idgeneral:

                onBackPressed();

                break;
            case R.id.idcontenido:

                Toast.makeText(getApplicationContext(), "Muy Bien", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }
}
