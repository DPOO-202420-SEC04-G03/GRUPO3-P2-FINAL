package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import actividad.actividad_para_Test;
import actividad.Examen;
import actividad.Actividad;
import learningpath.LearningPath;
import persistencia.Recommendation;
import usuario.Estudiante;
import usuario.Profesor;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;

class ProfesorTest {

    private Profesor profesor; // Instancia del profesor para los tests
    private LearningPath lp1; // Learning Path de prueba
    private actividad_para_Test actividad1; // Primera actividad de prueba
    private actividad_para_Test actividad2; // Segunda actividad de prueba

    @BeforeEach
    void setUp() {
        // Inicializa el profesor con un ID y credenciales
        profesor = new Profesor(2001, "profesor1", "securePassword");

        // Crea un Learning Path inicial vacío
        lp1 = new LearningPath("Learning Path 1", "Descripción LP1", new HashSet<>(), "Avanzado", 1, new Date(), "1.0", "Objetivo LP1");

        // Define actividades de ejemplo
        actividad1 = new actividad_para_Test(
                101,
                "Actividad 1",
                "Objetivo 1",
                "Básico",
                new ArrayList<>(),
                "tarea",
                new ArrayList<>(),
                false,
                new Date(),
                30
        );

        actividad2 = new actividad_para_Test(
                102,
                "Actividad 2",
                "Objetivo 2",
                "Intermedio",
                new ArrayList<>(),
                "quiz",
                new ArrayList<>(),
                false,
                new Date(),
                60
        );

        // Añade el Learning Path al profesor
        profesor.crearLearningPath(lp1.getTitulo(), lp1.getDescripcion(), lp1.getObjetivo(), lp1.getNivel_dificultad(),
                lp1.getId_LP(), lp1.getFecha_creacion(), lp1.getVersion());
    }

    @Test
    void testCrearLearningPath() {
        // Crea un nuevo Learning Path
        LearningPath lp2 = profesor.crearLearningPath("LP2", "Descripción LP2", "Objetivo LP2", "Intermedio",
                2, new Date(), "1.1");

        // Verifica que ahora hay dos Learning Paths en el conjunto del profesor
        assertEquals(2, profesor.getLearningPathsCreados().size(), "Debería haber dos Learning Paths creados");
        assertTrue(profesor.getLearningPathsCreados().contains(lp2), "El nuevo Learning Path debería estar en la lista");
    }

    @Test
    void testBuscarLearningPath() {
        // Busca un Learning Path por ID
        LearningPath found = profesor.buscarLearningPath(1);
        assertNotNull(found, "El Learning Path debería encontrarse");
        assertEquals("Learning Path 1", found.getTitulo(), "El título debería coincidir");

        // Intenta buscar un ID inexistente
        assertNull(profesor.buscarLearningPath(999), "Debería devolver null para un ID inexistente");
    }

    @Test
    void testEditarLearningPath() {
        // Edita un Learning Path existente
        profesor.editarLearningPath(1, "Nuevo Título", "Nueva Descripción", "Nuevo Objetivo", "Experto", "2.0");

        // Verifica que los cambios se hayan aplicado
        LearningPath lpEditado = profesor.buscarLearningPath(1);
        assertNotNull(lpEditado, "El Learning Path editado no debería ser null");
        assertEquals("Nuevo Título", lpEditado.getTitulo(), "El título debería actualizarse");
        assertEquals("Nueva Descripción", lpEditado.getDescripcion(), "La descripción debería actualizarse");
        assertEquals("Experto", lpEditado.getNivel_dificultad(), "El nivel de dificultad debería actualizarse");
    }

    @Test
    void testEliminarLearningPath() {
        // Elimina un Learning Path existente
        profesor.eliminarLearningPath(1);

        // Verifica que se haya eliminado
        assertNull(profesor.buscarLearningPath(1), "El Learning Path debería eliminarse");
        assertTrue(profesor.getLearningPathsCreados().isEmpty(), 
            "La lista de Learning Paths creados debería estar vacía");
    }

