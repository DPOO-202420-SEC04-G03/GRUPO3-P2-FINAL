package pregunta;

public class PreguntaAbierta extends Pregunta{

    private String estado_entrega;
    private String respuesta_estudiante;



    public PreguntaAbierta(int ID_pregunta, String descripcion, int calificacion, String estado_entrega,
                    String respuesta_estudiante) {

        super(ID_pregunta, descripcion, calificacion);
        this.estado_entrega = estado_entrega;
        this.respuesta_estudiante = respuesta_estudiante;
    }
    


    public String getEstado_entrega() {
        return this.estado_entrega;
    }

    public void setEstado_entrega(String estado_entrega) {
        this.estado_entrega = estado_entrega;
    }

    public String getRespuesta_estudiante() {
        return this.respuesta_estudiante;
    }

    public void setRespuesta_estudiante(String respuesta_estudiante) {
        this.respuesta_estudiante = respuesta_estudiante;
    }

}
