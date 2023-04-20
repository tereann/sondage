package fr.simplon.sondage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name="sondages")
public class Sondage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank //spring validation READ ABOUT IT
    @Size(min = 3, max = 120)
    private String description;

    @Size(max = 120)
    private String question;

    @NotNull
    @Column(name = "date_creation", updatable = false)
    private LocalDate date_creation = LocalDate.now();

    @Future
    @NotNull
    @Column(name = "end_date")
    private LocalDate end_date;

    @NotBlank
    @Size(max = 64)
    private String nom_creator;

    public Sondage() {
    }

    public Sondage(String description, String question, LocalDate date_creation, LocalDate end_date, String nom_creator) {
        this.description = description;
        this.question = question;
        this.date_creation = date_creation;
        this.end_date = end_date;
        this.nom_creator = nom_creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public LocalDate getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public String getNom_creator() {
        return nom_creator;
    }

    public void setNom_creator(String nom_creator) {
        this.nom_creator = nom_creator;
    }
}

