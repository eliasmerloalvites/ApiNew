package cfsuman.android.chaskii.com.apinew.ui.categoria;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorCategoria;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorFamilia;
import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MFamilia;

public class categoria extends Fragment {

    private CategoriaViewModel mViewModel;
    // TODO: Array del modelo familia, lista recicler
    ArrayList<MCategoria> listaCategoria;
    RecyclerView recycler1,recycler2;

    public static categoria newInstance() {
        return new categoria();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.categoria_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CategoriaViewModel.class);
        listaCategoria=new ArrayList<>();
        recycler1=(RecyclerView) getView().findViewById(R.id.recicleCategoria1);
        recycler2=(RecyclerView) getView().findViewById(R.id.recicleCategoria2);
        recycler1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        recycler2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
       // AdaptadorCategoria adaptadorCategoria= new AdaptadorCategoria(listaCategoria,getActivity(),getContext());
      /*  recycler1.setAdapter(adaptadorCategoria);
        recycler2.setAdapter(adaptadorCategoria);*/
    }

}
