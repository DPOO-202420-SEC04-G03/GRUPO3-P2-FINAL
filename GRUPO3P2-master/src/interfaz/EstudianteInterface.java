package interfaz;

import java.util.Scanner;

import persistencia.Recommendation;
import persistencia.Autenticador;
import usuario.Estudiante; 
import usuario.Usuario;

public class EstudianteInterface {

    public static void main(String[] args) {
        // Inicialización de Recommendation y carga de datos
        Recommendation recommendation = Recommendation.getInstance();
        recommendation.cargarUsuarios();
        recommendation.cargarLearningPaths();
        recommendation.cargarActividades();

        // Inicialización de Autenticador con los usuarios de Recommendation
        Autenticador autenticador = new Autenticador(recommendation.getUsuarios());
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido, Estudiante");

        // Solicitar autenticación
        System.out.print("Ingrese su login: ");
        String login = scanner.next();
        System.out.print("Ingrese su password: ");
        String password = scanner.next();

        // Autenticación del usuario
        Usuario usuario = autenticador.autenticar(login, password);

        if (usuario instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) usuario;
            int opcion;

            do {
                // Menú de opciones
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Inscribirse a un Learning Path");
                System.out.println("2. Realizar Actividad");
                System.out.println("3. Ver Progreso");
                System.out.println("4. Escribir Reseña");
                System.out.println("5. Ver Duración de Learning Path");
                System.out.println("6. Salir");
                System.out.print("Opción: ");

                // Validación de entrada para asegurar que sea un número
                while (!scanner.hasNextInt()) {
                    System.out.println("Por favor, ingrese un número.");
                    scanner.next(); // Limpiar entrada no válida
                }
                opcion = scanner.nextInt();

                // Procesar la opción seleccionada
                switch (opcion) {
                    case 1:
                        recommendation.req6InscribirseALp(); // Método para inscribirse a un Learning Path
                        break;
                    case 2:
                        recommendation.req7RealizarActividad(); // Método para realizar una actividad
                        break;
                    case 3:
                        recommendation.req8VerProgreso(); // Método para ver el progreso
                        break;
                    case 4:
                        recommendation.req10EscribirResena(); // Método para escribir una reseña
                        break;
                    case 5:
                        recommendation.req9DuracionLp(); // Método para ver la duración del Learning Path
                        break;
                    case 6:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            } while (opcion != 6); // Repetir hasta que elija salir

        } else {
            System.out.println("Autenticación fallida. Login o contraseña incorrectos.");
        }

        scanner.close();
    }
}