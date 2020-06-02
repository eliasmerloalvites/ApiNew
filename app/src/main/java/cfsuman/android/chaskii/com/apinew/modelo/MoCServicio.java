package cfsuman.android.chaskii.com.apinew.modelo;

public class MoCServicio {
    private String Id;
    private String Nombre;
    private String Descripcion;
    private String Imagen;
    private String Precio;

    public MoCServicio(){};

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

    public MoCServicio(String id, String nombre, String descripcion, String imagen, String precio) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
        Imagen = imagen;
        Precio = precio;
    }


}
