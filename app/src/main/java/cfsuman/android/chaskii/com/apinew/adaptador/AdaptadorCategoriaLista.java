package cfsuman.android.chaskii.com.apinew.adaptador;

import android.app.Activity;
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
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.WrapContentLinearLayoutManager;
import cfsuman.android.chaskii.com.apinew.modelo.MAdicionales;
import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MoCAdicionales;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.clase.Clases;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class AdaptadorCategoriaLista extends  RecyclerView.Adapter<AdaptadorCategoriaLista.ViewHolderfamiliaes> implements Filterable {

    private ArrayList<MAdicionales> listaAdicional;
    private ArrayList<MoCAdicionales> listaCAdicional;
    private ArrayList<MCategoria> listacategoria;
    private ArrayList<MoCServicio> listaservicio;
    private ArrayList<MCategoria> arraylistacategoria;
    private String modo;
    private MyApp varGlobal;
   private Context context;
    private Integer rango = 0 ,contador,indice;
    private Activity activity;

    public AdaptadorCategoriaLista(ArrayList<MCategoria> listacategoria, ArrayList<MoCServicio> listaservicio, ArrayList<MAdicionales> listaAdicional, ArrayList<MoCAdicionales> listaCAdicional, Context context, String modo, Integer rango, Activity activity) {
        this.listaAdicional = listaAdicional;
        this.listaCAdicional = listaCAdicional;
        this.listacategoria = listacategoria;
        this.listaservicio = listaservicio;
        this.activity = activity;
        this.context = context;
        varGlobal = (MyApp) context;
        this.arraylistacategoria = new ArrayList<MCategoria>();
        this.arraylistacategoria.addAll(listacategoria);
        this.modo = modo;
        this.rango = rango;
        contador = rango;
        indice = 1;
    }

    @NonNull
    @Override
    public ViewHolderfamiliaes onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_categoria,null);

        return new ViewHolderfamiliaes(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderfamiliaes holder, final int i) {

        if (listaCAdicional.size()>0 && contador==rango && indice <= listaAdicional.size())
        {
            for (int ij = 0 ; ij < listaCAdicional.size(); ij++ )
            {
                if (this.listaCAdicional.get(ij).getIdCategoria().equals(indice.toString()))
                {
                    // this.listaCAdicional1.add(new MoCAdicionales(listaCAdicional.get(ij).getId(),listaCAdicional.get(ij).getNombre(),listaCAdicional.get(ij).getDescripcion(),listaCAdicional.get(ij).getImagen(),listaCAdicional.get(ij).getPrecio(),listaCAdicional.get(ij).getIdCategoria(),false));
                    holder.listaCAdicional1.add(new MoCAdicionales(listaCAdicional.get(ij).getId(),listaCAdicional.get(ij).getNombre(),listaCAdicional.get(ij).getDescripcion(),listaCAdicional.get(ij).getImagen(),listaCAdicional.get(ij).getPrecio(),listaCAdicional.get(ij).getIdCategoria(),false));

                }
            }
            holder.CategoriaSlider.setTextSize(16);
            holder.CategoriaSlider.setText(String.valueOf(listaAdicional.get(indice-1).getNombre()));

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    holder.adaptadorCategoriaSlider = new AdaptadorCategoriaSlider(holder.listaCAdicional1,context);
                    holder.recicleAdicionales.setAdapter(holder.adaptadorCategoriaSlider);
                    holder.adaptadorCategoriaSlider.notifyDataSetChanged();
                }
            });

            indice = indice + 1;
            contador = 0;
        }
        contador = contador + 1 ;

        final int radius = 15;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius,margin, RoundedCornersTransformation.CornerType.LEFT);

        holder.Nombre.setText(listacategoria.get(i).getNombre());
        holder.Descripcion.setText(listacategoria.get(i).getDescripcion());
        holder.CostoMin.setText("S/ "+listacategoria.get(i).getCostoMin());
        holder.CostoMax.setText("S/ "+listacategoria.get(i).getCostoMax());

        int cont = 0;
        Boolean Entro = false;

        while ( cont < listaservicio.size() && !Entro ) {
            cont++;
            if (listacategoria.get(i).getId() == listaservicio.get(cont).getIdCategoria())
            {
                Picasso.get()
                        .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listaservicio.get(cont).getImagen()))
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
        TextView CostoMin;
        TextView CostoMax;
        TextView Nombre;
        TextView Descripcion;
        ImageView Imagen;
        LinearLayout linlay;
        TextView CategoriaSlider;
        ArrayList<MoCAdicionales> listaCAdicional1;
        AdaptadorCategoriaSlider adaptadorCategoriaSlider;
        RecyclerView recicleAdicionales;

        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);

            listaCAdicional1 = new ArrayList<>();
            recicleAdicionales = itemView.findViewById(R.id.recicleFamiliaSlider);
            recicleAdicionales.setLayoutManager(new WrapContentLinearLayoutManager(context, WrapContentLinearLayoutManager.HORIZONTAL,false));

            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(recicleAdicionales);

            CategoriaSlider = itemView.findViewById(R.id.txtCategoriaSlider);
            Nombre= itemView.findViewById(R.id.txtCategoriaNombre);
            Descripcion= itemView.findViewById(R.id.txtCategoriaDescripcion);
            CostoMin= itemView.findViewById(R.id.txtPrecioDesde);
            CostoMax= itemView.findViewById(R.id.txtPrecioHasta);
            Imagen= itemView.findViewById(R.id.image_categoria);
            linlay= itemView.findViewById(R.id.idCarview);

        }
    }



}
