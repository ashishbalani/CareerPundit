package inducesmile.com.CareerPundit.LoginRegister;

/**
 * Created by test on 11-03-2016.
 */
public class User {
    public String name;
    String email;
    String password;
    String dob;


    public User(String name, String email, String password, String dob) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this("", email, password, "");
    }
}
