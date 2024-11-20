package persistencia;

import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

import actividad.Actividad;
import actividad.Quiz;
import learningpath.LearningPath;

import pregunta.PreguntaVerdaderoFalso;
import usuario.Estudiante;
import usuario.Profesor;
import usuario.Usuario;

public class Recommendation {

    private HashMap<Integer, Actividad> actividades = new HashMap<>();

    private HashMap<Integer, LearningPath> learningpaths = new HashMap<>();
    private HashMap<Integer, Usuario> usuarios = new HashMap<>();
    private static Recommendation instance = new Recommendation();

    public static Recommendation getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        Recommendation recommendation = new Recommendation();
        recommendation.cargarActividades();

        recommendation.cargarLearningPaths();
        recommendation.cargarUsuarios();

        recommendation.req1CrearLP();
        recommendation.req2EliminarLp();
        recommendation.req3CrearActividad();
        recommendation.req4EliminarActividad();

        recommendation.req5EvaluarActividad();
        recommendation.req6InscribirseALp();
        recommendation.req7RealizarActividad();
        recommendation.req8VerProgreso();
        recommendation.req9DuracionLp();
        recommendation.req10EscribirResena();

    }
    

    public void cargarUsuarios() {
        System.out.println("Cargando Usuarios...");
        PersistenciaUsuario persistencia = new PersistenciaUsuario(
                "C:\\Users\\JUAN\\Desktop\\GRUPO3P2\\src\\persistencia\\usuario.txt");
        this.usuarios = persistencia.CargarUsuarios(this.learningpaths, this.actividades);
        System.out.println("Usuarios cargados: " + this.usuarios.size());

        if (!this.usuarios.isEmpty()) {
            Usuario primerUsuario = this.usuarios.values().iterator().next();
            System.out.println("Primer usuario cargado: ID = " + primerUsuario.getId_usuario() + ", Tipo = "
                    + (primerUsuario instanceof Profesor ? "Profesor" : "Estudiante") + ", Login = "
                    + primerUsuario.getLogin());
        }
    }

    public void cargarLearningPaths() {
        System.out.println("Cargando Learning Paths...");

        PersistenciaLearningPath carga = new PersistenciaLearningPath(
                "C:\\Users\\JUAN\\Desktop\\GRUPO3P2\\src\\persistencia\\learningPath.txt");
        this.learningpaths = carga.cargarLearningPaths(this.actividades);

        System.out.println("Learning Paths cargados: " + this.learningpaths.size());

        if (!this.learningpaths.isEmpty()) {
            LearningPath primerLearningPath = this.learningpaths.values().iterator().next();
            System.out.println("Primer Learning Path cargado:\n" + primerLearningPath.print());
        }
    }

    public void cargarActividades() {
        System.out.println("Cargando Actividades...");
        PersistenciaActividades carga = new PersistenciaActividades(
                "C:\\Users\\JUAN\\Desktop\\GRUPO3P2\\src\\persistencia\\actividades.txt");
        this.actividades = carga.CargarActividades();
        System.out.println("Actividades cargadas: " + this.actividades.size());
        System.out.println("primera actividad: " + this.actividades.values().iterator().next().print());

    }

    public void req1CrearLP() {
        System.out.println("Creando Learning Path...");

        // Configuración de datos de ejemplo, similar a `req3CrearActividad`
        String titulo = "Learning Path de Ejemplo";
        String descripcion = "Este es un ejemplo de un Learning Path.";
        String objetivo = "Desarrollar habilidades básicas en un área específica.";
        String nivelDificultad = "Intermedio";
        String version = "v1.0";
        int id_LP = 1001; // ID de ejemplo
        Date fechaCreacion = new Date();

        // Crear una lista de actividades de ejemplo
        Set<Actividad> actividadesEnLP = new HashSet<>();
        if (!this.actividades.isEmpty()) {
            actividadesEnLP.add(this.actividades.values().iterator().next()); // Añade una actividad de ejemplo
                                                                              // existente
        }

        // Crear instancia de LearningPath con los datos de prueba
        LearningPath nuevoLP = new LearningPath(titulo, descripcion, actividadesEnLP, nivelDificultad, id_LP,
                fechaCreacion, version, objetivo);

        // Añadir el nuevo Learning Path al mapa de learningpaths
        this.learningpaths.put(id_LP, nuevoLP);

        // Mensajes de confirmación en la consola
        System.out.println(nuevoLP.print());
        System.out.println("Learning Path añadido.");
        System.out.println("Learning Paths existentes: " + this.learningpaths.size());
    }

    public void req2EliminarLp() {
        System.out.println("Eliminando Learning Path...");
        if (learningpaths.containsKey(1)) {
            learningpaths.remove(1);
            System.out.println("Learning Path eliminado.");
            System.out.println("Learning path actuales" + this.learningpaths.size());
        } else {
            System.out.println("Learning Path no encontrado.");
        }
    }

    public void req3CrearActividad() {
        System.out.println("Creando Actividad");

        ArrayList<String> resenas = new ArrayList<>();
        resenas.add("El quiz más difícil que he hecho");

        ArrayList<Actividad> actividadessugeridas = new ArrayList<>();
        actividadessugeridas.add(this.actividades.values().iterator().next());

        // Crear un Hashdirectamente con preguntas de tipo PreguntaVerdaderoFalso
        HashSet<PreguntaVerdaderoFalso> preguntasSet = new HashSet<>();
        preguntasSet.add(new PreguntaVerdaderoFalso(5, "¿El agua no tiene color?", 5, true,
                "El agua no tiene color, pero puede parecer azul por el reflejo del cielo.", false));

        // Crear la instancia de Quiz con parámetros consistentes y relacionados con el
        // tema
        Actividad nuevActividad = new Quiz(5302, "Conociendo el agua", "Explorar conocimientos básicos sobre el agua",
                "Intermedio", resenas, "Quiz",
                actividadessugeridas, false, new Date(), 3.0f, 3.0f, preguntasSet, 15);

        this.actividades.put(5302, nuevActividad);
        System.out.println(nuevActividad.print());
        System.out.println("Actividad añadida");
        System.out.println("Actividades existentes: " + this.actividades.size());
    }

    public void req4EliminarActividad() {
        System.out.println(" Eliminando Actividad");
        this.actividades.remove(5302);
        System.out.println("Actividad Eliminada");
        System.out.println("Actividades existentes:  " + this.actividades.size());

    }

    // Usar coma como punto decimal
    public void req5EvaluarActividad() {
        System.out.println("Evaluando Actividad...");
        Scanner scanner = new Scanner(System.in);

        // Obtener actividad por ID de ejemplo
        Actividad actividad = actividades.get(5809); // ID de actividad de ejemplo
        if (actividad != null) {
            System.out.println("Evaluando la actividad: " + actividad.getDescripcion());

            // Solicitar calificación
            System.out.print("Ingrese una calificación (por ejemplo, entre 1 y 5 usando coma decimal ej: 2,5): ");
            double calificacion = scanner.nextDouble();

            // Limpiar el buffer de entrada
            scanner.nextLine();

            // Solicitar un comentario opcional
            System.out.print("Ingrese un comentario para la actividad: ");
            String comentario = scanner.nextLine();

            // Agregar calificación y comentario a la actividad
            actividad.agregarCalificacion(calificacion);
            actividad.agregarResena(comentario);

            System.out.println("Actividad evaluada con éxito.");
            System.out.println("Calificación: " + calificacion + " | Comentario: " + comentario);
        } else {
            System.out.println("Actividad no encontrada.");
        }
    }

    public void req6InscribirseALp() {
        System.out.println("Inscribiéndose a Learning Path...");

        Estudiante estudiante = (Estudiante) usuarios.get(2);
        LearningPath lp = learningpaths.get(3); // ID de LP de ejemplo

        if (estudiante != null && lp != null) {
            estudiante.inscribirseLearningPath(lp); // Usa el método correcto
            System.out.println("Inscripción exitosa al Learning Path: " + lp.getTitulo());
        } else {
            System.out.println("Estudiante o Learning Path no encontrado.");
        }
    }

    public void req7RealizarActividad() {
        System.out.println("Realizando Actividad...");
        Estudiante estudiante = (Estudiante) usuarios.get(2); // ID de estudiante de ejemplo
        Actividad actividad = actividades.get(5809); // ID de actividad de ejemplo
        int idLearningPath = 2; // ID de Learning Path de ejemplo

        if (estudiante != null && actividad != null) {
            estudiante.realizarActividad(actividad, idLearningPath); // Pasa el ID del Learning Path
            System.out.println("Actividad realizada: " + actividad.getDescripcion());
        } else {
            System.out.println("Estudiante o Actividad no encontrada.");
        }
    }

    public void req8VerProgreso() {
        System.out.println("Viendo Progreso...");
        Estudiante estudiante = (Estudiante) usuarios.get(2); // ID de estudiante de ejemplo
        if (estudiante != null) {
            estudiante.verProgreso();
        } else {
            System.out.println("Estudiante no encontrado.");
        }
    }

    public void req9DuracionLp() {
        System.out.println("Calculando Duración del Learning Path...");
        LearningPath lp = learningpaths.get(2); // ID de LP de ejemplo
        if (lp != null) {
            System.out.println("Duración total del LP: " + lp.getDuracion() + " minutos.");
        } else {
            System.out.println("Learning Path no encontrado.");
        }
    }

    public void req10EscribirResena() {
        System.out.println("Escribiendo Reseña...");
        Actividad actividad = actividades.get(5809); // ID de actividad de ejemplo
        if (actividad != null) {
            actividad.agregarResena("Muy interesante y educativo."); // Usa el nombre correcto del método
            System.out.println("Reseña añadida a la actividad: " + actividad.getDescripcion());
        } else {
            System.out.println("Actividad no encontrada.");
        }
    }

    /*
     * public void req3CrearActividad2() {
     * System.out.println("Creando Actividad");
     * ArrayList<Actividad> actividadessugeridas = new ArrayList<>();
     * actividadessugeridas.add(this.actividades.values().iterator().next());
     * Actividad nuevActividad = null;
     * for (Usuario usuario : this.usuarios.values()) {
     * if (usuario instanceof Profesor) {
     * nuevActividad = ((Profesor) usuario).crearActividad(5302, "aprende a contar",
     * "contando", " Facil",
     * actividadessugeridas, false, new Date(), 15, "quiz",
     * new BufferedReader(new InputStreamReader(System.in)));
     * break;
     * }
     * }
     * 
     * this.actividades.put(5302, nuevActividad);
     * System.out.println(nuevActividad.print());
     * System.out.println("Actividad Anadida");
     * System.out.println("Actividades existentes:  " + this.actividades.size());
     * 
     * }
     */

    public static LearningPath obtenerLearningPath(int id) {
        return instance.learningpaths.get(id);
    }

    public static Estudiante getEstudiante(int id) {
        if (instance.usuarios.get(id) instanceof Estudiante) {
            return (Estudiante) instance.usuarios.get(id);
        }
        return null;

    }

    public HashMap<Integer, Usuario> getUsuarios() {
        return this.usuarios;
    }

    
}