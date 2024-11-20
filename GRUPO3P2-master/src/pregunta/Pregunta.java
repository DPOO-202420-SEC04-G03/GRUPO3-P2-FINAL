package pregunta;

//atributos

public abstract class Pregunta {

    private int ID_pregunta;
    private String descripcion;
    private int calificacion;

    //constructor
    

    public Pregunta(int ID_pregunta, String descripcion, int calificacion) {
        this.ID_pregunta = ID_pregunta;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
    }
    

    //setters y getters


    public int getID_pregunta() {
        return this.ID_pregunta;
    }

    public void setID_pregunta(int ID_pregunta) {
        this.ID_pregunta = ID_pregunta;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

}
