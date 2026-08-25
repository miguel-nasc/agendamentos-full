package com.projeto.agendamentos.repository;

import com.projeto.agendamentos.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository
        extends JpaRepository<Sala, Long> {

        @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Sala s WHERE s.nome = :nome")        
        public boolean existsByNome(@Param(":nome") String nome);

        


}
