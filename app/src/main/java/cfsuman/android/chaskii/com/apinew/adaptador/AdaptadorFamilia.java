package cfsuman.android.chaskii.com.apinew.adaptador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MFamilia;
import cfsuman.android.chaskii.com.apinew.modelo.MoAlquiler;
import cfsuman.android.chaskii.com.apinew.modelo.MoFrelancer;
import cfsuman.android.chaskii.com.apinew.modelo.MoPromocion;
import cfsuman.android.chaskii.com.apinew.modelo.MoServicio;
import cfsuman.android.chaskii.com.apinew.ui.categoria.ACategoria;
import cfsuman.android.chaskii.com.apinew.ui.categoria.categoria;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static cfsuman.android.chaskii.com.apinew.R.drawable.formato_cardviewalquiler;
import static cfsuman.android.chaskii.com.apinew.R.drawable.formato_cardviewfrelancer;
import static com.facebook.FacebookSdk.getApplicationContext;


public class AdaptadorFamilia extends  RecyclerView.Adapter<AdaptadorFamilia.ViewHolderfamiliaes> implements Filterable {
    private ArrayList<MoPromocion> listapromocion;
    private ArrayList<MoPromocion> arraylistpromocion;
    private ArrayList<MoServicio> listaservicio;
    private ArrayList<MoServicio> arraylistservicio;
    private ArrayList<MoFrelancer> listafrelancer;
    private ArrayList<MoFrelancer> arraylistfrelancer;
    private ArrayList<MoAlquiler> listaalquiler;
    private ArrayList<MoAlquiler> arraylistalquiler;
    private Integer Tipo;
    private Activity activitys;
   private Context context;
   // LayoutInflater inflater;

