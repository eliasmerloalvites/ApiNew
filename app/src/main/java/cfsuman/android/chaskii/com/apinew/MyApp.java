package cfsuman.android.chaskii.com.apinew;

import android.app.Application;
import android.content.Context;


import com.facebook.appevents.AppEventsLogger;

public class MyApp extends Application {


    private String familiaId;
    private String familiaNombre;
    private String categoriaId;
    private String categoriaNombre;
    private String servicioId;
    private String cantCategoria;
    private String promocionId;
    private String CantCategoriaDetalle;
    private Integer modovista ;
  /*  private Integer itemPestaña ;

    public Integer getItemPestaña() {
        return itemPestaña;
    }

    public void setItemPestaña(Integer itemPestaña) {
        this.itemPestaña = itemPestaña;
    }*/

    public Integer getModovista() {
        return modovista;
    }

    public void setModovista(Integer modovista) {
        this.modovista = modovista;
    }

    public String getCantCategoriaDetalle() {
        return CantCategoriaDetalle;
    }

    public void setCantCategoriaDetalle(String cantCategoriaDetalle) {
        CantCategoriaDetalle = cantCategoriaDetalle;
    }

    public String getPromocionId() {
        return promocionId;
    }

    public void setPromocionId(String promocionId) {
        this.promocionId = promocionId;
    }

    public String getCantCategoria() {
        return cantCategoria;
    }

    public void setCantCategoria(String cantCategoria) {
        this.cantCategoria = cantCategoria;
    }

    public String getFamiliaId() {
        return familiaId;
    }

    public void setFamiliaId(String familiaId) {
        this.familiaId = familiaId;
    }

    public String getFamiliaNombre() {
        return familiaNombre;
    }

    public void setFamiliaNombre(String familiaNombre) {
        this.familiaNombre = familiaNombre;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public String getServicioId() {
        return servicioId;
    }

    public void setServicioId(String servicioId) {
        this.servicioId = servicioId;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppEventsLogger.activateApp(this);
    }
}
