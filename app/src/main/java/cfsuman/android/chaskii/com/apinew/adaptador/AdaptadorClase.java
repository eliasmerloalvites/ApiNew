package cfsuman.android.chaskii.com.apinew.adaptador;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Random;

import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MClase;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.clase.Clases;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class AdaptadorClase extends RecyclerView.Adapter<AdaptadorClase.ViewHolderCategoria> {

    private ArrayList<MClase> listaclase;
    private ArrayList<MoCServicio> listaservicio;
    private Context context;
    private EditText edtbuscador;

    public AdaptadorClase(ArrayList<MClase> listaclase, ArrayList<MoCServicio> listaservicio, Context context, EditText edtbuscador) {
        this.listaclase = listaclase;
        this.listaservicio = listaservicio;
        this.context = context;
        this.edtbuscador = edtbuscador;
    }

    @NonNull
    @Override
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_clase,null);
        return new ViewHolderCategoria(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCategoria holder, final int position) {

        Integer NumA = listaclase.size()/3;  //1
        Integer ResA = listaclase.size()%3;  //2

        if (ResA>0)
        {NumA=NumA+1;}


        if (NumA == position+1 && NumA != 1)
        {
            for (int i = 0; i < ResA; i++)
            {
                holder.listaCategoriaServicio.add(new MoCServicio(listaservicio.get(position*3+i).getId(),listaservicio.get(position*3+i).getNombre(),listaservicio.get(position*3+i).getDescripcion(),listaservicio.get(position*3+i).getImagen(),listaservicio.get(position*3+i).getPrecio(),listaservicio.get(position*3+i).getIdCategoria()));
            }
        }
        else if (NumA == 1)
        {
            for (int i = 0; i < listaclase.size(); i++)
            {
                holder.listaCategoriaServicio.add(new MoCServicio(listaservicio.get(position*3+i).getId(),listaservicio.get(position*3+i).getNombre(),listaservicio.get(position*3+i).getDescripcion(),listaservicio.get(position*3+i).getImagen(),listaservicio.get(position*3+i).getPrecio(),listaservicio.get(position*3+i).getIdCategoria()));
            }
        }
        else
        {
            for (int i = 0; i < 3; i++)
            {
                holder.listaCategoriaServicio.add(new MoCServicio(listaservicio.get(position*3+i).getId(),listaservicio.get(position*3+i).getNombre(),listaservicio.get(position*3+i).getDescripcion(),listaservicio.get(position*3+i).getImagen(),listaservicio.get(position*3+i).getPrecio(),listaservicio.get(position*3+i).getIdCategoria()));
            }
        }
        holder.adaptadorCategoria = new AdaptadorCServicio(holder.listaCategoriaServicio,context,"clase");
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
    }


    @Override
    public int getItemCount() {

        Integer Num = listaclase.size()/3;
        Integer Res = listaclase.size()%3;
        if (Res>0) Num=Num+1;

        return  Num;
    }

    public class ViewHolderCategoria extends RecyclerView.ViewHolder {
        ArrayList<MoCServicio> listaCategoriaServicio;
        RecyclerView recicler;
        AdaptadorCServicio adaptadorCategoria;
        public ViewHolderCategoria(@NonNull View itemView) {
            super(itemView);

            listaCategoriaServicio = new ArrayList<>();
            recicler = itemView.findViewById(R.id.recicleClaseServicio);
            recicler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));


        }
    }
}
