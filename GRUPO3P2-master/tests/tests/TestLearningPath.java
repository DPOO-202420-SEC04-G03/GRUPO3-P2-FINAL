package tests;

import actividad.actividad_para_Test;
import learningpath.LearningPath;
import java.util.Set;
import java.util.HashSet;
import actividad.Actividad;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestLearningPath {

    private LearningPath learningPath;	
    private actividad_para_Test actividad1;
    private actividad_para_Test actividad2;
    

    @BeforeEach
    public void setUp() {
        // Crear actividades de prueba
        actividad1 = new actividad_para_Test(1, "Actividad 1", "Objetivo 1", "Intermedio", new ArrayList<>(), 
                "Tipo 1", new ArrayList<>(), false, null, 30);

        actividad2 = new actividad_para_Test(2, "Actividad 2", "Objetivo 2", "Avanzado", new ArrayList<>(), 
                "Tipo 2", new ArrayList<>(), false, null, 40);

        // Crear un conjunto de actividades inicial
        Set<Actividad> actividades = new HashSet<>();
        actividades.add(actividad1);
        actividades.add(actividad2);

        // Crear el Learning Path
        learningPath = new LearningPath(
            "Learning Path de Prueba",
            "Descripción de prueba",
            actividades, // Este conjunto no puede ser null
            "Avanzado",
            1,
            new Date(),
            "1.0",
            "Objetivo del Learning Path"
        );

        // Verificar que las actividades se inicializaron correctamente
        assertNotNull(learningPath.getActividades(), "El conjunto de actividades no debería ser null");
    }


    @Test
    public void testEditarActividad1() {
        System.out.println("Conjunto de actividades antes de editar: " + learningPath.getActividades());

        // Crear una nueva actividad para reemplazar
        actividad_para_Test nuevaActividad = new actividad_para_Test(1, "Actividad Editada", "Nuevo Objetivo", 
                "Avanzado", new ArrayList<>(), "Tipo Editado", new ArrayList<>(), false, null, 60);

        // Editar la actividad
        learningPath.editarActividad(actividad1, nuevaActividad);

        System.out.println("Conjunto de actividades después de editar: " + learningPath.getActividades());

        // Validar que la actividad antigua fue removida y la nueva fue añadida
        assertTrue(learningPath.getActividades().contains(nuevaActividad));
        assertFalse(learningPath.getActividades().contains(actividad1));

        // Verificar la duración total después de editar
        assertEquals(100, learningPath.getDuracion()); // 60 (nuevaActividad) + 40 (actividad2)
    }


    @Test
    public void testEliminarActividad() {
        learningPath.eliminarActividad(actividad1);
        assertFalse(learningPath.getActividades().contains(actividad1));
        assertEquals(1, learningPath.cantidadActividades());
        assertEquals(40, learningPath.getDuracion()); // Duración restante: 40 (actividad2)
    }

    @Test
    public void testCantidadActividades() {
        assertEquals(2, learningPath.cantidadActividades()); // Actividades iniciales
    }

    @Test
    public void testEditarActividad() {
        System.out.println("Conjunto de actividades antes de editar: " + learningPath.getActividades());

        actividad_para_Test nuevaActividad = new actividad_para_Test(1, "Actividad Editada", "Nuevo Objetivo", 
                "Avanzado", new ArrayList<>(), "Tipo Editado", new ArrayList<>(), false, null, 60);

        learningPath.editarActividad(actividad1, nuevaActividad);

        System.out.println("Conjunto de actividades después de editar: " + learningPath.getActividades());

        assertTrue(learningPath.getActividades().contains(nuevaActividad));
        assertFalse(learningPath.getActividades().contains(actividad1));
        assertEquals(100, learningPath.getDuracion()); // 60 (nuevaActividad) + 40 (actividad2)
    }


    @Test
    public void testAgregarRating() {
        learningPath.agregarRating(4.5);
        learningPath.agregarRating(3.8);

        assertEquals(2, learningPath.getratings().size());
        assertEquals(4.15, learningPath.getRating(), 0.01); // Promedio esperado: (4.5 + 3.8) / 2
    }

    @Test
    public void testPromedioRatingSinCalificaciones() {
        assertEquals(0.0, learningPath.getRating(), 0.01); // Sin calificaciones, el promedio debe ser 0
    }

    @Test
    public void testPrint() {
        String output = learningPath.print();
        assertTrue(output.contains("Título: Learning Path de Prueba"));
        assertTrue(output.contains("Nivel de Dificultad: Avanzado"));
        assertTrue(output.contains("Duración Total: 70 minutos")); // Duración inicial: 30 + 40
        assertTrue(output.contains("Actividades:"));
        assertTrue(output.contains("Actividad 1")); // Verifica que las actividades se impriman
        assertTrue(output.contains("Actividad 2"));
    }
}
