package cfsuman.android.chaskii.com.apinew.adaptador;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cfsuman.android.chaskii.com.apinew.DetalleServicio;
import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AdaptadorClaServicio extends  RecyclerView.Adapter<AdaptadorClaServicio.ViewHolderfamiliaes> implements Filterable {
    private ArrayList<MoCServicio> listaservicio;
    private ArrayList<MoCServicio> arraylistaservicio;
    private String modo;
    private Context context;
    private MyApp varGlobal;

    public AdaptadorClaServicio(ArrayList<MoCServicio> listaservicio, Context context, String modo) {

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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cclase,null);

        return new ViewHolderfamiliaes(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderfamiliaes holder,final int i) {

        final int radius = 10;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius,margin, RoundedCornersTransformation.CornerType.TOP);
            Random random = new Random();

            holder.ratingBar1.setRating(random.nextInt(3)+2);
            holder.tipo1.setText(listaservicio.get(i).getNombre());
            holder.precioahora1.setText("S/ "+listaservicio.get(i).getPrecio());
            Picasso.get()
                    .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listaservicio.get(i).getImagen()))
                    .resize(140, 80)
                    .centerCrop()
                    .transform(transformation)
                    .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                    .into(holder.imagen1);
            if  (modo.equals("familia"))
            {
                if  (listaservicio.get(i).getIdCategoria().equals("0"))
                {
                    holder.precioahora1.setTypeface(null,Typeface.BOLD);
                    holder.precioahora1.setTextColor(Color.parseColor("#EE0807"));
                    holder.precioantes1.setTextColor(Color.parseColor("#8b8b8b"));
                    holder.precioantes1.setText("S/ 322.20");
                    Integer tamaño = holder.precioahora1.getText().length()*2;
                    String rayas = "" ;
                    for (int f=0;f<tamaño;f++)
                    {
                        rayas = rayas + "-";
                    }
                    holder.linea1.setText(rayas);
                }
                else
                {
                    holder.precioantes1.setText("Desde");
                }
            }
            else
            {
                holder.precioantes1.setText("Desde");
            }
        holder.linlayaut1.setOnClickListener(new View.OnClickListener() {
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
        holder.checkFavorito.setChecked(true);
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
        TextView tipo1,precioahora1,precioantes1,linea1;
        ImageView imagen1;
        LinearLayout linlayaut1;
        SimpleRatingBar ratingBar1;
        CheckBox checkFavorito;

        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);

            checkFavorito = itemView.findViewById(R.id.like_checkbox);
            ratingBar1 = itemView.findViewById(R.id.ratingBar);
            precioahora1= itemView.findViewById(R.id.txtPrecioAhora);
            precioantes1 = itemView.findViewById(R.id.txtPrecioAntes);;
            linea1 = itemView.findViewById(R.id.txtLinea);
            tipo1 = itemView.findViewById(R.id.txtNombre);
            imagen1 = itemView.findViewById(R.id.image_habitacion);
            linlayaut1 = itemView.findViewById(R.id.idCarviewClase);


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
