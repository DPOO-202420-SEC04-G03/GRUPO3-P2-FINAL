package actividad;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Atributos
public abstract class Actividad {        

    private int ID_actividad;
    private String descripcion;
    private String objetivo;
    private String nivel_dificultad ;  // Principiante/Intermedio/Avanzado
    private ArrayList<String> resenas;
    private String tipo_actividad;
    private List<Actividad> actividades_sugeridas;
    private boolean prerrequisitos;
    private Date fecha_limite;
    private int duracion;
    private ArrayList<Double> calificaciones = new ArrayList<>();


    //Constructor

    public Actividad(int ID_actividad, String descripcion, String objetivo, String nivel_dificultad , ArrayList<String> resena, String tipo_actividad,
                List<Actividad> actividades_sugeridas, boolean prerrequisitos, Date fecha_limite, int duracion) {
        this.ID_actividad = ID_actividad;
        this.descripcion = descripcion;
        this.objetivo = objetivo;
        this.nivel_dificultad  = nivel_dificultad ;
        this.resenas = resena;
        this.tipo_actividad = tipo_actividad;
        this.actividades_sugeridas = actividades_sugeridas;
        this.prerrequisitos = prerrequisitos;
        this.fecha_limite = fecha_limite;
        this.duracion= duracion;
    }

    //setters y getters






    public int getID_actividad() {
        return this.ID_actividad;
    }

    public void setID_actividad(int ID_actividad) {
        this.ID_actividad = ID_actividad;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }

    public String getObjetivo() {
        return this.objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNivel_dificultad() {
        return this.nivel_dificultad;
    }
    
    public void setNivel_dificultad(String nivel_dificultad) {
        this.nivel_dificultad = nivel_dificultad;
    }
    

    public ArrayList<String> getResena() {
        return this.resenas != null ? new ArrayList<>(this.resenas) : new ArrayList<>();
    }

    

    public void setResena(ArrayList<String> resena) {
        this.resenas = resena;
    }
    

    public String getTipo_actividad() {
        return this.tipo_actividad;
    }

    public void setTipo_actividad(String tipo_actividad) {
        this.tipo_actividad = tipo_actividad;
    }

    public List<Actividad> getActividades_sugeridas() {
        return this.actividades_sugeridas;
    }

    public void setActividades_sugeridas(List<Actividad> actividades_sugeridas) {
        this.actividades_sugeridas = actividades_sugeridas;
    }

    public boolean isPrerrequisitos() {
        return this.prerrequisitos;
    }

    public boolean getPrerrequisitos() {
        return this.prerrequisitos;
    }

    public void setPrerrequisitos(boolean prerrequisitos) {
        this.prerrequisitos = prerrequisitos;
    }

    public Date getFecha_limite() {
        return this.fecha_limite;
    }

    public void setFecha_limite(Date fecha_limite) {
        this.fecha_limite = fecha_limite;
    }
    

    public int getDuracion() {
        return this.duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void agregarResena(String resena){

        this.resenas.add(resena);
    }

    public void agregarCalificacion(double calificacion) {
        this.calificaciones.add(calificacion);
    }



    public String print() {
        StringBuilder respuesta = new StringBuilder();
        respuesta.append("ID Actividad: ").append(ID_actividad).append("\n");
        respuesta.append("Descripción: ").append(descripcion).append("\n");
        respuesta.append("Objetivo: ").append(objetivo).append("\n");
        respuesta.append("Nivel de Dificultad: ").append(nivel_dificultad).append("\n");
        respuesta.append("Tipo de Actividad: ").append(tipo_actividad).append("\n");
        respuesta.append("Prerrequisitos: ").append(prerrequisitos ? "Sí" : "No").append("\n");
        respuesta.append("Fecha Límite: ").append(fecha_limite != null ? fecha_limite.toString() : "Sin fecha límite").append("\n");
        respuesta.append("Duración: ").append(duracion).append(" minutos\n");
    
        // Añadir las reseñas, si existen
        if (resenas != null && !resenas.isEmpty()) {
            respuesta.append("Reseñas:\n");
            for (String resena : resenas) {
                respuesta.append(" - ").append(resena).append("\n");
            }
        } else {
            respuesta.append("Reseñas: No hay reseñas disponibles.\n");
        }
    
        // Añadir actividades sugeridas, si existen
        if (actividades_sugeridas != null && !actividades_sugeridas.isEmpty()) {
            respuesta.append("Actividades Sugeridas:\n");
            for (Actividad actividad : actividades_sugeridas) {
                respuesta.append(" - ").append(actividad.ID_actividad).append(" - ").append(actividad.descripcion).append("\n");
            }
        } else {
            respuesta.append("Actividades Sugeridas: No hay actividades sugeridas.\n");
        }
    
        return respuesta.toString();
    }
    

}
