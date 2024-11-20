package usuario;

import java.util.List;
import actividad.Actividad;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import learningpath.LearningPath;

public class Estudiante extends Usuario {
    
    private Set<LearningPath> learningPathInscritos; // Cambié a notación camelCase estándar
    private HashMap<Integer, Set<Actividad>> actividadesRealizadasPorLP;
    private List<Double> calificaciones;

    public Estudiante(int idUsuario, String login, String password, List<LearningPath> learningPathInscritos) {
        super(idUsuario, login, password);
        this.actividadesRealizadasPorLP = new HashMap<>();
        this.calificaciones = new ArrayList<>();
        // Convertir la lista a un conjunto para evitar duplicados
        this.learningPathInscritos = new HashSet<>(learningPathInscritos);
    }
    
    public Set<LearningPath> getLearningPathInscritos() {
        return this.learningPathInscritos;
    }

    public void setLearningPathInscritos(Set<LearningPath> learningPathInscritos) {
        // Asigna directamente el conjunto recibido, eliminando duplicados
        this.learningPathInscritos = learningPathInscritos;
    }

    public HashMap<Integer, Set<Actividad>> getActividadesRealizadas() {
        return this.actividadesRealizadasPorLP;
    }

    public void setActividadesRealizadas(HashMap<Integer, Set<Actividad>> actividadesRealizadas) {
        this.actividadesRealizadasPorLP = actividadesRealizadas;
    }

    public List<Double> getCalificaciones() {
        return this.calificaciones;
    }

    public void setCalificaciones(List<Double> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public void agregarCalificacion(double calificacion) {
        if (calificaciones == null) {
            calificaciones = new ArrayList<>();
        }
        calificaciones.add(calificacion);
    }

    public void inscribirseLearningPath(LearningPath lp) {
        // Agrega solo si el Learning Path no está ya inscrito
        this.learningPathInscritos.add(lp);
    }

    public String mostrarProgreso(int idLearningPath) {
        for (LearningPath lp : this.learningPathInscritos) {
            if (lp.getId_LP() == idLearningPath) {
                // Obtener el total de actividades en el Learning Path
                int total = lp.getActividades().size();
                // Obtener las actividades completadas usando getOrDefault
                int completadas = actividadesRealizadasPorLP.getOrDefault(idLearningPath, new HashSet<>()).size();

                return "Learning Path: " + lp.getTitulo() + "\n" +
                       "Progreso: " + completadas + "/" + total +
                       " actividades completadas (" + String.format("%.2f", 
                       total > 0 ? (completadas * 100.0) / total : 0) + "%)";
            }
        }
        return "No está inscrito en el LP: " + idLearningPath;
    }

    public void escribirResena(Actividad actividad, String resena) {
        actividad.agregarResena(resena);
    }

    public void realizarActividad(Actividad actividad, int idLearningPath) {
        Set<Actividad> actividadesRealizadas = actividadesRealizadasPorLP.getOrDefault(idLearningPath, new HashSet<>());
        actividadesRealizadas.add(actividad);
        actividadesRealizadasPorLP.put(idLearningPath, actividadesRealizadas);

        // Depuración
        System.out.println("Actividades realizadas para LP " + idLearningPath + ": " + actividadesRealizadas);
    }

    public String verProgreso() {
        StringBuilder progreso = new StringBuilder();
        for (LearningPath lp : learningPathInscritos) {
            int totalActividades = lp.getActividades().size();
            int actividadesCompletadas = actividadesRealizadasPorLP.getOrDefault(lp.getId_LP(), new HashSet<>()).size();
            double porcentajeCompletado = totalActividades > 0 ? (actividadesCompletadas * 100.0) / totalActividades : 0;

            progreso.append("Learning Path: ").append(lp.getTitulo()).append("\n") // Título del Learning Path
                    .append("Progreso: ").append(actividadesCompletadas).append("/").append(totalActividades)
                    .append(" actividades completadas (").append(String.format("%.2f", porcentajeCompletado)).append("%)").append("\n");
        }
        return progreso.toString().trim();
    }
}
