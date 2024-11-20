package actividad;

import java.util.Date;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import pregunta.PreguntaVerdaderoFalso;
 
// Atributos
public class Quiz extends Actividad {
        
    private float calificacion_minima;
    private float calificacion_obtenida;
    private HashSet<PreguntaVerdaderoFalso> preguntas;

    // Constructor
    public Quiz(int ID_actividad, String descripcion, String objetivo, String nivel_dificultad, ArrayList<String> resena,
                String tipo_actividad, List<Actividad> actividades_sugeridas, boolean prerrequisitos,
                Date fecha_limite, float d, float e, HashSet<PreguntaVerdaderoFalso> preguntas, int duracion) {

        super(ID_actividad, descripcion, objetivo, nivel_dificultad, resena, tipo_actividad, actividades_sugeridas, prerrequisitos, fecha_limite, duracion); 
        this.calificacion_minima = d;
        this.calificacion_obtenida = e;
        this.preguntas = preguntas;
    }

    public float getCalificacion_minima() {
        return this.calificacion_minima;
    }

    public void setCalificacion_minima(float calificacion_minima) {
        this.calificacion_minima = calificacion_minima;
    }

    public float getCalificacion_obtenida() {
        return this.calificacion_obtenida;
    }

    public void setCalificacion_obtenida(float calificacion_obtenida) {
        this.calificacion_obtenida = calificacion_obtenida;
    }

    public HashSet<PreguntaVerdaderoFalso> getPreguntas() {
        return this.preguntas;
    }

    public void setPreguntas(HashSet<PreguntaVerdaderoFalso> preguntas) {
        this.preguntas = preguntas;
    }
}
