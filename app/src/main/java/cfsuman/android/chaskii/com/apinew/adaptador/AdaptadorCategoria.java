package cfsuman.android.chaskii.com.apinew.adaptador;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;


public class AdaptadorCategoria extends RecyclerView.Adapter<AdaptadorCategoria.ViewHolderCategoria> {

    ArrayList<MCategoria> listacategoria;
    ArrayList<MoCServicio> listaservicio;

    public AdaptadorCategoria(ArrayList<MCategoria> listacategoria,ArrayList<MoCServicio> listaservicio) {
        this.listacategoria = listacategoria;
        this.listaservicio = listaservicio;

    }

    @NonNull
    @Override
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategoria holder, int position) {

    }


    @Override
    public int getItemCount() {
        return listacategoria.size();
    }

    public class ViewHolderCategoria extends RecyclerView.ViewHolder {
        public ViewHolderCategoria(@NonNull View itemView) {
            super(itemView);
        }
    }
}
