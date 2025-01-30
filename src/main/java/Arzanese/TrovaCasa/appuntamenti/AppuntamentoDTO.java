package Arzanese.TrovaCasa.appuntamenti;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppuntamentoDTO {
    private Long id;
    private String dataDisponibilita; // Formato: "yyyy-MM-dd"
    private String oraInizio;         // Formato: "HH:mm"
    private String oraFine;           // Formato: "HH:mm"
    private Boolean prenotato;
    // Identificativo immobile associato
}