    @Test
    void testAgregarActividadALearningPath() {
        // Agrega dos actividades a un Learning Path existente
        profesor.agregarActividadALearningPath(1, actividad1);
        profesor.agregarActividadALearningPath(1, actividad2);

        // Verifica que las actividades se hayan añadido correctamente
        LearningPath lp = profesor.buscarLearningPath(1);
        assertNotNull(lp, "El Learning Path debería existir");
        assertEquals(2, lp.getActividades().size(), "Debería haber dos actividades agregadas");
        assertTrue(lp.getActividades().contains(actividad1), "La actividad 1 debería estar en el Learning Path");
        assertTrue(lp.getActividades().contains(actividad2), "La actividad 2 debería estar en el Learning Path");
    }

    @Test
    void testEditarActividadEnLearningPath() {
        // Agrega una actividad inicial
        profesor.agregarActividadALearningPath(1, actividad1);

        // Define una nueva actividad para reemplazar a la inicial
        Actividad nuevaActividad = new actividad_para_Test(
                101,
                "Actividad Actualizada",
                "Nuevo Objetivo",
                "Avanzado",
                new ArrayList<>(),
                "tarea",
                new ArrayList<>(),
                false,
                new Date(),
                45
        );

        // Edita la actividad en el Learning Path
        profesor.editarActividadEnLearningPath(1, actividad1, nuevaActividad);

        // Verifica que se haya actualizado correctamente
        LearningPath lp = profesor.buscarLearningPath(1);
        assertNotNull(lp, "El Learning Path debería existir");
        assertTrue(lp.getActividades().contains(nuevaActividad), "La actividad actualizada debería estar en el Learning Path");
    }

    @Test
    void testEliminarActividadEnLearningPath() {
        // Agrega una actividad al Learning Path
        profesor.agregarActividadALearningPath(1, actividad1);

        // Elimina la actividad
        profesor.eliminarActividadEnLearningPath(1, actividad1);

        // Verifica que se haya eliminado correctamente
        LearningPath lp = profesor.buscarLearningPath(1);
        assertNotNull(lp, "El Learning Path debería existir");
        assertTrue(lp.getActividades().isEmpty(), "La lista de actividades debería estar vacía tras la eliminación");
    }

    @Test
    void testCrearActividad() {
        // Simula la creación de una actividad de tipo encuesta
        BufferedReader lector = new BufferedReader(new StringReader("Enviado\n"));
        Actividad actividad = profesor.crearActividad(201, "Nueva Actividad", "Nuevo Objetivo", "Intermedio",
                new ArrayList<>(), false, new Date(), 120, "encuesta", lector);

        // Verifica que la actividad se haya creado correctamente
        assertNotNull(actividad, "La actividad creada no debería ser null");
        assertEquals(201, actividad.getID_actividad(), "El ID de la actividad debería coincidir");
        assertEquals("Nueva Actividad", actividad.getDescripcion(), "La descripción de la actividad debería coincidir");
    }

    @Test
    void testEvaluarEstudiante() {
        // Simula la evaluación de un estudiante
        BufferedReader lector = new BufferedReader(new StringReader("95.0\n"));

        Estudiante estudiante = new Estudiante(3001, "student1", "password123", new ArrayList<>());

        // Registra el estudiante en el sistema y lo inscribe en un Learning Path
        Recommendation.getInstance().getUsuarios().put(estudiante.getId_usuario(), estudiante);
        estudiante.inscribirseLearningPath(lp1);

        // Crea un examen y lo marca como enviado
        Examen examen = new Examen(
            201,
            "Examen 1",
            "Objetivo Examen",
            "Avanzado",
            new ArrayList<>(),
            "examen",
            new ArrayList<>(),
            true,
            new Date(),
            "Enviado",
            new Date(),
            new ArrayList<>(),
            90
        );
        estudiante.realizarActividad(examen, lp1.getId_LP());

        // Evalúa al estudiante
        profesor.evaluarEstudiante(estudiante.getId_usuario(), lp1.getId_LP(), lector);

        // Verifica que se haya registrado la calificación y el examen esté revisado
        assertEquals(1, estudiante.getCalificaciones().size(), "El estudiante debería tener una calificación registrada");
        assertEquals(95.0, estudiante.getCalificaciones().get(0), 0.01, "La calificación debería ser 95.0");
        assertEquals("Revisado", examen.getEstado_entrega(), "El estado del examen debería ser 'Revisado'");
    }
}
