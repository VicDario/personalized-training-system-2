package backend.modelo;

// Clase base abstracta para todos los ejercicios del sistema
public abstract class Ejercicio {

    // Atributos del ejercicio
    private String codigo;
    private String nombre;
    private TipoEjercicio tipo;
    private NivelIntensidad nivel;
    private int tiempoEstimado;
    private String descripcion;
    private int semanaUltimoUso;

    // Inicializa todos los atributos con valores por defecto
    public Ejercicio() {
        codigo = "";
        nombre = "";
        tipo = null;
        nivel = null;
        tiempoEstimado = 0;
        descripcion = "";
    }

    // Getters y setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public TipoEjercicio getTipo() { return tipo; }
    public void setTipo(TipoEjercicio tipo) { this.tipo = tipo; }

    public NivelIntensidad getNivel() { return nivel; }
    public void setNivel(NivelIntensidad nivel) { this.nivel = nivel; }

    public int getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(int tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
