package cfsuman.android.chaskii.com.apinew.adaptador;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MoCAdicionales;
import cfsuman.android.chaskii.com.apinew.modelo.MoPromocion;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class AdaptadorCategoriaSlider extends  RecyclerView.Adapter<AdaptadorCategoriaSlider.ViewHolderfamiliaes> {
    private ArrayList<MoCAdicionales> listaadicionales;

    private Context context;
    private MyApp varGlobal;
    public AdaptadorCategoriaSlider(ArrayList<MoCAdicionales> listaadicionales, Context context) {
        this.listaadicionales = listaadicionales;
        this.context = context;
        varGlobal = (MyApp) context;
    }

    @NonNull
    @Override
    public ViewHolderfamiliaes onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_adicionales,null);

        return new ViewHolderfamiliaes(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderfamiliaes holder, final int i) {
        final int radius = 10;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius,margin, RoundedCornersTransformation.CornerType.TOP);

         Picasso.get()
                .load(Uri.parse("http://subdominio.maprocorp.com/images/servicio/"+listaadicionales.get(i).getImagen()))
                .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                .into(holder.imagen);

    }

    @Override
    public int getItemCount() {
        return listaadicionales.size();
    }

    public class ViewHolderfamiliaes extends RecyclerView.ViewHolder {
        ImageView imagen;
        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.ImagenAdicionales);
        }
    }



}
