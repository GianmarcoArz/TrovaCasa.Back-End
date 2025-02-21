package Arzanese.TrovaCasa.auth;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String nome;
    private String cognome;
    private String telefono;
    private LocalDateTime dataRegistrazione;


}
