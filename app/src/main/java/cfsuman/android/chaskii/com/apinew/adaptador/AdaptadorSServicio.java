package cfsuman.android.chaskii.com.apinew.adaptador;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cfsuman.android.chaskii.com.apinew.DetalleServicio;
import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class AdaptadorSServicio extends  RecyclerView.Adapter<AdaptadorSServicio.ViewHolderfamiliaes> {
    private ArrayList<MoCServicio> listaservicio;
    private ArrayList<MoCServicio> arraylistaservicio;
    private String modo;
   private Context context;
    private MyApp varGlobal;

    public AdaptadorSServicio(ArrayList<MoCServicio> listaservicio, Context context, String modo) {

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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_hservicio,null);

        return new ViewHolderfamiliaes(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderfamiliaes holder,final int i) {

        final int radius = 10;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius,margin, RoundedCornersTransformation.CornerType.TOP);

            Random random = new Random();
            Double est = 1 + (5 - 1) * random.nextDouble();
            holder.txtEstrella.setText(String.format("%.1f", est));
            holder.ratingBar.setRating(random.nextInt(3)+2);
            holder.tipo.setText(listaservicio.get(i).getNombre().toUpperCase());
            Picasso.get()
                    .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listaservicio.get(i).getImagen()))
                    .resize(100, 70)
                    .centerCrop()
                    .transform(transformation)
                    .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                    .into(holder.imagen);
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

    }

    @Override
    public int getItemCount() {
            return listaservicio.size();
    }

    public class ViewHolderfamiliaes extends RecyclerView.ViewHolder {
        TextView tipo,txtEstrella;
        ImageView imagen;
        LinearLayout linlayaut;
        SimpleRatingBar ratingBar;

        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);

            ratingBar = itemView.findViewById(R.id.ratingBar);
            tipo= itemView.findViewById(R.id.txtNombre);
            imagen= itemView.findViewById(R.id.image_habitacion);
            txtEstrella = itemView.findViewById(R.id.txtEstrella);
            linlayaut = itemView.findViewById(R.id.idCarview);
        }
    }



}
