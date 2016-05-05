package example.com.signeasy.model;

/**
 * Created by Saloni on 5/6/2016.
 */
public class LoginErrorResponse {
    private String error_code;
    private String message;

    public LoginErrorResponse() {
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
