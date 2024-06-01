package gradle.javadoc;

public class Asignatura {
    private String ID_Asignatura;
    private String Nombre;
    private String Codigo;
    private String Creditos;

    public Asignatura(String ID_Asignatura, String Nombre, String Codigo, String Creditos) {
        this.ID_Asignatura = ID_Asignatura;
        this.Nombre = Nombre;
        this.Codigo = Codigo;
        this.Creditos = Creditos;
    }
    public String [] devolverAsignatura() {
        return new String[]{ID_Asignatura, Nombre, Codigo, Creditos};
    }

    public String getID_Asignatura() {
        return ID_Asignatura;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getCodigo() {
        return Codigo;
    }

    public String getCreditos() {
        return Creditos;
    }
    
}