package sample;

public class Admin extends User{
    public Admin(int id, String firstName, String lastName, String email, String username, String password) {
        super(id, firstName, lastName, email, username, password);
    }

    public Admin() {
        super();
    }
}
