package example.project.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserDTO {
    //Needed for JWT
    public final static String EMAIL = "email";
    public final static String FIRST_NAME = "first name";
    public final static String PASSWORD = "password";
    public final static String ROLE = "role";
    public final static String SURNAME = "surname";
    public final static String USERNAME = "username";
    public final static String ID = "id";

    private String id;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String surname;
    private String role;
}
