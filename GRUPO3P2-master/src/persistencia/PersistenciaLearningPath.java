package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.stream.Collectors;


import learningpath.LearningPath;
import actividad.Actividad;

public class PersistenciaLearningPath {

    private String rutaArchivo;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
    

    public PersistenciaLearningPath(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        
    }

    


    public HashMap<Integer, LearningPath> cargarLearningPaths(HashMap<Integer, Actividad> mapactividades) {
        HashMap<Integer, LearningPath> learningPaths = new HashMap<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
    
            // Leer la primera línea y verificar si es un encabezado
            if ((linea = lector.readLine()) != null && linea.contains("id_LP")) {
                // Omitir la línea si contiene encabezados
                linea = lector.readLine();
            }
    
            // Leer el resto del archivo
            while (linea != null) {
                String[] partes = linea.split(";");
    
                // Campos básicos
                int id_LP = Integer.parseInt(partes[0]);
                String titulo = partes[1];
                String descripcion = partes[2];
                String objetivo = partes[3];
                String nivel_dificultad = partes[4];
                Date fecha_creacion = convertirFecha(partes[5]);
                String version = partes[6];
                int duracion = Integer.parseInt(partes[7]);
                ArrayList<Double> ratings = Arrays.stream(partes[8].split(","))
                                 .map(Double::parseDouble)
                                 .collect(Collectors.toCollection(ArrayList::new));

                Date fecha_modificacion = convertirFecha(partes[9]);
    
                // Cargar actividades por sus IDs
                Set<Actividad> actividades = new HashSet<>();
                if (partes.length > 10 && !partes[10].isEmpty()) {
                    for (String idActividadStr : partes[10].split(",")) {
                        int idActividad = Integer.parseInt(idActividadStr.trim());
                        Actividad actividad = mapactividades.get(idActividad);
                        if (actividad != null) {
                            actividades.add(actividad);
                        }
                    }
                }
    
                // Crear y almacenar el LearningPath
                LearningPath lp = new LearningPath(titulo, descripcion, actividades, nivel_dificultad, id_LP, fecha_creacion, version, objetivo);
                lp.setDuracion(duracion);  // Establecer la duración leída
                learningPaths.put(id_LP, lp);
                lp.setFecha_modificacion(fecha_modificacion);
                lp.setratings(ratings);

    
                // Leer la siguiente línea
                linea = lector.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir un número: " + e.getMessage());
        }
        return learningPaths;
    }
    

    public void persistirLearningPaths(HashMap<Integer, LearningPath> existentes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Map.Entry<Integer, LearningPath> entry : existentes.entrySet()) {
                LearningPath lp = entry.getValue();
                StringBuilder sb = new StringBuilder();

                // Campos básicos
                sb.append(lp.getId_LP()).append(";");
                sb.append(lp.getTitulo()).append(";");
                sb.append(lp.getDescripcion()).append(";");
                sb.append(lp.getObjetivo()).append(";");
                sb.append(lp.getNivel_dificultad()).append(";");
                sb.append(dateFormat.format(lp.getFecha_creacion())).append(";");
                sb.append(lp.getVersion()).append(";");
                sb.append(lp.getDuracion()).append(";");
                Set<Double> ratings = new HashSet <>();
                for (Double rating : lp.getratings()) {
                    ratings.add(rating);
                }
                sb.append(String.join(",", ratings.toString().replaceAll("[\\[\\] ]", "")));
                sb.append(";");
                sb.append(dateFormat.format(lp.getFecha_modificacion())).append(";");

                // Guardar IDs de las actividades
                Set<Integer> actividadIds = new HashSet<>();
                for (Actividad actividad : lp.getActividades()) {
                    actividadIds.add(actividad.getID_actividad());
                }
                sb.append(String.join(",", actividadIds.toString().replaceAll("[\\[\\] ]", "")));

                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }
    }

    private Date convertirFecha(String fechaTexto) {
        try {
            return dateFormat.parse(fechaTexto);
        } catch (ParseException e) {
            System.out.println("Error al convertir la fecha: " + e.getMessage());
            return null; // Manejo de error si no se puede parsear la fecha
        }
    }
}
