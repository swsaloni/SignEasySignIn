package example.com.signeasy.model;

/**
 * Created by Saloni on 5/6/2016.
 */
public class LoginSuccessResponse {

    private int id;
    private String access_token;

    public LoginSuccessResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
