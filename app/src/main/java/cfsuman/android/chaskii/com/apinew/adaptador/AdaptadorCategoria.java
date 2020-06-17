package cfsuman.android.chaskii.com.apinew.adaptador;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.clase.Clases;


public class AdaptadorCategoria extends RecyclerView.Adapter<AdaptadorCategoria.ViewHolderCategoria> {

    private ArrayList<MCategoria> listacategoria;
    private ArrayList<MoCServicio> listaservicio;
    private Context context;
    private MyApp varGlobal;
    private EditText edtbuscador;

    public AdaptadorCategoria(ArrayList<MCategoria> listacategoria,ArrayList<MoCServicio> listaservicio, Context context,EditText edtbuscador) {
        this.listacategoria = listacategoria;
        this.listaservicio = listaservicio;
        this.context = context;
        varGlobal = (MyApp) context;
        this.edtbuscador = edtbuscador;
    }

    @NonNull
    @Override
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_categorias,null);
        return new ViewHolderCategoria(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCategoria holder, final int position) {

        holder.Categoria.setText(listacategoria.get(position).getNombre());
        for (int i = 0; i < listaservicio.size(); i++)
        {
            if (listacategoria.get(position).getId() == listaservicio.get(i).getIdCategoria())
            {
                holder.listaCategoriaServicio.add(new MoCServicio(listaservicio.get(i).getId(),listaservicio.get(i).getNombre(),listaservicio.get(i).getDescripcion(),listaservicio.get(i).getImagen(),listaservicio.get(i).getPrecio(),listaservicio.get(i).getIdCategoria()));
            }
        }
        holder.adaptadorCategoria = new AdaptadorCServicio(holder.listaCategoriaServicio,context,"categoria");
        holder.recicler.setAdapter(holder.adaptadorCategoria);
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
        TextView Categoria,Izquierda,Derecha,VerMas;;
        RecyclerView recicler;
        AdaptadorCServicio adaptadorCategoria;
        public ViewHolderCategoria(@NonNull View itemView) {            super(itemView);

            listaCategoriaServicio = new ArrayList<>();
            Categoria = itemView.findViewById(R.id.txtCategoria);
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
}
