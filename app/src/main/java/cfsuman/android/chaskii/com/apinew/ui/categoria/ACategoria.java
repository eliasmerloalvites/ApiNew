package cfsuman.android.chaskii.com.apinew.ui.categoria;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import cfsuman.android.chaskii.com.apinew.MyApp;
import cfsuman.android.chaskii.com.apinew.R;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorCategoria;
import cfsuman.android.chaskii.com.apinew.adaptador.AdaptadorCategoriaLista;
import cfsuman.android.chaskii.com.apinew.modelo.MCategoria;
import cfsuman.android.chaskii.com.apinew.modelo.MoCServicio;
import cfsuman.android.chaskii.com.apinew.ui.categoria.ui.PlaceholderFragment;
import cfsuman.android.chaskii.com.apinew.ui.categoria.ui.SectionsPagerAdapter;
import cfsuman.android.chaskii.com.apinew.ui.home.Inicio;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ACategoria extends AppCompatActivity {

    MyApp varGlobal;
    EditText editText;
    ImageView icoModoLista;
    TabLayout tabs;
    ViewPager viewPager;
    TabLayout.Tab tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acategoria);

        varGlobal = (MyApp) getApplicationContext();
        editText = findViewById(R.id.edtBuscadorCat);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tab = tabs.getTabAt(Integer.valueOf(varGlobal.getFamiliaId())-1);
        if (tab != null) {
            tab.select();
        }

        icoModoLista = findViewById(R.id.modoListaCategoria);
        if (varGlobal.getModovista() == 0 ) {
            icoModoLista.setImageResource(R.drawable.ic_menu_black_24dp);
        }
        else
        {
            icoModoLista.setImageResource(R.drawable.ic_menu_cuadro);
        }

        icoModoLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (varGlobal.getModovista() == 0 ) {
                    icoModoLista.setImageResource(R.drawable.ic_menu_cuadro);
                    varGlobal.setModovista(1);
                    varGlobal.setFamiliaId(String.valueOf(viewPager.getCurrentItem() + 1) );
                    finish();
                    startActivity(getIntent());
                }
                else
                {
                    icoModoLista.setImageResource(R.drawable.ic_menu_black_24dp);
                    varGlobal.setFamiliaId(String.valueOf(viewPager.getCurrentItem() + 1) );
                    varGlobal.setModovista(0);
                    finish();
                    startActivity(getIntent());
                }
            }
        });

    }

}
