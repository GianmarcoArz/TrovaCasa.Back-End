package Arzanese.TrovaCasa.appuntamenti;

import Arzanese.TrovaCasa.auth.AppUser;
import Arzanese.TrovaCasa.auth.AppUserService;
import Arzanese.TrovaCasa.immobili.Immobile;
import Arzanese.TrovaCasa.immobili.ImmobileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class AppuntamentoService {

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    @Autowired
    private ImmobileRepository immobileRepository;

    @Autowired
    private AppUserService appUserService;


    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Creare una nuova disponibilità
    public Appuntamento creaDisponibilita(Long immobileId, AppuntamentoDTO appuntamentoDTO) {
        // Recupera l'utente autenticato
        AppUser utenteAutenticato = appUserService.getUtenteAutenticato();

        // Recupera l'immobile associato all'ID
        Immobile immobile = immobileRepository.findById(immobileId)
                .orElseThrow(() -> new EntityNotFoundException("Immobile non trovato con ID: " + immobileId));

        // Verifica che l'utente autenticato sia il proprietario dell'immobile
        if (!immobile.getUser().getId().equals(utenteAutenticato.getId())) {
            throw new SecurityException("Non sei autorizzato a creare disponibilità per questo immobile.");
        }

        // Conversione delle stringhe in LocalDate e LocalTime
        LocalDate dataDisponibilita = parseDate(appuntamentoDTO.getDataDisponibilita());
        LocalTime oraInizio = parseTime(appuntamentoDTO.getOraInizio());
        LocalTime oraFine = parseTime(appuntamentoDTO.getOraFine());

        if (oraInizio.isAfter(oraFine) || oraInizio.equals(oraFine)) {
            throw new IllegalArgumentException("L'ora di inizio deve essere prima dell'ora di fine!");
        }

        // Creazione oggetto Appuntamento
        Appuntamento appuntamento = new Appuntamento();
        appuntamento.setDataDisponibilita(String.valueOf(dataDisponibilita));
        appuntamento.setOraInizio(String.valueOf(oraInizio));
        appuntamento.setOraFine(String.valueOf(oraFine));
        appuntamento.setPrenotato(false);
        appuntamento.setImmobile(immobile);
        appuntamento.setCreatoreAnnuncio(utenteAutenticato);

        return appuntamentoRepository.save(appuntamento);
    }



    // Prenotare un appuntamento associando l'utente autenticato
    public Appuntamento prenotaAppuntamento(Long appuntamentoId) {
        // Recupera l'appuntamento dall'id
        Appuntamento appuntamento = appuntamentoRepository.findById(appuntamentoId)
                .orElseThrow(() -> new EntityNotFoundException("Appuntamento non trovato con ID: " + appuntamentoId));

        // Controlla se l'appuntamento è già prenotato
        if (Boolean.TRUE.equals(appuntamento.getPrenotato())) {
            throw new IllegalArgumentException("Appuntamento già prenotato!");
        }

        // Recupera l'utente autenticato
        AppUser utenteAutenticato = appUserService.getUtenteAutenticato();

        // Segna l'appuntamento come prenotato e associa l'utente prenotante
        appuntamento.setPrenotato(true);
        appuntamento.setUtentePrenotato(utenteAutenticato);

        return appuntamentoRepository.save(appuntamento);
    }

    // Aggiornare una disponibilità
    public Appuntamento aggiornaDisponibilita(Long appuntamentoId, AppuntamentoDTO appuntamentoDTO) {
        // Recupera l'appuntamento
        Appuntamento appuntamento = appuntamentoRepository.findById(appuntamentoId)
                .orElseThrow(() -> new EntityNotFoundException("Appuntamento non trovato con ID: " + appuntamentoId));

        // Recupera l'utente autenticato
        AppUser utenteAutenticato = appUserService.getUtenteAutenticato();

        // Verifica che l'utente autenticato sia il creatore dell'appuntamento
        if (!appuntamento.getCreatoreAnnuncio().getId().equals(utenteAutenticato.getId())) {
            throw new SecurityException("Non sei autorizzato a modificare questa disponibilità.");
        }

        // Aggiorna i campi dell'appuntamento
        appuntamento.setDataDisponibilita(appuntamentoDTO.getDataDisponibilita());
        appuntamento.setOraInizio(appuntamentoDTO.getOraInizio());
        appuntamento.setOraFine(appuntamentoDTO.getOraFine());

        // Verifica validità degli orari
        LocalTime oraInizio = parseTime(appuntamentoDTO.getOraInizio());
        LocalTime oraFine = parseTime(appuntamentoDTO.getOraFine());
        if (oraInizio.isAfter(oraFine) || oraInizio.equals(oraFine)) {
            throw new IllegalArgumentException("L'ora di inizio deve essere prima dell'ora di fine!");
        }

        return appuntamentoRepository.save(appuntamento);
    }

    // Eliminare una disponibilità
    public void eliminaDisponibilita(Long appuntamentoId) {
        // Recupera l'appuntamento
        Appuntamento appuntamento = appuntamentoRepository.findById(appuntamentoId)
                .orElseThrow(() -> new EntityNotFoundException("Appuntamento non trovato con ID: " + appuntamentoId));

        // Recupera l'utente autenticato
        AppUser utenteAutenticato = appUserService.getUtenteAutenticato();

        // Verifica che l'utente autenticato sia il creatore dell'appuntamento
        if (!appuntamento.getCreatoreAnnuncio().getId().equals(utenteAutenticato.getId())) {
            throw new SecurityException("Non sei autorizzato a eliminare questa disponibilità.");
        }

        appuntamentoRepository.deleteById(appuntamentoId);
    }



    public List<Appuntamento> getAppuntamentiPrenotatiCreatore() {
        // Recupera l'utente autenticato
        AppUser utenteAutenticato = appUserService.getUtenteAutenticato();

        // Recupera gli appuntamenti prenotati dove l'utente è il creatore o l'utente prenotato
        List<Appuntamento> appuntamentiCreati = appuntamentoRepository.findByCreatoreAnnuncioAndPrenotatoTrue(utenteAutenticato);
        List<Appuntamento> appuntamentiPrenotati = appuntamentoRepository.findByUtentePrenotato(utenteAutenticato);

        // Unisce le due liste
        appuntamentiCreati.addAll(appuntamentiPrenotati);

        return appuntamentiCreati;
    }

    // Metodi di supporto per conversione
    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato data non valido! Usa 'yyyy-MM-dd'.");
        }
    }

    private LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato ora non valido! Usa 'HH:mm'.");
        }
    }



}
