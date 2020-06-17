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

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.clase.Clases;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class AdaptadorCategoriaLista extends  RecyclerView.Adapter<AdaptadorCategoriaLista.ViewHolderfamiliaes> implements Filterable {
    private ArrayList<MCategoria> listacategoria;
    private ArrayList<MoCServicio> listaservicio;
    private ArrayList<MCategoria> arraylistacategoria;
    private String modo;
    private MyApp varGlobal;
   private Context context;

    public AdaptadorCategoriaLista(ArrayList<MCategoria> listacategoria, ArrayList<MoCServicio> listaservicio, Context context, String modo) {
        this.listacategoria = listacategoria;
        this.listaservicio = listaservicio;
        this.context = context;
        varGlobal = (MyApp) context;
        this.arraylistacategoria = new ArrayList<MCategoria>();
        this.arraylistacategoria.addAll(listacategoria);
        this.modo = modo;
    }

    @NonNull
    @Override
    public ViewHolderfamiliaes onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_categoria,null);

        return new ViewHolderfamiliaes(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderfamiliaes holder, final int i) {
        final int radius = 15;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius,margin, RoundedCornersTransformation.CornerType.LEFT);

        holder.Nombre.setText(listacategoria.get(i).getNombre());
        int cont = 0;
        Boolean Entro = false;

        while ( cont < listaservicio.size() && !Entro ) {
            cont++;
            if (listacategoria.get(i).getId() == listaservicio.get(cont).getIdCategoria())
            {
                Picasso.get()
                        .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listaservicio.get(cont).getImagen()))
                        .resize(110, 80)
                        .centerCrop()
                        .transform(transformation)
                        .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                        .into(holder.Imagen);
                Entro=true;
            }
        }
        holder.linlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listacategoria.get(i).getId()!= "0")
                {
                    varGlobal.setCategoriaId(listacategoria.get(i).getId());
                    varGlobal.setCategoriaNombre(listacategoria.get(i).getNombre());

                    Intent intent = new Intent(context, Clases.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else
                {
                    varGlobal.setCategoriaId("1");
                    varGlobal.setCategoriaNombre(listacategoria.get(i).getNombre());

                    Intent intent = new Intent(context, Clases.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

            return listacategoria.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence==null | charSequence.length() ==0)
                {
                    filterResults.count = arraylistacategoria.size();
                    filterResults.values = arraylistacategoria;
                }else{
                    String searchChar = charSequence.toString().toLowerCase();
                    List<MCategoria> resultadoDatos = new ArrayList<>();
                    for (MCategoria mcategoria: arraylistacategoria){
                        if (mcategoria.getNombre().toLowerCase().contains(searchChar))
                        {
                            resultadoDatos.add(mcategoria);
                        }
                    }
                    filterResults.count = resultadoDatos.size();
                    filterResults.values = resultadoDatos;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listacategoria = (ArrayList<MCategoria>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public class ViewHolderfamiliaes extends RecyclerView.ViewHolder {
        TextView Nombre;
        ImageView Imagen;
        LinearLayout linlay;

        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);

            Nombre= itemView.findViewById(R.id.txtCategoriaNombre);
            Imagen= itemView.findViewById(R.id.image_categoria);
            linlay= itemView.findViewById(R.id.idCarview);

        }
    }



}
