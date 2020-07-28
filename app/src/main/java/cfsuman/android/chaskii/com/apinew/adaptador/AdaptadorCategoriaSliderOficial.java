package cfsuman.android.chaskii.com.apinew.adaptador;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.modelo.MoCAdicionales;
import cfsuman.android.chaskii.com.apinew.modelo.MoPromocion;


public class AdaptadorCategoriaSliderOficial extends  RecyclerView.Adapter<AdaptadorCategoriaSliderOficial.ViewHolderfamiliaes> {
    private ArrayList<MoCAdicionales> listaadicional;

    private Context context;
    private MyApp varGlobal;
    private int mCurrentPage;
    private TextView[] mText;
    private LinearLayout mLinearLayoutH;
    private ViewPager mSliderViewPager;
    private SliderAdapterCategoria sliderAdapter;

    public AdaptadorCategoriaSliderOficial(ArrayList<MoCAdicionales> listaadicional, Context context) {
        this.listaadicional = listaadicional;
        this.context = context;
        varGlobal = (MyApp) context;
        varGlobal.setCantCategoria(String.valueOf(listaadicional.size()) );
    }

    @NonNull
    @Override
    public ViewHolderfamiliaes onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.relativo_layoutpromociones,null);

        return new ViewHolderfamiliaes(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderfamiliaes holder, final int position) {

        sliderAdapter = new SliderAdapterCategoria(context,listaadicional);
        mSliderViewPager.setAdapter(sliderAdapter);
        addTextLayoutIndicador(0);
        segundo();
        mSliderViewPager.addOnPageChangeListener(viewListener);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolderfamiliaes extends RecyclerView.ViewHolder {

        public ViewHolderfamiliaes(@NonNull View itemView) {
            super(itemView);
            mSliderViewPager = itemView.findViewById(R.id.idViewPager);
            mLinearLayoutH = itemView.findViewById(R.id.idLinearLayourH);
        }
    }

    public void addTextLayoutIndicador(int pos)
    {
        mText = new TextView[Integer.valueOf(varGlobal.getCantCategoria()) ];
        mLinearLayoutH.removeAllViews();
        for (int i = 0 ; i <mText.length; i++)
        {
            mText[i]= new TextView(context);
            mText[i].setText(Html.fromHtml("&#8226"));
            mText[i].setTextSize(35);
            mText[i].setTextColor(context.getResources().getColor(R.color.colortransparente));

            mLinearLayoutH.addView(mText[i]);
        }
        if (mText.length>0)
        {
            mText[pos].setTextColor(context.getResources().getColor(R.color.edtAmarillo));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addTextLayoutIndicador(position);

            mCurrentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private void segundo()
    {
        CountDownTimer countDownTimer1 = new CountDownTimer(Integer.valueOf(varGlobal.getCantCategoria())*3*1000, 3000) {
            public void onTick(long millisUntilFinished) {
                if (mCurrentPage == mText.length - 1 )
                {
                    mCurrentPage = 0;
                    mSliderViewPager.setCurrentItem(mCurrentPage);
                }
                else
                {
                    mSliderViewPager.setCurrentItem(mCurrentPage + 1);
                }

            }

            public void onFinish() {
                segundo();
            }
        }.start();
    }

}
