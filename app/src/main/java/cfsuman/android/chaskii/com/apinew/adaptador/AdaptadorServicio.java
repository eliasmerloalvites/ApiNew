package cfsuman.android.chaskii.com.apinew.adaptador;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MClase;
import cfsuman.android.chaskii.com.apinew.modelo.MServicio;
import cfsuman.android.chaskii.com.apinew.ui.clase.clase;


public class AdaptadorServicio extends RecyclerView.Adapter<AdaptadorServicio.ViewHolderfamiliaes> {

    ArrayList<MServicio> listaservicio;
    Activity activitys;
    Context context;
    String hab;

    public AdaptadorServicio(ArrayList<MServicio> listaservicio, Activity activity, Context context) {
        this.listaservicio = listaservicio;
        this.activitys = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderfamiliaes onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_servicio,null,false);

        return new ViewHolderfamiliaes(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderfamiliaes holder, int i) {
/*
        holder.tipo.setText(listaservicio.get(i).getTipofamilia());
        holder.descripcion.setText(listaservicio.get(i).getDescripcion());
        holder.precio.setText(listaservicio.get(i).getCosto());
        holder.num.setText(listaservicio.get(i).getNumfamilia());
        holder.idhab.setText(listaservicio.get(i).getIdfamilia());
        holder.precioxnoche.setText(precioxnoche.toString());
    */
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolderfamiliaes extends RecyclerView.ViewHolder {
/*        TextView tipo,descripcion, precio, precioxnoche,num,idhab;
        ImageView imagen;

-
        String a1,a2,a3;

        LinearLayout contenidoEstado;*/
        LinearLayout LLServicio;

        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);

 /*           tipo=(TextView) itemView.findViewById(R.id.txtTipo);
            descripcion=(TextView) itemView.findViewById(R.id.txtDesc);
            precio=(TextView) itemView.findViewById(R.id.txtPrecio);
            precioxnoche=(TextView) itemView.findViewById(R.id.txtPreNoche);
            num=(TextView) itemView.findViewById(R.id.txtnum);
            idhab=(TextView) itemView.findViewById(R.id.txtidfamilia);
            imagen =(ImageView) itemView.findViewById(R.id.image_familia);
            contenidoEstado = itemView.findViewById(R.id.estadofamilia);*/
            LLServicio =(LinearLayout) itemView.findViewById(R.id.linlayServicio);

            LLServicio.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Fragment fragment;
                    FragmentTransaction ft;
                    switch (view.getId()){

                        case R.id.linlayServicio :  fragment = new clase();
                            ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.nav_host_fragment, fragment).addToBackStack(null);
                            ft.commit();
                            break;/*
                        case R.id.idComputadora :fragment = Productos.newInstance("COMPUTADORA","");
                            ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.nav_host_fragment, fragment).addToBackStack(null);
                            ft.commit();
                            break;
                        case R.id.idTable : fragment = Productos.newInstance("TABLET","");
                            ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.nav_host_fragment, fragment).addToBackStack(null);
                            ft.commit();
                            break;*/
                    }

                }
            });



        }
    }
}
