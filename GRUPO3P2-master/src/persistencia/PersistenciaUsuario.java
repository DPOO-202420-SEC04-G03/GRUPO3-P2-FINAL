package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Set;
import java.util.Arrays;

import java.util.stream.Collectors;

import actividad.Actividad;
import usuario.Estudiante;
import usuario.Profesor;
import usuario.Usuario;
import learningpath.LearningPath;

public class PersistenciaUsuario {

    private String rutaArchivo;

    public PersistenciaUsuario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    // Método para cargar los usuarios desde el archivo
    public HashMap<Integer, Usuario> CargarUsuarios(HashMap<Integer, LearningPath> learningpaths,
            HashMap<Integer, Actividad> actividades) {
        HashMap<Integer, Usuario> usuarios = new HashMap<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");
                String tipoUsuario = partes[0];
                int idUsuario = Integer.parseInt(partes[1]);
                String login = partes[2];
                String password = partes[3];

                Usuario usuario = null;

                // Verifica el tipo de usuario
                if (tipoUsuario.equals("Profesor")) {
                    Set<LearningPath> creados = new HashSet<>();
                    for (String id : partes[4].split(",")) {
                        creados.add(learningpaths.get(Integer.parseInt(id)));
                    }
                    Profesor profe = new Profesor(idUsuario, login, password);
                    profe.setLearningPathsCreados(creados);
                    usuario = profe;
                } else if (tipoUsuario.equals("Estudiante")) {
                    
                    // Crear una instancia de Estudiante
                    ArrayList<Integer> idinscritos = Arrays.stream(partes[4].split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toCollection(ArrayList::new));
                    ArrayList<LearningPath> inscritos = new ArrayList<>();
                    for (Integer id : idinscritos) {
                        inscritos.add(learningpaths.get(id));

                    }
                    Estudiante estudiante = new Estudiante(idUsuario, login, password, inscritos);
                    String[] llavesvalores = partes[5].split(":");
                    HashMap<Integer, Set<Actividad>> mapa = new HashMap<>();
                    for (String pareja : llavesvalores) {
                        ArrayList<Integer> numeros = Arrays.stream(pareja.split(",")).map(Integer::parseInt)
                                .collect(Collectors.toCollection(ArrayList::new));
                        ;
                        int id_LP = numeros.remove(0);
                        Set<Actividad> setactividades = new HashSet<>();
                        for (int idActividad : numeros) {
                            setactividades.add(actividades.get(idActividad));
                        }
                        mapa.put(id_LP, setactividades);
                    }

                    estudiante.setActividadesRealizadas(mapa);

                    ArrayList <Double> calificaciones = Arrays.stream(partes[6].split(","))
                    .map(Double::parseDouble)
                    .collect(Collectors.toCollection(ArrayList::new));
                    estudiante.setCalificaciones(calificaciones);
                    usuario = estudiante;
                }

                // Añadir el usuario al mapa si no es nulo
                if (usuario != null) {
                    usuarios.put(idUsuario, usuario);
                    
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return usuarios;
    }

    // Método para persistir los usuarios en el archivo
    public void PersistirUsuarios(HashMap<Integer, Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Usuario usuario : usuarios.values()) {
                StringBuilder sb = new StringBuilder();

                // Agrega el tipo de usuario al principio de la línea
                if (usuario instanceof Profesor) {
                    sb.append("Profesor;");
                } else if (usuario instanceof Estudiante) {
                    sb.append("Estudiante;");
                }

                sb.append(usuario.getId_usuario()).append(";")
                        .append(usuario.getLogin()).append(";")
                        .append(usuario.getPassword());

                // Escribe la línea al archivo
                writer.write(sb.toString());
                writer.newLine(); // Salto de línea para el siguiente usuario
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
}
