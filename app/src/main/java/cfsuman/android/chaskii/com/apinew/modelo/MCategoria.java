package cfsuman.android.chaskii.com.apinew.modelo;

public class MCategoria {
    private String Id;
    private String Nombre;
    private String Descripcion;
    private String CostoMin;
    private String CostoMax;

    public String getCostoMin() {
        return CostoMin;
    }

    public void setCostoMin(String costoMin) {
        CostoMin = costoMin;
    }

    public MCategoria(String id, String nombre, String descripcion, String costoMin, String costoMax) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
        CostoMin = costoMin;
        CostoMax = costoMax;
    }

    public String getCostoMax() {
        return CostoMax;
    }

    public void setCostoMax(String costoMax) {
        CostoMax = costoMax;
    }

    public MCategoria(){};

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }


}
