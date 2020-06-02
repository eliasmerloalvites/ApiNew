package cfsuman.android.chaskii.com.apinew.modelo;

public class MoServicio {
    private String Id;
    private String Nombre;
    private String Descripcion;
    private String Imagen;
    private String Precio;

    public MoServicio(){};

    public String getPrecio() { return Precio; }

    public void setPrecio(String precio) { Precio = precio; }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }



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

    public MoServicio(String id, String nombre, String descripcion, String imagen, String precio) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
        Imagen = imagen;
        Precio = precio;
    }


}
