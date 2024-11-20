package usuario;

public abstract class Usuario {

    private int id_usuario;
    private String login;
    private String password;


    //Constructor
    public Usuario (int id_usuario, String login, String password){
        
        this.id_usuario= id_usuario;
        this.login= login;
        this. password= password;

    }


    public int getId_usuario() {
        return this.id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
