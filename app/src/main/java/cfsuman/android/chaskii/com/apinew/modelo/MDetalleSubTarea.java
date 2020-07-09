package cfsuman.android.chaskii.com.apinew.modelo;

public class MDetalleSubTarea {
    private String SUT_Id;
    private String SER_Id;
    private String DSS_Costo;
    private String DSS_Tiempo;
    private String DSS_Formato;
    private String SUT_Nombre;

    public MDetalleSubTarea(){};

    public String getSUT_Id() {
        return SUT_Id;
    }

    public void setSUT_Id(String SUT_Id) {
        this.SUT_Id = SUT_Id;
    }

    public String getSER_Id() {
        return SER_Id;
    }

    public void setSER_Id(String SER_Id) {
        this.SER_Id = SER_Id;
    }

    public String getDSS_Costo() {
        return DSS_Costo;
    }

    public void setDSS_Costo(String DSS_Costo) {
        this.DSS_Costo = DSS_Costo;
    }

    public String getDSS_Tiempo() {
        return DSS_Tiempo;
    }

    public void setDSS_Tiempo(String DSS_Tiempo) {
        this.DSS_Tiempo = DSS_Tiempo;
    }

    public String getDSS_Formato() {
        return DSS_Formato;
    }

    public void setDSS_Formato(String DSS_Formato) {
        this.DSS_Formato = DSS_Formato;
    }

    public String getSUT_Nombre() {
        return SUT_Nombre;
    }

    public void setSUT_Nombre(String SUT_Nombre) {
        this.SUT_Nombre = SUT_Nombre;
    }

    public MDetalleSubTarea(String SUT_Id, String SER_Id, String DSS_Costo, String DSS_Tiempo, String DSS_Formato, String SUT_Nombre) {
        this.SUT_Id = SUT_Id;
        this.SER_Id = SER_Id;
        this.DSS_Costo = DSS_Costo;
        this.DSS_Tiempo = DSS_Tiempo;
        this.DSS_Formato = DSS_Formato;
        this.SUT_Nombre = SUT_Nombre;
    }
}
