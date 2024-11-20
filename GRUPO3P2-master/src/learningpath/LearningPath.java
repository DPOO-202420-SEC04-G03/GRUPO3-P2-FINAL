package learningpath;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import actividad.Actividad;
import java.text.SimpleDateFormat;


// Atributos
public class LearningPath {       
    private String titulo;
    private String descripcion;
    private Set<Actividad> actividades; 
    private String nivel_dificultad;    // Principiante/Intermedio/Avanzado
    private int id_LP;
    private int duracion;
    private List<Double> ratings; // Almacena múltiples calificaciones
    private Date fecha_creacion;
    private Date fecha_modificacion;
    private String version;
    private String objetivo;  

    // Constructor
    public LearningPath(String titulo, String descripcion, Set<Actividad> actividades, String nivel_dificultad, int id_LP, Date fecha_creacion, String version, String objetivo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.actividades = (actividades != null) ? actividades : new HashSet<>(); // Asegura que actividades no sea null
        this.nivel_dificultad = nivel_dificultad;
        this.id_LP = id_LP;
        this.fecha_creacion = fecha_creacion;
        this.version = version;
        this.duracion = 0; // Inicializa en 0
        this.ratings = new ArrayList<>();
        this.fecha_modificacion = new Date();
        this.objetivo = objetivo;
        actualizarDuracion(); // Calcula la duración inicial
    }


	public LearningPath(String string, String descripcion2, Object string2, String nivel_dificultad2, int id_LP2,
			Date fecha_creacion2, String version2, String objetivo2) {
		// TODO Auto-generated constructor stub
	}

	// Setters y Getters
    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Actividad> getActividades() {
        return this.actividades;
    }

    public void setActividades(Set<Actividad> actividades) {
        this.actividades = actividades != null ? actividades : new HashSet<>();
    }


    public String getNivel_dificultad() {
        return this.nivel_dificultad;
    }

    public void setNivel_dificultad(String nivel_dificultad) {
        this.nivel_dificultad = nivel_dificultad;
    }

    public int getId_LP() {
        return this.id_LP;
    }

    public void setId_LP(int id_LP) {
        this.id_LP = id_LP;
    }

    public int getDuracion() {
        return this.duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }


    public List <Double> getratings(){
        return this.ratings;
    } 

    public double getRating() {

        if (this.ratings.isEmpty()) {

            return 0.0; 
        }
        double ratingtotal = 0;

        for (double rating : this.ratings) {
            ratingtotal += rating;
        }
        return ratingtotal / this.ratings.size(); 
    }
    public void setratings(ArrayList <Double> ratings ){
        this.ratings = ratings;
    }

    public Date getFecha_creacion() {
        return this.fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_modificacion() {
        return this.fecha_modificacion;
    }

    public void setFecha_modificacion(Date fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getObjetivo() {
        return this.objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    // Métodos

    public void agregarActividad(Actividad actividad) {
        this.actividades.add(actividad); 
        actualizarDuracion();
        this.fecha_modificacion = new Date();
    }
    
    private void actualizarDuracion() {
        int duracion_total = 0;
        for (Actividad actividad : this.actividades) {
            duracion_total += actividad.getDuracion();
        }
        this.duracion = duracion_total;
    }

    public void eliminarActividad(Actividad actividad) {
        this.actividades.remove(actividad);
        actualizarDuracion();
        this.fecha_modificacion = new Date();
    }

    public int cantidadActividades() {
        if (this.actividades == null) {
            return 0;
        }
        return this.actividades.size();
    }


    public void editarActividad(Actividad antigua, Actividad nueva) {
        if (antigua == null || nueva == null) {
            throw new IllegalArgumentException("Ni la actividad antigua ni la nueva pueden ser null");
        }

        if (!this.actividades.contains(antigua)) {
            throw new IllegalStateException("La actividad antigua no existe en el conjunto de actividades");
        }

        if (this.actividades.remove(antigua)) {
            this.actividades.add(nueva);
            actualizarDuracion();
            this.fecha_modificacion = new Date();
        }
    }

    public void agregarRating(double nuevoRating) {
        // Añade un nuevo rating a la lista y actualiza la fecha de modificación
        this.ratings.add(nuevoRating);
        this.fecha_modificacion = new Date();
    }

    // Métodos estáticos para búsqueda, edición y eliminación de Learning Paths
    public static LearningPath buscarLearningPath(Set<LearningPath> learningPaths, int id_LP) {
        for (LearningPath lp : learningPaths) {
            if (lp.getId_LP() == id_LP) {
                return lp;
            }
        }
        return null;
    }

    public static void editarLearningPath(Set<LearningPath> learningPaths, int id_LP, String nuevoTitulo, String nuevaDescripcion, String nuevoObjetivo, 
                                            String nuevoNivelDificultad, String nuevaVersion) {
        LearningPath learningPath = buscarLearningPath(learningPaths, id_LP);
        if (learningPath != null) {
            learningPath.setTitulo(nuevoTitulo);
            learningPath.setDescripcion(nuevaDescripcion);
            learningPath.setObjetivo(nuevoObjetivo);
            learningPath.setNivel_dificultad(nuevoNivelDificultad);
            learningPath.setVersion(nuevaVersion);
            learningPath.setFecha_modificacion(new Date());  
        }
    }

    public static void eliminarLearningPath(Set<LearningPath> learningPaths, int id_LP) {
        LearningPath learningPath = buscarLearningPath(learningPaths, id_LP);
        if (learningPath != null) {
            learningPaths.remove(learningPath);  
        }
    }
    public String print() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        StringBuilder respuesta = new StringBuilder();

        respuesta.append("ID Learning Path: ").append(id_LP).append("\n");
        respuesta.append("Título: ").append(titulo).append("\n");
        respuesta.append("Descripción: ").append(descripcion).append("\n");
        respuesta.append("Objetivo: ").append(objetivo).append("\n");
        respuesta.append("Nivel de Dificultad: ").append(nivel_dificultad).append("\n");
        respuesta.append("Fecha de Creación: ").append(fecha_creacion != null ? dateFormat.format(fecha_creacion) : "No especificada").append("\n");
        respuesta.append("Fecha de Modificación: ").append(fecha_modificacion != null ? dateFormat.format(fecha_modificacion) : "No especificada").append("\n");
        respuesta.append("Versión: ").append(version).append("\n");
        respuesta.append("Duración Total: ").append(duracion).append(" minutos\n");

        // Mostrar el promedio de ratings si existen
        if (ratings != null && !ratings.isEmpty()) {
            respuesta.append("Rating Promedio: ").append(getRating()).append("\n");
        } else {
            respuesta.append("Rating: No hay calificaciones disponibles.\n");
        }

        // Mostrar actividades, si existen
        if (actividades != null && !actividades.isEmpty()) {
            respuesta.append("Actividades:\n");
            for (Actividad actividad : actividades) {
                respuesta.append(" - ID: ").append(actividad.getID_actividad()).append(", Descripción: ").append(actividad.getDescripcion()).append(", Duración: ").append(actividad.getDuracion()).append(" minutos\n");
            }
        } else {
            respuesta.append("Actividades: No hay actividades registradas.\n");
        }

        return respuesta.toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Compara referencias
        if (obj == null || getClass() != obj.getClass()) return false; // Asegura que sean del mismo tipo
        LearningPath that = (LearningPath) obj;
        return id_LP == that.id_LP; // Compara usando el ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_LP); // Genera el hash basado en el ID
    }
}

