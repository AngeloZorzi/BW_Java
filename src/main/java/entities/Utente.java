package entities;

import enumerating.Ruolo;
import jakarta.persistence.*;

@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_utente;

    private String nome;
    private String cognome;
    private String email;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    // Getters & Setters
    public Utente () {}

    public Utente(Long id_utente, String nome, String cognome, String email, Ruolo ruolo) {
        this.id_utente = id_utente;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo = ruolo;
    }

    public Long getId_utente() {
        return id_utente;
    }

    public void setId_utente(Long id_utente) {
        this.id_utente = id_utente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }
}
