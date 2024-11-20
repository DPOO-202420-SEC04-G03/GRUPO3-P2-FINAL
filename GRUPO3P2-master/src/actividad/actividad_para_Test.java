package actividad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class actividad_para_Test extends Actividad {
    public actividad_para_Test(int ID_actividad, String descripcion, String objetivo, String nivel_dificultad, 
                               ArrayList<String> resenas, String tipo_actividad, List<Actividad> actividades_sugeridas, 
                               boolean prerrequisitos, Date fecha_limite, int duracion) {
        super(ID_actividad, descripcion, objetivo, nivel_dificultad, resenas, tipo_actividad, actividades_sugeridas, 
              prerrequisitos, fecha_limite, duracion);
    }

    public ArrayList<String> getResenas() {
        return super.getResena(); // Accede a las reseñas de la clase madre
    }
}


