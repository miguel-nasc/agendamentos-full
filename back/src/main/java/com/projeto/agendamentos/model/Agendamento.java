package com.projeto.agendamentos.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table (name= "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String reservadoPor;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    public Agendamento() {
    }

    public Agendamento(Long id, String titulo, String reservadoPor, LocalDate data,
                       LocalTime horaInicio, LocalTime horaFim, Sala sala) {
        this.id = id;
        this.titulo = titulo;
        this.reservadoPor = reservadoPor;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.sala = sala;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getReservadoPor() {
        return reservadoPor;
    }

    public void setReservadoPor(String reservadoPor) {
        this.reservadoPor = reservadoPor;
    }


    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Agendamento that = (Agendamento) o;
        return Objects.equals(id, that.id) && Objects.equals(titulo, that.titulo) && Objects.equals(reservadoPor, that.reservadoPor) && Objects.equals(data, that.data) && Objects.equals(horaInicio, that.horaInicio) && Objects.equals(horaFim, that.horaFim) && Objects.equals(sala, that.sala);
    }
    public void setReservado_por(String reservadoPor) {
        this.reservadoPor = reservadoPor;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, reservadoPor, data, horaInicio, horaFim, sala);
    }

}