package gradle.javadoc;

public class Alumno {
    private String ID_Alumno;
    private String Nombre;
    private String Apellido;
    private String Fecha_nacimiento;

    public Alumno(String ID_Alumno, String Nombre, String Apellido, String Fecha_nacimiento) {
        this.ID_Alumno = ID_Alumno;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Fecha_nacimiento = Fecha_nacimiento;
    }
    public String [] devolverAlumno() {
        return new String[]{ID_Alumno, Nombre, Apellido, Fecha_nacimiento};
    }

    public String getID_Alumno() {
        return ID_Alumno;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public String getFecha_nacimiento() {
        return Fecha_nacimiento;
    }
    
}
