package cfsuman.android.chaskii.com.apinew.adaptador;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MClase;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class AdaptadorClaseLista extends  RecyclerView.Adapter<AdaptadorClaseLista.ViewHolderfamiliaes> implements Filterable {
    private ArrayList<MClase> listaclase;
    private ArrayList<MoCServicio> listaservicio;
    private ArrayList<MoCServicio> arraylistaservicio;
    private String modo;
   private Context context;

    public AdaptadorClaseLista(ArrayList<MClase> listaclase, ArrayList<MoCServicio> listaservicio, Context context, EditText filtrar, String modo) {
        this.listaclase = listaclase;
        this.listaservicio = listaservicio;
        this.context = context;
        this.arraylistaservicio = new ArrayList<MoCServicio>();
        this.arraylistaservicio.addAll(listaservicio);
        this.modo = modo;
    }

    @NonNull
    @Override
    public ViewHolderfamiliaes onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_claselista,null);

        return new ViewHolderfamiliaes(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderfamiliaes holder, int i) {
        final int radius = 15;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius,margin, RoundedCornersTransformation.CornerType.LEFT);

        Random random = new Random();
        holder.ratingBar.setRating(random.nextInt(3)+2);
        holder.tipo.setText(listaservicio.get(i).getNombre().toUpperCase());
        holder.precioahora.setText("S/ "+listaservicio.get(i).getPrecio());
        Picasso.get()
                .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listaservicio.get(i).getImagen()))
                .resize(110, 80)
                .centerCrop()
                .transform(transformation)
                .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                .into(holder.imagen);
        if  (modo.equals("familia"))
        {
            if  (listaservicio.get(i).getIdCategoria().equals("0"))
            {
                holder.precioahora.setTextColor(Color.parseColor("#EE0807"));
                holder.precioantes.setTextColor(Color.parseColor("#8b8b8b"));
                holder.precioantes.setText("S/ 322.20");
                Integer tamaño = holder.precioahora.getText().length()*2;
                String rayas = "" ;
                for (i=0;i<tamaño;i++)
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
        RatingBar ratingBar;

        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);

            ratingBar = itemView.findViewById(R.id.ratingBar);
            precioahora= itemView.findViewById(R.id.txtPrecioAhora);
            precioantes = itemView.findViewById(R.id.txtPrecioAntes);;
            linea = itemView.findViewById(R.id.txtLinea);
            tipo= itemView.findViewById(R.id.txtNombre);
            imagen= itemView.findViewById(R.id.image_clase);
            linlayaut = itemView.findViewById(R.id.idCarview);
        }
    }



}
