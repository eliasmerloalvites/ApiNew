package cfsuman.android.chaskii.com.apinew.modelo;

public class MoNovedades {
    private String Id;
    private String Nombre;
    private String Descripcion;
    private String Imagen;
    private String Precio;
    private String Formato;
    private String Tiempo;

    public String getFormato() {
        return Formato;
    }

    public void setFormato(String formato) {
        Formato = formato;
    }

    public String getTiempo() {
        return Tiempo;
    }

    public void setTiempo(String tiempo) {
        Tiempo = tiempo;
    }

    private String Proveedor;

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String proveedor) {
        Proveedor = proveedor;
    }

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

    public MoNovedades(String id, String nombre, String descripcion, String imagen, String precio, String formato, String tiempo, String proveedor) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
        Imagen = imagen;
        Precio = precio;
        Formato = formato;
        Tiempo = tiempo;
        Proveedor = proveedor;
    }

    public MoNovedades() {
    }
}
