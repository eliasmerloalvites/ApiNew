package cfsuman.android.chaskii.com.apinew.adaptador;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.Promocion;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MDetalleImagen;
import cfsuman.android.chaskii.com.apinew.modelo.MoPromocion;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class SliderAdapterPromocionDetalle extends PagerAdapter {

    Context context;
    private MyApp varGlobal;
    LayoutInflater layoutInflater;
    private ArrayList<MDetalleImagen> listaimagenes;

    public SliderAdapterPromocionDetalle(Context context, ArrayList<MDetalleImagen> listaimagenes)
    {
        this.context = context;
        this.listaimagenes = listaimagenes;
        varGlobal = (MyApp) context;
    }

    @Override
    public int getCount() {
        return listaimagenes.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layoutpromociones_flotante,container,false);
        final int radius = 25;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius,margin);

        ImageView sliderImagen = view.findViewById(R.id.idImagen);

        Picasso.get()
                .load(Uri.parse("http://subdominio.maprocorp.com/images/promocion/"+listaimagenes.get(position).getDIS_Nombre()))
                .transform(transformation)
                .error(R.drawable.apple_logo) //en caso que la url no sea válida muestro otra imagen
                .into(sliderImagen);
        sliderImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                varGlobal.setPromocionId(listaimagenes.get(position).getDIS_Id());
                Intent intent = new Intent(context, Promocion.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        container.addView(view);

        return  view;
    }
}
