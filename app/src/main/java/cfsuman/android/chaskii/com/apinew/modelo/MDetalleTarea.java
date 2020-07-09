package cfsuman.android.chaskii.com.apinew.modelo;

public class MDetalleTarea {
    private String TAR_Id;
    private String SER_Id;
    private String DTS_Costo;
    private String DTS_Tiempo;
    private String DTS_Formato;
    private String TAR_Nombre;

    public MDetalleTarea(){};

    public String getTAR_Id() {
        return TAR_Id;
    }

    public void setTAR_Id(String TAR_Id) {
        this.TAR_Id = TAR_Id;
    }

    public String getSER_Id() {
        return SER_Id;
    }

    public void setSER_Id(String SER_Id) {
        this.SER_Id = SER_Id;
    }

    public String getDTS_Costo() {
        return DTS_Costo;
    }

    public void setDTS_Costo(String DTS_Costo) {
        this.DTS_Costo = DTS_Costo;
    }

    public String getDTS_Tiempo() {
        return DTS_Tiempo;
    }

    public void setDTS_Tiempo(String DTS_Tiempo) {
        this.DTS_Tiempo = DTS_Tiempo;
    }

    public String getDTS_Formato() {
        return DTS_Formato;
    }

    public void setDTS_Formato(String DTS_Formato) {
        this.DTS_Formato = DTS_Formato;
    }

    public String getTAR_Nombre() {
        return TAR_Nombre;
    }

    public void setTAR_Nombre(String TAR_Nombre) {
        this.TAR_Nombre = TAR_Nombre;
    }

    public MDetalleTarea(String TAR_Id, String SER_Id, String DTS_Costo, String DTS_Tiempo, String DTS_Formato, String TAR_Nombre) {
        this.TAR_Id = TAR_Id;
        this.SER_Id = SER_Id;
        this.DTS_Costo = DTS_Costo;
        this.DTS_Tiempo = DTS_Tiempo;
        this.DTS_Formato = DTS_Formato;
        this.TAR_Nombre = TAR_Nombre;
    }
}
