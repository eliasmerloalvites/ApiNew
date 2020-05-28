package cfsuman.android.chaskii.com.apinew.adaptador;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MFamilia;
import cfsuman.android.chaskii.com.apinew.ui.categoria.categoria;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class AdaptadorFamilia extends  RecyclerView.Adapter<AdaptadorFamilia.ViewHolderfamiliaes> implements Filterable {

    private ArrayList<MFamilia> listafamilia;
    private ArrayList<MFamilia> arraylist;
   // Activity activitys;
   private Context context;
   // LayoutInflater inflater;

    public AdaptadorFamilia(ArrayList<MFamilia> listafamilia, Context context) {
        this.listafamilia = listafamilia;
       // this.activitys = activity;
        this.context = context;
        this.arraylist = new ArrayList<MFamilia>();
        this.arraylist.addAll(listafamilia);
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
        final int radius = 10;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius,margin, RoundedCornersTransformation.CornerType.TOP);

        holder.tipo.setText(listafamilia.get(i).getNombre().toUpperCase());
        Picasso.get()
                .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listafamilia.get(i).getImagen()))
                .resize(100, 80)
                .centerCrop()
                .transform(transformation)
                .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                .into(holder.imagen);
      //  holder.descripcion.setText(listafamilia.get(i).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listafamilia.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence==null | charSequence.length() ==0)
                {
                    filterResults.count = arraylist.size();
                    filterResults.values = arraylist;
                }else{
                    String searchChar = charSequence.toString().toLowerCase();
                    List<MFamilia> resultadoDatos = new ArrayList<>();
                    for (MFamilia mofamilia: arraylist){
                        if (mofamilia.getNombre().toLowerCase().contains(searchChar))
                        {
                            resultadoDatos.add(mofamilia);
                        }
                    }
                    filterResults.count = resultadoDatos.size();
                    filterResults.values = resultadoDatos;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listafamilia = (ArrayList<MFamilia>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public class ViewHolderfamiliaes extends RecyclerView.ViewHolder {
        TextView tipo,descripcion, precio, precioxnoche,num;
        ImageView imagen;


      //  String a1,a2,a3;

      //  LinearLayout contenidoEstado;
        Button next;

        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);

            tipo= itemView.findViewById(R.id.txtNombre);
            imagen= itemView.findViewById(R.id.image_habitacion);
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