    public AdaptadorFamilia(ArrayList<MoPromocion> listaPromocion, ArrayList<MoServicio> listaServicio, ArrayList<MoFrelancer> listafrelancer, ArrayList<MoAlquiler> listaAlquiler, Context context, Integer tipo) {
        this.listapromocion = listaPromocion;
        this.listaservicio = listaServicio;
        this.listafrelancer = listafrelancer;
        this.listaalquiler = listaAlquiler;
     //   this.activitys = activity;
        this.Tipo = tipo;
        this.context = context;
        this.arraylistpromocion = new ArrayList<MoPromocion>();
        this.arraylistpromocion.addAll(listaPromocion);
        this.arraylistservicio = new ArrayList<MoServicio>();
        this.arraylistservicio.addAll(listaServicio);
        this.arraylistfrelancer = new ArrayList<MoFrelancer>();
        this.arraylistfrelancer.addAll(listafrelancer);
        this.arraylistalquiler = new ArrayList<MoAlquiler>();
        this.arraylistalquiler.addAll(listaAlquiler);
     //   inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolderfamiliaes onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_habitacion,null);

        return new ViewHolderfamiliaes(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderfamiliaes holder, int i) {

        //LayerDrawable drawable = (LayerDrawable) holder.ratingBar.getProgressDrawable();

        /*drawable.getDrawable(2).setColorFilter(Color.parseColor("#FFD640"), PorterDuff.Mode.SRC_ATOP);
        drawable.getDrawable(1).setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        drawable.getDrawable(0).setColorFilter(Color.parseColor("#FFD640"), PorterDuff.Mode.SRC_ATOP);*/

        final int radius = 10;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius,margin, RoundedCornersTransformation.CornerType.TOP);
        if (Tipo == 0)
        {
             Random random = new Random();
            holder.ratingBar.setRating(random.nextInt(3)+2);
            //drawable.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
            holder.tipo.setText(listapromocion.get(i).getNombre().toUpperCase());
            holder.precioahora.setText("S/ "+listapromocion.get(i).getPrecio());
            holder.precioahora.setTextColor(Color.parseColor("#EE0807"));
            Picasso.get()
                    .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listapromocion.get(i).getImagen()))
                    .resize(110, 75)
                    .centerCrop()
                    .transform(transformation)
                    .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                    .into(holder.imagen);
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
        if (Tipo == 1)
        {
            Random random = new Random();
            holder.ratingBar.setRating(random.nextInt(3)+2);
            holder.tipo.setText(listaservicio.get(i).getNombre().toUpperCase());
            holder.precioahora.setText("S/ "+listaservicio.get(i).getPrecio());
            Picasso.get()
                    .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listaservicio.get(i).getImagen()))
                    .resize(110, 75)
                    .centerCrop()
                    .transform(transformation)
                    .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                    .into(holder.imagen);
            holder.precioantes.setTextColor(Color.parseColor("#8b8b8b"));
            holder.precioantes.setText("Desde");
        }
        if (Tipo == 2)
        {
            Random random = new Random();
            holder.ratingBar.setRating(random.nextInt(3)+2);
            holder.tipo.setText(listafrelancer.get(i).getNombre().toUpperCase());
            holder.precioahora.setText("S/ "+listafrelancer.get(i).getPrecio());
            Picasso.get()
                    .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listafrelancer.get(i).getImagen()))
                    .resize(110, 75)
                    .centerCrop()
                    .transform(transformation)
                    .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                    .into(holder.imagen);
            holder.precioantes.setText("Desde");
            //holder.linlayaut.setBackgroundResource(formato_cardviewalquiler);
        }
        if (Tipo == 3)
        {
            Random random = new Random();
            holder.ratingBar.setRating(random.nextInt(3)+2);
            holder.tipo.setText(listaalquiler.get(i).getNombre().toUpperCase());
            holder.precioahora.setText("S/ "+listaalquiler.get(i).getPrecio());
            Picasso.get()
                    .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listaalquiler.get(i).getImagen()))
                    .resize(110, 75)
                    .centerCrop()
                    .transform(transformation)
                    .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                    .into(holder.imagen);
            holder.precioantes.setText("Desde");
           // holder.linlayaut.setBackgroundResource(formato_cardviewalquiler);
        }

    }

    @Override
    public int getItemCount() {
        if (Tipo == 0)
        {
            return listapromocion.size();
        }
        if (Tipo == 1)
        {
            return listaservicio.size();
        }
        else if (Tipo == 2)
        {
            return listafrelancer.size();
        }
        else if (Tipo == 3)
        {
            return listaalquiler.size();
        }
        return 5;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence==null | charSequence.length() ==0)
                {
                    filterResults.count = arraylistservicio.size();
                    filterResults.values = arraylistservicio;
                }else{
                    String searchChar = charSequence.toString().toLowerCase();
                    List<MoServicio> resultadoDatos = new ArrayList<>();
                    for (MoServicio moservicio: arraylistservicio){
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
                listaservicio = (ArrayList<MoServicio>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
    public Filter getFilterPr() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence==null | charSequence.length() ==0)
                {
                    filterResults.count = arraylistpromocion.size();
                    filterResults.values = arraylistpromocion;
                }else{
                    String searchChar = charSequence.toString().toLowerCase();
                    List<MoPromocion> resultadoDatos = new ArrayList<>();
                    for (MoPromocion mopromocion: arraylistpromocion){
                        if (mopromocion.getNombre().toLowerCase().contains(searchChar))
                        {
                            resultadoDatos.add(mopromocion);
                        }
                    }
                    filterResults.count = resultadoDatos.size();
                    filterResults.values = resultadoDatos;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listapromocion = (ArrayList<MoPromocion>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
    public Filter getFilterFr() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence==null | charSequence.length() ==0)
                {
                    filterResults.count = arraylistfrelancer.size();
                    filterResults.values = arraylistfrelancer;
                }else{
                    String searchChar = charSequence.toString().toLowerCase();
                    List<MoFrelancer> resultadoDatos = new ArrayList<>();
                    for (MoFrelancer mofrelancer: arraylistfrelancer){
                        if (mofrelancer.getNombre().toLowerCase().contains(searchChar))
                        {
                            resultadoDatos.add(mofrelancer);
                        }
                    }
                    filterResults.count = resultadoDatos.size();
                    filterResults.values = resultadoDatos;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listafrelancer = (ArrayList<MoFrelancer>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
    public Filter getFilterAl() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence==null | charSequence.length() ==0)
                {
                    filterResults.count = arraylistalquiler.size();
                    filterResults.values = arraylistalquiler;
                }else{
                    String searchChar = charSequence.toString().toLowerCase();
                    List<MoAlquiler> resultadoDatos = new ArrayList<>();
                    for (MoAlquiler moalquiler: arraylistalquiler){
                        if (moalquiler.getNombre().toLowerCase().contains(searchChar))
                        {
                            resultadoDatos.add(moalquiler);
                        }
                    }
                    filterResults.count = resultadoDatos.size();
                    filterResults.values = resultadoDatos;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listaalquiler = (ArrayList<MoAlquiler>) filterResults.values;
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


      //  String a1,a2,a3;

      //  LinearLayout contenidoEstado;
        Button next;

        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);

            ratingBar = itemView.findViewById(R.id.ratingBar);
            precioahora= itemView.findViewById(R.id.txtPrecioAhora);
            precioantes = itemView.findViewById(R.id.txtPrecioAntes);;
            linea = itemView.findViewById(R.id.txtLinea);
            tipo= itemView.findViewById(R.id.txtNombre);
            imagen= itemView.findViewById(R.id.image_habitacion);
            linlayaut = itemView.findViewById(R.id.idCarview);


            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    Toast.makeText(context,"Usted a votado con"+v,Toast.LENGTH_LONG).show();
                }
            });
           // descripcion=(TextView) itemView.findViewById(R.id.txtDescripcion);


            /*next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Fragment fragment;
                    FragmentTransaction ft;
                    switch (view.getId()){

                        case R.id.nextCategoria :  fragment = new categoria();
                            ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.nav_host_fragment, fragment).addToBackStack(null);
                            ft.commit();
                            break;*//*
                        case R.id.idComputadora :fragment = Productos.newInstance("COMPUTADORA","");
                            ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.nav_host_fragment, fragment).addToBackStack(null);
                            ft.commit();
                            break;
                        case R.id.idTable : fragment = Productos.newInstance("TABLET","");
                            ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.nav_host_fragment, fragment).addToBackStack(null);
                            ft.commit();
                            break;*//*
                    }

                }
            });*/



        }
    }



}
