package usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import learningpath.LearningPath;
import persistencia.Recommendation;
import actividad.Actividad;
import actividad.Examen;

public class Profesor extends Usuario {

    private Set<LearningPath> learningPathsCreados; // Usamos Set para evitar duplicados

    // Constructor
    public Profesor(int id_usuario, String login, String password) {
        super(id_usuario, login, password);
        this.learningPathsCreados = new HashSet<>();
    }

    // Getters y Setters
    public Set<LearningPath> getLearningPathsCreados() {
        return this.learningPathsCreados;
    }

    public void setLearningPathsCreados(Set<LearningPath> learningPathsCreados) {
        this.learningPathsCreados = learningPathsCreados;
    }

    // Crear Learning Path
    public LearningPath crearLearningPath(String titulo, String descripcion, String objetivo, String nivel_dificultad,
            int id_LP, Date fecha_creacion, String version) {
        LearningPath lpnuevo = new LearningPath(titulo, descripcion, new HashSet<>(), nivel_dificultad, id_LP,
                fecha_creacion, version, objetivo);
        this.learningPathsCreados.add(lpnuevo);
        return lpnuevo;
    }

    public LearningPath buscarLearningPath(int id_LP) {

        for (LearningPath lp : this.learningPathsCreados) {
            if (lp.getId_LP() == id_LP) { // Busca por ID
                return lp;
            }
        }
        return null;
    }

    public void editarLearningPath(int id_LP, String nuevoTitulo, String nuevaDescripcion, String nuevoObjetivo,
            String nuevoNivelDificultad, String nuevaVersion) {

        LearningPath learningPath = buscarLearningPath(id_LP);
        if (learningPath != null) {
            learningPath.setTitulo(nuevoTitulo);
            learningPath.setDescripcion(nuevaDescripcion);
            learningPath.setObjetivo(nuevoObjetivo);
            learningPath.setNivel_dificultad(nuevoNivelDificultad);
            learningPath.setVersion(nuevaVersion);
            learningPath.setFecha_modificacion(new Date()); // Actualiza la fecha de modificación
        }
    }

    // Eliminar Learning Path por ID
    public void eliminarLearningPath(int id_LP) {
        LearningPath learningPath = buscarLearningPath(id_LP);
        if (learningPath != null) {
            this.learningPathsCreados.remove(learningPath);
        }
    }
    


    // Agregar una actividad a un Learning Path
    public void agregarActividadALearningPath(int id_LP, Actividad nuevaActividad) {
        LearningPath learningPath = buscarLearningPath(id_LP);
        if (learningPath != null) {
            learningPath.agregarActividad(nuevaActividad);
        }
    }

    // Editar una actividad en un Learning Path
    public void editarActividadEnLearningPath(int id_LP, Actividad antiguaActividad, Actividad nuevaActividad) {
        LearningPath learningPath = buscarLearningPath(id_LP);
        if (learningPath != null) {
            learningPath.editarActividad(antiguaActividad, nuevaActividad);
        }
    }

    // Eliminar una actividad en un Learning Path
    public void eliminarActividadEnLearningPath(int id_LP, Actividad actividad) {
        LearningPath learningPath = buscarLearningPath(id_LP);
        if (learningPath != null) {
            learningPath.eliminarActividad(actividad);
        }
    }

