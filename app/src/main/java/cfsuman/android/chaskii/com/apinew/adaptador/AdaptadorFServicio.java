package cfsuman.android.chaskii.com.apinew.adaptador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MFamilia;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.categoria.ACategoria;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class AdaptadorFServicio extends  RecyclerView.Adapter<AdaptadorFServicio.ViewHolderfamiliaes> implements Filterable {
    private ArrayList<MFamilia> listafamilia;
    private ArrayList<MoCServicio> listaservicio;
    private ArrayList<MoCServicio> arrayservicio;
    private EditText edtbuscador;

    private Context context;
    private MyApp varGlobal;

    public AdaptadorFServicio(ArrayList<MFamilia> listafamilia,ArrayList<MoCServicio> listaservicio, Context context,EditText edtbuscador) {
        this.listafamilia = listafamilia;
        this.listaservicio = listaservicio;
        this.arrayservicio = new ArrayList<MoCServicio>();
        this.arrayservicio.addAll(listaservicio);
        this.context = context;
        varGlobal = (MyApp) context;
        this.edtbuscador = edtbuscador;

    }

    @NonNull
    @Override
    public ViewHolderfamiliaes onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_categorias,null);

        return new ViewHolderfamiliaes(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderfamiliaes holder, final int position) {
        if (listafamilia.get(position).getId().equals("1"))
        {
            holder.logo.setBackgroundResource(R.drawable.ic_servicio);
        }
        else if (listafamilia.get(position).getId().equals("2"))
        {
            holder.logo.setBackgroundResource(R.drawable.ic_alquiler);
        }
        else if (listafamilia.get(position).getId().equals("3"))
        {
            holder.logo.setBackgroundResource(R.drawable.ic_freelancer);
        }
        else if (listafamilia.get(position).getId().equals("4"))
        {
            holder.logo.setBackgroundResource(R.drawable.ic_venta);
        }
        holder.Familia.setText(listafamilia.get(position).getNombre());
        holder.Familia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listafamilia.get(position).getId()!= "0")
                {
                    varGlobal.setFamiliaId(listafamilia.get(position).getId());
                    varGlobal.setFamiliaNombre(listafamilia.get(position).getNombre());

                    Intent intent = new Intent(context, ACategoria.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else
                {
                    varGlobal.setFamiliaId("1");
                    varGlobal.setFamiliaNombre(listafamilia.get(position).getNombre());

                    Intent intent = new Intent(context, ACategoria.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
        holder.linLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listafamilia.get(position).getId()!= "0")
                {
                    varGlobal.setFamiliaId(listafamilia.get(position).getId());
                    varGlobal.setFamiliaNombre(listafamilia.get(position).getNombre());

                    Intent intent = new Intent(context, ACategoria.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else
                {
                    varGlobal.setFamiliaId("1");
                    varGlobal.setFamiliaNombre(listafamilia.get(position).getNombre());

                    Intent intent = new Intent(context, ACategoria.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
        for (int i = 0; i < listaservicio.size(); i++)
        {
            if (listafamilia.get(position).getId() == listaservicio.get(i).getIdCategoria())
            {
               holder.listaFamiliaServicio.add(new MoCServicio(listaservicio.get(i).getId(),listaservicio.get(i).getNombre(),listaservicio.get(i).getDescripcion(),listaservicio.get(i).getImagen(),listaservicio.get(i).getPrecio(),listaservicio.get(i).getIdCategoria(),listaservicio.get(i).getFavorito()));
            }
        }
        if (listafamilia.get(position).getId().equals("0"))
        {
           // holder.linLay.setBackgroundResource(R.drawable.formato_cajalinearlayout);
            holder.Familia.setTextColor(Color.parseColor("#FFD640"));
            holder.Derecha.setHintTextColor(Color.parseColor("#FFD640"));
            holder.Izquierda.setHintTextColor(Color.parseColor("#FFD640"));
        }
        holder.adaptadorFamilia = new AdaptadorCServicio(holder.listaFamiliaServicio,context,"familia");
        holder.recicler.setAdapter(holder.adaptadorFamilia);
        edtbuscador.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(holder.adaptadorFamilia != null) {
                    holder.adaptadorFamilia.getFilter().filter(s);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        holder.VerMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listafamilia.get(position).getId()!= "0")
                {
                    varGlobal.setFamiliaId(listafamilia.get(position).getId());
                    varGlobal.setFamiliaNombre(listafamilia.get(position).getNombre());

                    Intent intent = new Intent(context, ACategoria.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else
                {
                    varGlobal.setFamiliaId("1");
                    varGlobal.setFamiliaNombre(listafamilia.get(position).getNombre());

                    Intent intent = new Intent(context, ACategoria.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return listafamilia.size();
    }

    public class ViewHolderfamiliaes extends RecyclerView.ViewHolder {
        ArrayList<MoCServicio> listaFamiliaServicio;
        TextView Familia,Izquierda,Derecha,VerMas,logo;
        RecyclerView recicler;
        AdaptadorCServicio adaptadorFamilia;
        LinearLayout linLay;

        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);

            listaFamiliaServicio = new ArrayList<>();
            linLay = itemView.findViewById(R.id.LinLayMayor);
            logo   = itemView.findViewById(R.id.txtlogo);
            Familia = itemView.findViewById(R.id.txtCategoria);
            VerMas = itemView.findViewById(R.id.idMasCategoria);
            Izquierda = itemView.findViewById(R.id.txtIzquierda);
            Derecha = itemView.findViewById(R.id.txtDerecho);
            recicler = itemView.findViewById(R.id.recicleCategoriaServicio);
            recicler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
            Derecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recicler.scrollBy(150, 0);
                }
            });
            Izquierda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recicler.scrollBy(-150, 0);
                }
            });


        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence==null | charSequence.length() ==0)
                {
                    filterResults.count = arrayservicio.size();
                    filterResults.values = arrayservicio;
                }else{
                    String searchChar = charSequence.toString().toLowerCase();
                    List<MoCServicio> resultadoDatos = new ArrayList<>();
                    for (MoCServicio mocservicio: arrayservicio){
                        if (mocservicio.getNombre().toLowerCase().contains(searchChar))
                        {
                            resultadoDatos.add(mocservicio);
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

}
