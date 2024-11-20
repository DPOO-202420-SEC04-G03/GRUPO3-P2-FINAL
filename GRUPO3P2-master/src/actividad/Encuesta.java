package actividad;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pregunta.Pregunta;

public class Encuesta extends Actividad {

    private String estado_entrega;
    private List<Pregunta> preguntas;


    //Constructor

    public Encuesta(int ID_actividad, String descripcion, String objetivo, String nivel_dificultad , ArrayList<String> resena, String tipo_actividad,
                    List<Actividad> actividades_sugeridas, boolean prerrequisitos, Date fecha_limite, String estado_entrega,
                    List<Pregunta> preguntas, int duracion){

        super(ID_actividad, descripcion, objetivo, nivel_dificultad, resena, tipo_actividad, actividades_sugeridas, prerrequisitos, fecha_limite, duracion); 
        
        this.estado_entrega= estado_entrega;
        this.preguntas= preguntas;
        
        }

    

    // Getters y Setters
    public String getEstado_entrega() {
        return this.estado_entrega;
    }

    public void setEstado_entrega(String estado_entrega) {
        this.estado_entrega = estado_entrega;
    }

    public List<Pregunta> getPreguntas() {
        return this.preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

}
