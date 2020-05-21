package cfsuman.android.chaskii.com.apinew.ui.clase;

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
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorClase;
import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MClase;

public class clase extends Fragment {

    private ClaseViewModel mViewModel;
    // TODO: Array del modelo familia, lista recicler
    ArrayList<MClase> listaClase;
    RecyclerView recycler1,recycler2;

    public static clase newInstance() {
        return new clase();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.clase_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ClaseViewModel.class);
        listaClase=new ArrayList<>();
        recycler1=(RecyclerView) getView().findViewById(R.id.recicleClase1);
        recycler2=(RecyclerView) getView().findViewById(R.id.recicleClase2);
        recycler1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        recycler2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        AdaptadorClase adaptadorClase= new AdaptadorClase(listaClase,getActivity(),getContext());
        recycler1.setAdapter(adaptadorClase);
        recycler2.setAdapter(adaptadorClase);
    }

}
