package cfsuman.android.chaskii.com.apinew.adaptador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.WrapContentLinearLayoutManager;
import cfsuman.android.chaskii.com.apinew.common.StartSnapHelper;
import cfsuman.android.chaskii.com.apinew.modelo.MAdicionales;
import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MoCAdicionales;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.clase.Clases;


public class AdaptadorCategoria extends RecyclerView.Adapter<AdaptadorCategoria.ViewHolderCategoria> {

    private ArrayList<MAdicionales> listaAdicional;
    private ArrayList<MoCAdicionales> listaCAdicional;
    private ArrayList<MCategoria> listacategoria;
    private ArrayList<MoCServicio> listaservicio;
    private Context context;
    private MyApp varGlobal;
    private EditText edtbuscador;
    private Integer rango = 0 ,contador,indice;
    private Activity activity;

    public AdaptadorCategoria(ArrayList<MCategoria> listacategoria, ArrayList<MoCServicio> listaservicio, ArrayList<MAdicionales> listaAdicional, ArrayList<MoCAdicionales> listaCAdicional, Context context, Integer rango, EditText edtbuscador, Activity activity) {
        this.listaAdicional = listaAdicional;
        this.listaCAdicional = listaCAdicional;
        this.listacategoria = listacategoria;
        this.listaservicio = listaservicio;
        this.activity = activity;
        this.context = context;
        varGlobal = (MyApp) context;
        this.edtbuscador = edtbuscador;
        this.rango = rango;
        contador = rango;
        indice = 1;
    }

    @NonNull
    @Override
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_categorias,null);
        return new ViewHolderCategoria(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCategoria holder, final int position) {


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
                    /*holder.adaptadorCategoriaSliderOficial= new AdaptadorCategoriaSliderOficial(holder.listaCAdicional1,context);
                    holder.recicleAdicionales.setAdapter(holder.adaptadorCategoriaSlider);*/
                    holder.adaptadorCategoriaSlider = new AdaptadorCategoriaSlider(holder.listaCAdicional1,context);
                    holder.recicleAdicionales.setAdapter(holder.adaptadorCategoriaSlider);
                    holder.adaptadorCategoriaSlider.notifyDataSetChanged();
                }
            });

            indice = indice + 1;
            contador = 0;
        }
        contador = contador + 1 ;

        holder.Categoria.setText(listacategoria.get(position).getNombre());
        for (int i = 0; i < listaservicio.size(); i++)
        {
            if (listacategoria.get(position).getId() == listaservicio.get(i).getIdCategoria())
            {
                holder.listaCategoriaServicio.add(new MoCServicio(listaservicio.get(i).getId(),listaservicio.get(i).getNombre(),listaservicio.get(i).getDescripcion(),listaservicio.get(i).getImagen(),listaservicio.get(i).getPrecio(),listaservicio.get(i).getIdCategoria(),false));
            }
        }
        holder.adaptadorCategoria = new AdaptadorCServicio(holder.listaCategoriaServicio,context,"categoria");
        holder.recicler.setAdapter(holder.adaptadorCategoria);
        holder.recicler.getRecycledViewPool().clear();
        holder.adaptadorCategoria.notifyDataSetChanged();
        edtbuscador.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(holder.adaptadorCategoria != null) {
                    holder.adaptadorCategoria.getFilter().filter(s);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        holder.VerMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listacategoria.get(position).getId()!= "0")
                {

                    varGlobal.setCategoriaId(listacategoria.get(position).getId());
                    varGlobal.setCategoriaNombre(listacategoria.get(position).getNombre());

                    Intent intent = new Intent(context, Clases.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else
                {
                    varGlobal.setCategoriaId("1");
                    varGlobal.setCategoriaNombre(listacategoria.get(position).getNombre());

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

    public class ViewHolderCategoria extends RecyclerView.ViewHolder {
        ArrayList<MoCServicio> listaCategoriaServicio;
        ArrayList<MoCAdicionales> listaCAdicional1;
        TextView CategoriaSlider,Categoria,Izquierda,Derecha,VerMas;
        AdaptadorCategoriaSlider adaptadorCategoriaSlider;
        RecyclerView recicler,recicleAdicionales;
        AdaptadorCServicio adaptadorCategoria;
        AdaptadorCategoriaSliderOficial adaptadorCategoriaSliderOficial;
        public ViewHolderCategoria(@NonNull View itemView) {
            super(itemView);

            listaCategoriaServicio = new ArrayList<>();
            listaCAdicional1 = new ArrayList<>();
            CategoriaSlider = itemView.findViewById(R.id.txtCategoriaSlider);
            Categoria = itemView.findViewById(R.id.txtCategoria);
            VerMas = itemView.findViewById(R.id.idMasCategoria);
            Izquierda = itemView.findViewById(R.id.txtIzquierda);
            Derecha = itemView.findViewById(R.id.txtDerecho);
            recicler = itemView.findViewById(R.id.recicleCategoriaServicio);
            recicler.setLayoutManager(new WrapContentLinearLayoutManager(context, WrapContentLinearLayoutManager.HORIZONTAL,false));
            recicleAdicionales = itemView.findViewById(R.id.recicleFamiliaSlider);
            recicleAdicionales.setLayoutManager(new WrapContentLinearLayoutManager(context, WrapContentLinearLayoutManager.HORIZONTAL,false));

            SnapHelper snapHelper1 = new StartSnapHelper();
            snapHelper1.attachToRecyclerView(recicler);

            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(recicleAdicionales);

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
}
