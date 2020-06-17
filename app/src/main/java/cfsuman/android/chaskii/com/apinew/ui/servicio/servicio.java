package cfsuman.android.chaskii.com.apinew.ui.servicio;

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
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorServicio;
import cfsuman.android.chaskii.com.apinew.modelo.MServicio;

public class servicio extends Fragment {

    private ServicioViewModel mViewModel;
    // TODO: Array del modelo familia, lista recicler
    ArrayList<MServicio> listaServicio;
    RecyclerView recycler1,recycler2;

    public static servicio newInstance() {
        return new servicio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.servicio_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ServicioViewModel.class);
        listaServicio=new ArrayList<>();
        recycler1=(RecyclerView) getView().findViewById(R.id.recicleServicio1);
        recycler2=(RecyclerView) getView().findViewById(R.id.recicleServicio2);
        recycler1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        recycler2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        AdaptadorServicio adaptadorServicio= new AdaptadorServicio(listaServicio,getActivity(),getContext());
        recycler1.setAdapter(adaptadorServicio);
        recycler2.setAdapter(adaptadorServicio);
    }

}
