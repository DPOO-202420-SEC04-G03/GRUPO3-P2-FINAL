package pregunta;

import java.util.List;

public class PreguntaOpcionMultiple extends Pregunta {
    
    private List<String> opciones;
    private String opcion_correcta; 
    private String explicacion;


    public PreguntaOpcionMultiple(int ID_pregunta, String descripcion, int calificacion, List<String> opciones,
                                    String opcion_correcta, String explicacion) {

        super(ID_pregunta, descripcion, calificacion);
        this.opciones = opciones;
        this.opcion_correcta = opcion_correcta;
        this.explicacion = explicacion;
    }


    public List<String> getOpciones() {
        return this.opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public String getOpcion_correcta() {
        return this.opcion_correcta;
    }

    public void setOpcion_correcta(String opcion_correcta) {
        this.opcion_correcta = opcion_correcta;
    }

    public String getExplicacion() {
        return this.explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }

    
}
