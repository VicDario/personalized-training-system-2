package backend.modelo;

public class Cliente {

    private int id;
    private String nombre;
    private String rut;
    private int semanaActual;

    public Cliente(int id, String nombre, String rut, int semanaActual) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.semanaActual = semanaActual;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public int getSemanaActual() { return semanaActual; }
    public void setSemanaActual(int semanaActual) { this.semanaActual = semanaActual; }

    public void incrementarSemana() { semanaActual++; }
}
