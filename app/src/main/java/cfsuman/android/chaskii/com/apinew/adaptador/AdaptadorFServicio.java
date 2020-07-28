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
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.WrapContentLinearLayoutManager;
import cfsuman.android.chaskii.com.apinew.common.StartSnapHelper;
import cfsuman.android.chaskii.com.apinew.modelo.MFamilia;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.modelo.MoPromocion;
import cfsuman.android.chaskii.com.apinew.modelo.MoServicio;
import cfsuman.android.chaskii.com.apinew.ui.categoria.ACategoria;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class AdaptadorFServicio extends  RecyclerView.Adapter<AdaptadorFServicio.ViewHolderfamiliaes> implements Filterable {
    private ArrayList<MFamilia> listafamilia;
    private ArrayList<MFamilia> arrayfamilia;
    private ArrayList<MoPromocion> listapromocion;
    private ArrayList<MoCServicio> listaservicio;
    private ArrayList<MoCServicio> arrayservicio;
    private EditText edtbuscador;
    RecyclerView recyclerFamilia;
    AdaptadorCServicio adaptadorCServicio;
    private int EntrarAdapter = 0 ;

    private Context context;
    private MyApp varGlobal;

    public AdaptadorFServicio(ArrayList<MFamilia> listafamilia,ArrayList<MoCServicio> listaservicio,ArrayList<MoPromocion> listapromocion, Context context,EditText edtbuscador,RecyclerView recyclerFamilia) {
        this.listafamilia = listafamilia;
        this.arrayfamilia = new ArrayList<MFamilia>();
        this.arrayfamilia.addAll(listafamilia);
        this.listapromocion = listapromocion;
        this.listaservicio = listaservicio;
        this.arrayservicio = new ArrayList<MoCServicio>();
        this.arrayservicio.addAll(listaservicio);
        this.context = context;
        varGlobal = (MyApp) context;
        this.edtbuscador = edtbuscador;
        this.recyclerFamilia = recyclerFamilia;

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
        if (position==0 && listapromocion.size()>0)
        {
            holder.adaptadorFamiliaSlider= new AdaptadorFamiliaSlider(listapromocion,context);
            holder.recicleSlider.setAdapter(holder.adaptadorFamiliaSlider);
        }

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

        if (EntrarAdapter==0)
        {
            holder.adaptadorFamilia = new AdaptadorCServicio(holder.listaFamiliaServicio,context,"familia");
            holder.recicler.setAdapter(holder.adaptadorFamilia);
        }

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
        AdaptadorCServicio adaptadorFamilia;
        AdaptadorFamiliaSlider adaptadorFamiliaSlider;
        RecyclerView recicler,recicleSlider;
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
            recicler.setLayoutManager(new WrapContentLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
            recyclerFamilia = itemView.findViewById(R.id.recicleCategoriaServicio);
            recyclerFamilia.setLayoutManager(new WrapContentLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
            SnapHelper snapHelper = new StartSnapHelper();
            snapHelper.attachToRecyclerView(recicler);
            recicleSlider = itemView.findViewById(R.id.recicleFamiliaSlider);
            recicleSlider.setLayoutManager(new WrapContentLinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
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
                boolean entrar;
                if (charSequence==null | charSequence.length() ==0)
                {
                    filterResults.count = listafamilia.size();
                    filterResults.values = arrayfamilia;
                }else{
                    String searchChar = charSequence.toString().toLowerCase();
                    List<MFamilia> resultadoDatos = new ArrayList<>();
                    List<MoCServicio> resultadoDatosServicio = new ArrayList<>();
                    listaservicio.clear();
                    for (MFamilia mfamilia: arrayfamilia){
                        entrar = true;
                        for (MoCServicio mocservicio: arrayservicio) {
                            if (mfamilia.getId().equals(mocservicio.getIdCategoria()))
                            {
                                if (mocservicio.getNombre().toLowerCase().contains(searchChar) && entrar==true) {
                                    resultadoDatos.add(mfamilia);
                                    entrar = false;
                                }
                                if (mocservicio.getNombre().toLowerCase().contains(searchChar))
                                {
                                    resultadoDatosServicio.add(mocservicio);
                                }

                            }
                        }
                    }
                    filterResults.count = resultadoDatos.size();
                    filterResults.values = resultadoDatos;
                    adaptadorCServicio = new AdaptadorCServicio((ArrayList<MoCServicio>) resultadoDatosServicio,context,"familia");
                    recyclerFamilia.setAdapter(adaptadorCServicio);
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listafamilia = (ArrayList<MFamilia>) filterResults.values;
                notifyDataSetChanged();
                EntrarAdapter = 1;

            }
        };
        return filter;
    }

}
