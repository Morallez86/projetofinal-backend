package aor.paj.projetofinalbackend.pojo;

public class ConfirmationRequest {
    private String token;
    private String password;

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


