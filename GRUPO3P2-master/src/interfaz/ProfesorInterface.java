package interfaz;

import java.util.Scanner;
import persistencia.Recommendation;
import persistencia.Autenticador;
import usuario.Profesor;
import usuario.Usuario;

public class ProfesorInterface {

    public static void main(String[] args) {
        // Inicialización de Recommendation y carga de datos
        Recommendation recommendation = Recommendation.getInstance();
        recommendation.cargarUsuarios();
        recommendation.cargarLearningPaths();
        recommendation.cargarActividades();

        // Inicialización de Autenticador con los usuarios de Recommendation
        Autenticador autenticador = new Autenticador(recommendation.getUsuarios());
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido, Profesor");

        // Solicitar autenticación
        System.out.print("Ingrese su login: ");
        String login = scanner.next();
        System.out.print("Ingrese su password: ");
        String password = scanner.next();

        // Autenticación del usuario
        Usuario usuario = autenticador.autenticar(login, password);

        if (usuario instanceof Profesor) {
            Profesor profesor = (Profesor) usuario;

            int opcion;
            do {
                // Mostrar menú de opciones
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Crear Actividad");
                System.out.println("2. Eliminar Actividad");
                System.out.println("3. Crear Learning Path");
                System.out.println("4. Eliminar Learning Path");
                System.out.println("5. Evaluar Actividad");
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
                        recommendation.req3CrearActividad(); // Método para crear una actividad
                        break;
                    case 2:
                        recommendation.req4EliminarActividad(); // Método para eliminar una actividad
                        break;
                    case 3:
                        recommendation.req1CrearLP(); // Método para crear un Learning Path
                        break;
                    case 4:
                        recommendation.req2EliminarLp(); // Método para eliminar un Learning Path
                        break;
                    case 5:
                        recommendation.req5EvaluarActividad(); // Método para evaluar Actividad
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