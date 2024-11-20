package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


import actividad.Actividad;
import actividad.Encuesta;
import actividad.Examen;
import actividad.Quiz;
import actividad.RecursoEducativo;
import actividad.Tarea;
import pregunta.Pregunta;
import pregunta.PreguntaAbierta;
import pregunta.PreguntaOpcionMultiple;
import pregunta.PreguntaVerdaderoFalso;

public class PersistenciaActividades {

    private String rutaArchivo;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

    private HashMap<Integer, Actividad> actividades;

    public PersistenciaActividades(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;

    }

    public Actividad obtenerActividadPorID(int idActividad) {
        return actividades.get(idActividad);
    }

    public HashMap<Integer, Actividad> CargarActividades() {
        HashMap<Integer, Actividad> actividades = new HashMap<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] partes = (linea.split(";"));
                String tipo = partes[0];

                int id = Integer.parseInt(partes[1]);
                String description = partes[2];
                String objetivo = partes[3];
                String niveldedificultad = partes[4];
                ArrayList<String> resenas = new ArrayList<>(List.of(partes[5].split("'")));
                String[] sugeridasstStrings = partes[6].split(",");
                int[] sugeridas = new int[sugeridasstStrings.length];
                for (int i = 0; i < sugeridasstStrings.length; i++) {
                    if (sugeridasstStrings[i].equals(""))
                        continue;
                    sugeridas[i] = Integer.parseInt(sugeridasstStrings[i]);
                }
                ArrayList<Actividad> actividadessugeridas = new ArrayList<>();
                for (int s : sugeridas) {
                    actividadessugeridas.add(actividades.get(s));
                }
                boolean prerrequisitos = Boolean.parseBoolean(partes[7]);
                Date fechaLimite = convertirFecha(partes[8]);
                int duracion = Integer.parseInt(partes[9]);

