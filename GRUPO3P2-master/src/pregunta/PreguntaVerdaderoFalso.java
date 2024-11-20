package pregunta;

public class PreguntaVerdaderoFalso extends Pregunta {

    private boolean respuesta_correcta;
    private String explicacion;
    private boolean rta_usuario;

    // Constructor que usa el super para inicializar los atributos de la clase padre
    public PreguntaVerdaderoFalso(int ID_pregunta, String descripcion, int calificacion, 
                                    boolean respuesta_correcta, String explicacion, boolean rta_usuario) {
        super(ID_pregunta, descripcion, calificacion); // Llamada al constructor de la clase padre
        this.respuesta_correcta = respuesta_correcta;
        this.explicacion = explicacion;
        this.rta_usuario = rta_usuario;
    }

    public boolean isRespuesta_correcta() {
        return this.respuesta_correcta;
    }

    public boolean getRespuesta_correcta() {
        return this.respuesta_correcta;
    }

    public void setRespuesta_correcta(boolean respuesta_correcta) {
        this.respuesta_correcta = respuesta_correcta;
    }

    public String getExplicacion() {
        return this.explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    } 

    public boolean isRta_usuario() {
        return this.rta_usuario;
    }

    public boolean getRta_usuario() {
        return this.rta_usuario;
    }

    public void setRta_usuario(String rta_usuario) {

        
        if (rta_usuario.equalsIgnoreCase("verdadero")) {
            this.rta_usuario = true;
        } else if (rta_usuario.equalsIgnoreCase("falso")) {
            this.rta_usuario = false;
        } else {
            throw new IllegalArgumentException("La respuesta debe ser 'verdadero' o 'falso'");
        }
    }
}
