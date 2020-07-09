package cfsuman.android.chaskii.com.apinew.adaptador;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MClase;
import cfsuman.android.chaskii.com.apinew.modelo.MDetalleSubTarea;
import cfsuman.android.chaskii.com.apinew.modelo.MDetalleTarea;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;


public class AdaptadorDetallesTarea extends RecyclerView.Adapter<AdaptadorDetallesTarea.ViewHolderCategoria> {

    private ArrayList<MDetalleTarea> mDetalleTareas;
    private ArrayList<MDetalleSubTarea> mDetalleSubTareas;
    private Context context;
    private EditText edtbuscador;
    private TextView txtTotal;
    private Double total=0.00 ;

    public AdaptadorDetallesTarea(ArrayList<MDetalleTarea> mDetalleTareas, ArrayList<MDetalleSubTarea> mDetalleSubTareas, Context context,TextView text) {
        this.mDetalleTareas = mDetalleTareas;
        this.mDetalleSubTareas = mDetalleSubTareas;
        this.context = context;
        this.txtTotal = text;
    }

    @NonNull
    @Override
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detallelista,null);
        return new ViewHolderCategoria(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderCategoria holder, final int position) {

        Double precio = Double.parseDouble(mDetalleTareas.get(position).getDTS_Costo());
        total = total + precio;
        holder.precio.setText("S/ "+ mDetalleTareas.get(position).getDTS_Costo());
        txtTotal.setText("S/ "+ String.format("%.2f", total));
        holder.chkItem.setText(mDetalleTareas.get(position).getTAR_Nombre());
        holder.chkItem.setChecked(true);
        holder.chkItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    Double precioU = Double.parseDouble(mDetalleTareas.get(position).getDTS_Costo());
                    total = total + precioU;
                    txtTotal.setText("S/ "+  String.format("%.2f", total));
                } else {
                    Double precioU = Double.parseDouble(mDetalleTareas.get(position).getDTS_Costo());
                    total = total - precioU;
                    txtTotal.setText("S/ "+  String.format("%.2f", total));
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return  mDetalleTareas.size();
    }

    public class ViewHolderCategoria extends RecyclerView.ViewHolder {
        TextView precio,total;
        CheckBox chkItem;
        public ViewHolderCategoria(@NonNull View itemView) {
            super(itemView);

            chkItem = itemView.findViewById(R.id.chkItem);
            precio = itemView.findViewById(R.id.txtprecio);
            total = itemView.findViewById(R.id.txtTotal);


        }
    }
}
