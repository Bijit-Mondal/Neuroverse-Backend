package com.pxp.SQLite.demo.repository;

import com.pxp.SQLite.demo.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    public boolean existsByEmail(String email);

    public List<Participant> findByEmail(String email);

    @Query("SELECT p FROM Participant p WHERE p.email = :email")
    public Participant findByEmailParticipant(@Param("email") String email);



    @Query("select max(p.id) from Participant p")
    public Integer findMaxId();
}
