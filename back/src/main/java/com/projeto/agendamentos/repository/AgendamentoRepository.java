package com.projeto.agendamentos.repository;

import com.projeto.agendamentos.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AgendamentoRepository
        extends JpaRepository<Agendamento, Long> {


    @Query(""" 
SELECT COUNT(a) > 0
    FROM Agendamento a
    WHERE a.sala.id = :salaId
      AND a.data = :data
      AND a.horaInicio < :horaFim
      AND a.horaFim > :horaInicio
""")
    boolean existeConflito(
            @Param("salaId") Long salaId,
            @Param("data") LocalDate data,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFim") LocalTime horaFim
    );

}
