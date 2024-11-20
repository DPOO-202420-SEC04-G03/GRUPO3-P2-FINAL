package persistencia;

import usuario.Usuario;
import java.util.HashMap;

public class Autenticador {
    private HashMap<String, Usuario> usuariosPorLogin;

    // Constructor que recibe el mapa de usuarios y lo organiza por login
    public Autenticador(HashMap<Integer, Usuario> usuarios) {
        this.usuariosPorLogin = new HashMap<>();
        for (Usuario usuario : usuarios.values()) {
            usuariosPorLogin.put(usuario.getLogin(), usuario);
        }
    }

    /**
     * Método para autenticar al usuario.
     * @param login El login del usuario.
     * @param password La contraseña del usuario.
     * @return El objeto Usuario autenticado o null si la autenticación falla.
     */
    public Usuario autenticar(String login, String password) {
        Usuario usuario = usuariosPorLogin.get(login);

        if (usuario == null) {
            System.out.println("Error: Login de usuario no encontrado.");  // Mensaje de depuración
            return null;
        }

        if (!usuario.getPassword().equals(password)) {
            System.out.println("Error: Contraseña incorrecta.");  // Mensaje de depuración
            return null;
        }

        System.out.println("Usuario autenticado: " + usuario.getLogin());  // Mensaje de depuración
        return usuario;
    }
}