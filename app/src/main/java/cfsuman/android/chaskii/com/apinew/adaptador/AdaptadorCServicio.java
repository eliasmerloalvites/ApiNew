package cfsuman.android.chaskii.com.apinew.adaptador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cfsuman.android.chaskii.com.apinew.DetalleServicio;
import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MoAlquiler;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.modelo.MoFrelancer;
import cfsuman.android.chaskii.com.apinew.modelo.MoPromocion;
import cfsuman.android.chaskii.com.apinew.modelo.MoServicio;
import cfsuman.android.chaskii.com.apinew.ui.clase.Clases;
import cfsuman.android.chaskii.com.apinew.ui.favorito.Favoritos;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AdaptadorCServicio extends  RecyclerView.Adapter<AdaptadorCServicio.ViewHolderfamiliaes> implements Filterable {
    private ArrayList<MoCServicio> listaservicio;
    private ArrayList<MoCServicio> arraylistaservicio;
    private String modo;
   private Context context;
    private MyApp varGlobal;

    public AdaptadorCServicio(ArrayList<MoCServicio> listaservicio, Context context,String modo) {

        this.listaservicio = listaservicio;
        this.context = context;
        varGlobal = (MyApp) context;
        this.arraylistaservicio = new ArrayList<MoCServicio>();
        this.arraylistaservicio.addAll(listaservicio);
        this.modo = modo;
    }

    @NonNull
    @Override
    public ViewHolderfamiliaes onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_habitacion,null);

        return new ViewHolderfamiliaes(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderfamiliaes holder,final int i) {

        final int radius = 35;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius,margin, RoundedCornersTransformation.CornerType.TOP);

            Random random = new Random();
            holder.ratingBar.setRating(random.nextInt(3)+2);
            holder.tipo.setText(listaservicio.get(i).getNombre());
            holder.precioahora.setText("S/ "+listaservicio.get(i).getPrecio());
            Picasso.get()
                    .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listaservicio.get(i).getImagen()))
                    .transform(transformation)
                    .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                    .into(holder.imagen);
            if  (modo.equals("familia"))
            {
                if  (listaservicio.get(i).getIdCategoria().equals("0"))
                {
                    holder.precioahora.setTypeface(null,Typeface.BOLD);
                    holder.precioahora.setTextColor(Color.parseColor("#EE0807"));
                    holder.precioantes.setTextColor(Color.parseColor("#8b8b8b"));
                    holder.precioantes.setText("S/ 322.20");
                    Integer tamaño = holder.precioahora.getText().length()*2;
                    String rayas = "" ;
                    for (int f=0;f<tamaño;f++)
                    {
                        rayas = rayas + "-";
                    }
                    holder.linea.setText(rayas);
                }
                else
                {
                    holder.precioantes.setText("Desde");
                }
            }
            else
            {
                holder.precioantes.setText("Desde");
            }
        holder.linlayaut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaservicio.get(i).getId()!= "0")
                {
                    varGlobal.setServicioId(listaservicio.get(i).getId());
                    Intent intent = new Intent(context, DetalleServicio.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else
                {

                    varGlobal.setServicioId("1");

                    Intent intent = new Intent(context, DetalleServicio.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
            if (listaservicio.get(i).getFavorito())
            {
                holder.checkFavorito.setChecked(true);
            }
            else
            {
                holder.checkFavorito.setChecked(false);
            }
        holder.checkFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    ListarFavoritos("agregar",listaservicio.get(i).getId());
                } else {
                    ListarFavoritos("quitar",listaservicio.get(i).getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {

            return listaservicio.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence==null | charSequence.length() ==0)
                {
                    filterResults.count = arraylistaservicio.size();
                    filterResults.values = arraylistaservicio;
                }else{
                    String searchChar = charSequence.toString().toLowerCase();
                    List<MoCServicio> resultadoDatos = new ArrayList<>();
                    for (MoCServicio moservicio: arraylistaservicio){
                        if (moservicio.getNombre().toLowerCase().contains(searchChar))
                        {
                            resultadoDatos.add(moservicio);
                        }
                    }
                    filterResults.count = resultadoDatos.size();
                    filterResults.values = resultadoDatos;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listaservicio = (ArrayList<MoCServicio>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public class ViewHolderfamiliaes extends RecyclerView.ViewHolder {
        TextView tipo,descripcion, precioahora, masservicios,precioantes,linea;
        ImageView imagen;
        LinearLayout linlayaut;
        SimpleRatingBar ratingBar;
        CheckBox checkFavorito;

        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);

            ratingBar = itemView.findViewById(R.id.ratingBar);
            checkFavorito = itemView.findViewById(R.id.like_checkbox);
            precioahora= itemView.findViewById(R.id.txtPrecioAhora);
            precioantes = itemView.findViewById(R.id.txtPrecioAntes);;
            linea = itemView.findViewById(R.id.txtLinea);
            tipo= itemView.findViewById(R.id.txtNombre);
            imagen= itemView.findViewById(R.id.image_habitacion);
            linlayaut = itemView.findViewById(R.id.idCarview);
        }
    }

    public void ListarFavoritos(String formato,String idServicio) {
        SharedPreferences preferences = context.getSharedPreferences("preferenciaUsuario", Context.MODE_PRIVATE);
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


                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}