    public actividad.Actividad crearActividad(int ID_actividad, String descripcion, String objetivo,
            String nivel_dificultad, List<actividad.Actividad> actividades_sugeridas, boolean prerequisitos, Date fecha_limite,
            int duracion, String tipo_actividad, BufferedReader lector) {

        actividad.Actividad actividad;

        // Encuesta
        if (tipo_actividad.equals("encuesta")) {
            System.out.println("Digite el estado de la encuesta: ");
            String estado = "";
            try {
                estado = lector.readLine();
            } catch (IOException e) {
                System.out.println("Error leyendo el estado.");
            }
            return new actividad.Encuesta(ID_actividad, descripcion, objetivo, nivel_dificultad, new ArrayList<>(),
                    tipo_actividad, actividades_sugeridas, prerequisitos, fecha_limite, estado, new ArrayList<>(),
                    duracion);

            // Quiz
        } else if (tipo_actividad.equals("quiz")) {
            System.out.println("Digite la calificación mínima: ");
            float calificacion_minima = 0;
            try {
                calificacion_minima = Float.parseFloat(lector.readLine());
            } catch (IOException e) {
                System.out.println("Error leyendo las calificaciones.");
            }
            return new actividad.Quiz(ID_actividad, descripcion, objetivo, nivel_dificultad, new ArrayList<>(),
                    tipo_actividad, actividades_sugeridas, prerequisitos, fecha_limite, calificacion_minima, 0,
                    new HashSet<>(), duracion);


            // Examen
        } else if (tipo_actividad.equals("examen")) {
            System.out.println("Digite el estado de entrega del examen: ");
            String estado_entrega = "";
            String fecha_entrega_texto = "";
            Date fecha_entrega = new Date();
            try {
                estado_entrega = lector.readLine();
                System.out.println("Digite la fecha de entrega (aaaa-mm-dd): ");
                fecha_entrega_texto = lector.readLine();
                fecha_entrega = new Date(); // Valor placeholder
            } catch (IOException e) {
                System.out.println("Error leyendo el estado o la fecha.");
            }
            return new actividad.Examen(ID_actividad, descripcion, objetivo, nivel_dificultad, new ArrayList<>(),
                    tipo_actividad, actividades_sugeridas, prerequisitos, fecha_limite, estado_entrega, fecha_entrega,
                    new ArrayList<>(), duracion);

            // Recurso Educativo
        } else if (tipo_actividad.equals("recurso")) {
            
                System.out.println("Digite el tipo  de recurso: ");
                String tiporecurso = "";
                String url = "";
                try {
                    tiporecurso = lector.readLine();
                    System.out.println("Digite la url: ");
                    url = lector.readLine();
                } catch (IOException e) {
                    System.out.println("Error leyendo el estado o la fecha.");
                }
            return new actividad.RecursoEducativo(ID_actividad, descripcion, objetivo, nivel_dificultad,
                    new ArrayList<>(),
                    tipo_actividad, actividades_sugeridas, prerequisitos, fecha_limite, tiporecurso,url , duracion);

            // Tarea
        } else if (tipo_actividad.equals("tarea")) {
            System.out.println("Digite el estado de entrega de la tarea: ");
            String estado_entrega = "";
            String fecha_entrega_texto = "";
            String medio_entrega = "";
            Date fecha_entrega = new Date();
            try {
                estado_entrega = lector.readLine();
                System.out.println("Digite la fecha de entrega (aaaa-mm-dd): ");
                fecha_entrega_texto = lector.readLine();
                System.out.println("Digite el medio de entrega: ");
                medio_entrega = lector.readLine();
            } catch (IOException e) {
                System.out.println("Error leyendo los datos.");
            }
            return new actividad.Tarea(ID_actividad, descripcion, objetivo, nivel_dificultad, new ArrayList<>(),
                    tipo_actividad, actividades_sugeridas, prerequisitos, fecha_limite, estado_entrega, fecha_entrega,
                    medio_entrega, duracion);
        }

        return new actividad.Encuesta(ID_actividad, descripcion, objetivo, nivel_dificultad, new ArrayList<>(),
                tipo_actividad, actividades_sugeridas, prerequisitos, fecha_limite, "No enviado", new ArrayList<>(),
                duracion);
    }

    public void evaluarEstudiante(int id_estudiante, int id_learningpath, BufferedReader lector) {

        boolean centinela_profesor = false;
        boolean centinela_estudiante = false;

        for (LearningPath lp : this.learningPathsCreados) {

            if (id_learningpath == lp.getId_LP()) {

                centinela_profesor = true;

                break;
            }
        }
        if (!centinela_profesor) {

            System.out.println("No posee ese Learning Path, no puede calificar");
            return;
        }

        Estudiante estu = Recommendation.getEstudiante(id_estudiante);

        for (LearningPath lp : estu.getLearningPathInscritos()) {

            if (id_learningpath == lp.getId_LP()) {

                centinela_estudiante = true;

                break;
            }
        }
        if (!centinela_estudiante) {
            System.out.println("El estudiante no está en ese LP, no se puede calificar");
            return;
        }

        for (Actividad actividad : estu.getActividadesRealizadas().get(id_learningpath)) {

            if (actividad instanceof Examen) {
                Examen examen = (Examen) actividad; // Pasar la actividad a examen

                if (examen.getEstado_entrega().equals("Enviado")) {

                    System.out.println("Digite la calificación para el examen " + examen.getID_actividad() + ": ");
                    try {
                        String calificacion = lector.readLine();
                        double cali = Double.parseDouble(calificacion);
                        estu.agregarCalificacion(cali);
                        examen.setEstado_entrega("Revisado");
                        System.out.println("Calificación guaradada");
                    } catch (IOException e) {
                        
                        e.printStackTrace();
                    }
                    
                }
            }
        }
    }

    public void escribirResena(Actividad actividad, String resena) {

        actividad.agregarResena(resena);

    }
}
