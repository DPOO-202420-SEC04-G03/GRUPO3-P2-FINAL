package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividad.Actividad;
import actividad.actividad_para_Test;
import learningpath.LearningPath;
import usuario.Estudiante;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class EstudianteTest {

    // Instancia del estudiante para pruebas
    private Estudiante estudiante;
    // Learning Paths para las pruebas
    private LearningPath lp1;
    private LearningPath lp2;
    // Actividades asociadas a los Learning Paths
    private actividad_para_Test actividad1;
    private actividad_para_Test actividad2;

    @BeforeEach
    void setUp() {
        // Configuración inicial antes de cada test

        // Crear actividades de prueba
        actividad1 = new actividad_para_Test(
                1,
                "Actividad 1",
                "Descripción de actividad 1",
                "Principiante",
                new ArrayList<>(),
                "Teórico",
                new ArrayList<>(),
                false,
                new Date(),
                30 // Duración en minutos
        );

        actividad2 = new actividad_para_Test(
                2,
                "Actividad 2",
                "Descripción de actividad 2",
                "Intermedio",
                new ArrayList<>(),
                "Práctico",
                new ArrayList<>(),
                false,
                new Date(),
                45 // Duración en minutos
        );

        // Crear un conjunto de actividades y asignarlo al primer Learning Path
        Set<Actividad> actividadesLP1 = new HashSet<>();
        actividadesLP1.add(actividad1);
        actividadesLP1.add(actividad2);

        // Configurar el primer Learning Path con actividades
        lp1 = new LearningPath(
            "Learning Path 1",
            "Descripción de LP1",
            actividadesLP1,
            "Principiante",
            1,
            new Date(),
            "1.0",
            "Objetivo de LP1"
        );

        // Crear un segundo Learning Path vacío para pruebas adicionales
        lp2 = new LearningPath(
            "Learning Path 2",
            "Descripción de LP2",
            new HashSet<>(),
            "Intermedio",
            2,
            new Date(),
            "1.1",
            "Objetivo de LP2"
        );

        // Asignar el primer Learning Path al estudiante al inicializarlo
        List<LearningPath> learningPaths = new ArrayList<>();
        learningPaths.add(lp1);
        estudiante = new Estudiante(1001, "test_user", "password123", learningPaths);
    }

    @Test
    void testEstadoInicial() {
        // Verifica que el estudiante tenga un Learning Path al inicio
        assertNotNull(estudiante.getLearningPathInscritos(), "El estudiante debería tener Learning Paths asignados.");
        assertEquals(1, estudiante.getLearningPathInscritos().size(), "El estudiante debería tener exactamente un Learning Path inscrito.");
        assertTrue(estudiante.getLearningPathInscritos().contains(lp1), "El estudiante debería estar inscrito en el primer Learning Path.");
    }

    @Test
    void testRegistroActividades() {
        // Registra actividades realizadas por el estudiante
        estudiante.realizarActividad(actividad1, 1);
        estudiante.realizarActividad(actividad2, 1);

        // Verifica que las actividades se registren correctamente
        Set<Actividad> actividadesRealizadas = estudiante.getActividadesRealizadas().get(1);
        assertNotNull(actividadesRealizadas, "Las actividades realizadas no deberían ser null.");
        assertEquals(2, actividadesRealizadas.size(), "El estudiante debería haber completado dos actividades.");
        assertTrue(actividadesRealizadas.contains(actividad1), "La primera actividad debería estar registrada.");
        assertTrue(actividadesRealizadas.contains(actividad2), "La segunda actividad debería estar registrada.");
    }

    @Test
    void testAgregarCalificacion() {
        // Agrega calificaciones al estudiante
        estudiante.agregarCalificacion(4.5);
        estudiante.agregarCalificacion(3.8);

        // Verifica que las calificaciones se hayan guardado correctamente
        List<Double> calificaciones = estudiante.getCalificaciones();
        assertEquals(2, calificaciones.size(), "El estudiante debería tener dos calificaciones registradas.");
        assertEquals(4.5, calificaciones.get(0), "La primera calificación debería ser 4.5.");
        assertEquals(3.8, calificaciones.get(1), "La segunda calificación debería ser 3.8.");
    }

    @Test
    void testInscribirseLearningPath() {
        // Inscribe al estudiante en un segundo Learning Path
        estudiante.inscribirseLearningPath(lp2);

        // Verifica que el estudiante ahora esté inscrito en dos Learning Paths
        Set<LearningPath> learningPaths = estudiante.getLearningPathInscritos();
        assertEquals(2, learningPaths.size(), "El estudiante debería estar inscrito en dos Learning Paths.");
        assertTrue(learningPaths.contains(lp1), "El primer Learning Path debería seguir inscrito.");
        assertTrue(learningPaths.contains(lp2), "El segundo Learning Path debería estar inscrito.");
    }

    @Test
    void testMostrarProgreso() {
        // Registra una actividad realizada
        estudiante.realizarActividad(actividad1, 1);

        // Obtiene el progreso del estudiante en el Learning Path 1
        String progreso = estudiante.mostrarProgreso(1);

        // Verifica que el progreso sea el esperado
        String expectedProgreso = "Learning Path: Learning Path 1\n" +
                                  "Progreso: 1/2 actividades completadas (50,00%)";
        assertEquals(expectedProgreso, progreso, "El progreso mostrado no es correcto.");
    }

    @Test
    void testRealizarActividad() {
        // Registra dos actividades realizadas
        estudiante.realizarActividad(actividad1, 1);
        estudiante.realizarActividad(actividad2, 1);

        // Verifica que ambas actividades estén registradas
        Set<Actividad> actividadesRealizadas = estudiante.getActividadesRealizadas().get(1);
        assertNotNull(actividadesRealizadas, "Las actividades realizadas no deberían ser null.");
        assertEquals(2, actividadesRealizadas.size(), "Debería haber dos actividades registradas.");
        assertTrue(actividadesRealizadas.contains(actividad1), "La primera actividad debería estar registrada.");
        assertTrue(actividadesRealizadas.contains(actividad2), "La segunda actividad debería estar registrada.");
    }

    @Test
    void testEscribirResena() {
        // El estudiante escribe una reseña para una actividad
        String resena = "Esta actividad fue muy útil.";
        estudiante.escribirResena(actividad1, resena);

        // Verifica que la reseña se haya guardado correctamente
        assertEquals(resena, actividad1.getResenas().get(0), "La reseña no se guardó correctamente.");
    }

    @Test
    void testVerProgreso() {
        // Registra actividades realizadas
        estudiante.realizarActividad(actividad1, 1);
        estudiante.realizarActividad(actividad2, 1);

        // Obtiene el progreso global del estudiante
        String progreso = estudiante.verProgreso();
        String expectedOutput = "Learning Path: Learning Path 1\n" +
                                "Progreso: 2/2 actividades completadas (100,00%)";

        // Verifica que el progreso global sea correcto
        assertEquals(expectedOutput.trim(), progreso.trim(), "El progreso global mostrado no es correcto.");
    }

    @Test
    void testConsistenciaProgreso() {
        // Registra actividades realizadas
        estudiante.realizarActividad(actividad1, 1);
        estudiante.realizarActividad(actividad2, 1);

        // Verifica que los métodos de progreso sean consistentes
        String progresoMostrar = estudiante.mostrarProgreso(1);
        String progresoVer = estudiante.verProgreso();

        assertTrue(progresoVer.contains(progresoMostrar), 
                   "Los métodos mostrarProgreso y verProgreso no son consistentes.");
    }
}