                if (tipo.equals("Tarea")) {
                    String estadoEntregaTarea = partes[10];
                    Date fechaEntrega = convertirFecha(partes[11]);
                    String medioEntregaTarea = partes[12];
                    Tarea tarea = new Tarea(id, description, objetivo, niveldedificultad, resenas, "Tarea",
                            actividadessugeridas, prerrequisitos, fechaLimite, estadoEntregaTarea, fechaEntrega,
                            medioEntregaTarea,
                            duracion);
                    actividades.put(id, tarea);

                } else if (tipo.equals("Quiz")) {
                    float calificacion_minima = Float.parseFloat(partes[10]);
                    float calificacion_obtenida = Float.parseFloat(partes[11]);
                    List<Pregunta> preguntas = new ArrayList<>();
                    String[] preguntasArray = partes[12].split(","); // Cambia el delimitador según tu formato

                    for (String preguntaTexto : preguntasArray) {
                        // Divide el texto en sus componentes según el tipo de pregunta
                        String[] detalles = preguntaTexto.trim().split(":");

                        String tipoPregunta = detalles[0].trim();
                        int idPregunta = Integer.parseInt(detalles[1].trim());
                        String descripcionPregunta = detalles[2].trim();
                        int calificacionPregunta = Integer.parseInt(detalles[3].trim());

                        if (tipoPregunta.equals("Abierta")) {
                            // Si es una pregunta abierta
                            String estadoEntrega = detalles[4].trim();
                            String respuestaEstudiante = detalles[5].trim();

                            Pregunta pregunta = new PreguntaAbierta(idPregunta, descripcionPregunta,
                                    calificacionPregunta, estadoEntrega, respuestaEstudiante);
                            preguntas.add(pregunta);

                        } else if (tipoPregunta.equals("OpcionMultiple")) {
                            // Si es una pregunta de opción múltiple
                            List<String> opciones = List.of(detalles[4].trim().split(","));
                            String opcionCorrecta = detalles[5].trim();
                            String explicacion = detalles[6].trim();
                            Pregunta pregunta = new PreguntaOpcionMultiple(idPregunta, descripcionPregunta,
                                    calificacionPregunta, opciones, opcionCorrecta, explicacion);
                            preguntas.add(pregunta);
                        }
                    }
                    // Convierte la lista de preguntas a un HashSet de PreguntaVerdaderoFalso antes
                    // de pasarlo al constructor
                    HashSet<PreguntaVerdaderoFalso> preguntasSet = new HashSet<>();
                    for (Pregunta pregunta : preguntas) {
                        if (pregunta instanceof PreguntaVerdaderoFalso) {
                            preguntasSet.add((PreguntaVerdaderoFalso) pregunta); // Convierte y añade al set
                        }
                    }

                    // Crear la instancia de Quiz con el conjunto de preguntas
                    Quiz quiz = new Quiz(id, description, objetivo, niveldedificultad, resenas, "Quiz",
                            actividadessugeridas, prerrequisitos, fechaLimite, calificacion_minima,
                            calificacion_obtenida, preguntasSet, duracion);
                    actividades.put(id, quiz);

                } else if (tipo.equals("Examen")) {
                    // El código que sigue para el caso "Examen"

                    String estado_entrega = partes[10];
                    Date fecha_entrega = convertirFecha(partes[11]); // Método para convertir la fecha de String a Date
                    List<Pregunta> preguntas = new ArrayList<>();
                    String[] preguntasArray = partes[12].split(","); // Cambia el delimitador según tu formato

                    for (String preguntaTexto : preguntasArray) {
                        // Divide el texto en sus componentes según el tipo de pregunta
                        String[] detalles = preguntaTexto.trim().split(":");

                        String tipoPregunta = detalles[0].trim();
                        int idPregunta = Integer.parseInt(detalles[1].trim());
                        String descripcionPregunta = detalles[2].trim();
                        int calificacionPregunta = Integer.parseInt(detalles[3].trim());

                        if (tipoPregunta.equals("Abierta")) {
                            // Si es una pregunta abierta
                            String estadoEntrega = detalles[4].trim();
                            String respuestaEstudiante = detalles[5].trim();

                            Pregunta pregunta = new PreguntaAbierta(idPregunta, descripcionPregunta,
                                    calificacionPregunta, estadoEntrega, respuestaEstudiante);
                            preguntas.add(pregunta);

                        } else if (tipoPregunta.equals("OpcionMultiple")) {
                            // Si es una pregunta de opción múltiple
                            List<String> opciones = List.of(detalles[4].trim().split(","));
                            String opcionCorrecta = detalles[5].trim();
                            String explicacion = detalles[6].trim();

                            Pregunta pregunta = new PreguntaOpcionMultiple(idPregunta, descripcionPregunta,
                                    calificacionPregunta, opciones, opcionCorrecta, explicacion);
                            preguntas.add(pregunta);
                        }
                    }
                    // Crear la instancia de Examen con la lista de preguntas
                    Examen examen = new Examen(id, description, objetivo, niveldedificultad, resenas, "Examen",
                            actividadessugeridas, prerrequisitos, fechaLimite, estado_entrega,
                            fecha_entrega, preguntas, duracion);
                    actividades.put(id, examen);
                } else if (tipo.equals("Encuesta")) {
                    String estado_entrega = partes[10];
                    List<Pregunta> preguntas = new ArrayList<>();
                    String[] preguntasArray = partes[11].split(","); // Cambia el delimitador según tu formato

                    for (String preguntaTexto : preguntasArray) {

                        String[] detalles = preguntaTexto.trim().split(":");

                        int idPregunta = Integer.parseInt(detalles[0].trim());
                        String descripcionPregunta = detalles[1].trim();
                        int calificacionPregunta = Integer.parseInt(detalles[2].trim());
                        String estadoEntregaPregunta = detalles[3].trim();
                        String respuestaEstudiante = detalles[4].trim();

                        Pregunta pregunta = new PreguntaAbierta(idPregunta, descripcionPregunta, calificacionPregunta,
                                estadoEntregaPregunta, respuestaEstudiante);
                        preguntas.add(pregunta);
                    }

                    Encuesta encuesta = new Encuesta(id, description, objetivo, niveldedificultad, resenas, "Encuesta",
                            actividadessugeridas, prerrequisitos, fechaLimite, estado_entrega,
                            preguntas, duracion);
                    actividades.put(id, encuesta);

                } else if (tipo.equals("RecursoEducativo")) {
                    String tipoRecurso = partes[10];
                    String url = partes[11];

                    RecursoEducativo recursoEducativo = new RecursoEducativo(id, description, objetivo,
                            niveldedificultad, resenas,
                            "RecursoEducativo", actividadessugeridas, prerrequisitos, fechaLimite, tipoRecurso, url,
                            duracion);

                    actividades.put(id, recursoEducativo);
                }

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return actividades;

    }

    public void PersistirActividades(HashMap<Integer, Actividad> existentes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Map.Entry<Integer, Actividad> entry : existentes.entrySet()) {
                Actividad actividad = entry.getValue();

                // Crea una línea en formato CSV o similar separada por ;
                StringBuilder sb = new StringBuilder();
                sb.append(actividad.getTipo_actividad()).append(";"); // Tipo de actividad
                sb.append(actividad.getID_actividad()).append(";"); // ID de la actividad
                sb.append(actividad.getDescripcion()).append(";"); // Descripción
                sb.append(actividad.getObjetivo()).append(";"); // Objetivo
                sb.append(actividad.getNivel_dificultad()).append(";"); // Nivel de dificultad
                sb.append(String.join("'", actividad.getResena())).append(";"); // Reseñas separadas por '
                for (Actividad sugerida : actividad.getActividades_sugeridas()) {
                    sb.append(sugerida.getID_actividad()).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);

                sb.append(actividad.getPrerrequisitos()).append(";"); // Prerrequisitos
                sb.append(actividad.getFecha_limite()).append(";"); // Fecha límite (puedes formatear si es un Date)
                sb.append(actividad.getDuracion()).append(";"); // Duración

                // Dependiendo del tipo de actividad, agrega atributos específicos
                if (actividad instanceof Tarea) {
                    Tarea tarea = (Tarea) actividad;
                    sb.append(tarea.getEstado_entrega()).append(";");
                    sb.append(tarea.getFecha_entrega()).append(';');
                    sb.append(tarea.getMedio_entrega());
                } else if (actividad instanceof Quiz) {
                    Quiz quiz = (Quiz) actividad;
                    sb.append(quiz.getCalificacion_minima()).append(";");
                    sb.append(quiz.getCalificacion_obtenida()).append(";");
                    // Procesar cada pregunta en la lista de preguntas del Quiz
                    for (Pregunta pregunta : quiz.getPreguntas()) {
                        if (pregunta instanceof PreguntaAbierta) {
                            PreguntaAbierta abierta = (PreguntaAbierta) pregunta;
                            sb.append("Abierta:").append(abierta.getID_pregunta()).append(":")
                                    .append(abierta.getDescripcion()).append(":")
                                    .append(abierta.getCalificacion()).append(":")
                                    .append(abierta.getEstado_entrega()).append(":")
                                    .append(abierta.getRespuesta_estudiante()).append(",");
                        } else if (pregunta instanceof PreguntaOpcionMultiple) {
                            PreguntaOpcionMultiple opcionMultiple = (PreguntaOpcionMultiple) pregunta;
                            sb.append("OpcionMultiple:").append(opcionMultiple.getID_pregunta()).append(":")
                                    .append(opcionMultiple.getDescripcion()).append(":")
                                    .append(opcionMultiple.getCalificacion()).append(":")
                                    .append(String.join(",", opcionMultiple.getOpciones())).append(":")
                                    .append(opcionMultiple.getOpcion_correcta()).append(":")
                                    .append(opcionMultiple.getExplicacion()).append(",");
                        }
                    }

                } else if (actividad instanceof Examen) {
                    Examen examen = (Examen) actividad;
                    sb.append(examen.getEstado_entrega()).append(";");
                    String fechaEntregaStr = dateFormat.format(examen.getFecha_entrega());
                    sb.append(fechaEntregaStr).append(";");
                    for (Pregunta pregunta : examen.getPreguntas()) {
                        if (pregunta instanceof PreguntaAbierta) {
                            PreguntaAbierta abierta = (PreguntaAbierta) pregunta;
                            sb.append("Abierta:").append(abierta.getID_pregunta()).append(":")
                                    .append(abierta.getDescripcion()).append(":")
                                    .append(abierta.getCalificacion()).append(":")
                                    .append(abierta.getEstado_entrega()).append(":")
                                    .append(abierta.getRespuesta_estudiante()).append(",");
                        } else if (pregunta instanceof PreguntaOpcionMultiple) {
                            PreguntaOpcionMultiple opcionMultiple = (PreguntaOpcionMultiple) pregunta;
                            sb.append("OpcionMultiple:").append(opcionMultiple.getID_pregunta()).append(":")
                                    .append(opcionMultiple.getDescripcion()).append(":")
                                    .append(opcionMultiple.getCalificacion()).append(":")
                                    .append(String.join(",", opcionMultiple.getOpciones())).append(":")
                                    .append(opcionMultiple.getOpcion_correcta()).append(":")
                                    .append(opcionMultiple.getExplicacion()).append(",");
                        }
                    }

                } else if (actividad instanceof Encuesta) {
                    Encuesta encuesta = (Encuesta) actividad;
                    sb.append(encuesta.getEstado_entrega()).append(";");
                    for (Pregunta pregunta : encuesta.getPreguntas()) {
                        if (pregunta instanceof PreguntaAbierta) {
                            PreguntaAbierta abierta = (PreguntaAbierta) pregunta;
                            sb.append("Abierta:").append(abierta.getID_pregunta()).append(":")
                                    .append(abierta.getDescripcion()).append(":")
                                    .append(abierta.getCalificacion()).append(":")
                                    .append(abierta.getEstado_entrega()).append(":")
                                    .append(abierta.getRespuesta_estudiante()).append(",");
                        }
                    }

                    // Eliminar la última coma si hay preguntas
                    if (!encuesta.getPreguntas().isEmpty()) {
                        sb.deleteCharAt(sb.length() - 1);
                    }

                } else if (actividad instanceof RecursoEducativo) {
                    RecursoEducativo recurso = (RecursoEducativo) actividad;
                    sb.append(recurso.getTipoRecurso()).append(";");
                    sb.append(recurso.getUrl());
                }
                writer.write(sb.toString());
                writer.newLine(); // Salto de línea para la siguiente actividad
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
