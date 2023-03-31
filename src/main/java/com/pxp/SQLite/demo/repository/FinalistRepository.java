package com.pxp.SQLite.demo.repository;

import com.pxp.SQLite.demo.entity.Finalist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalistRepository extends JpaRepository<Finalist, Integer> {

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Finalist f WHERE f.participant.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT f FROM Finalist f WHERE f.id = :id")
    public Finalist getFinalistById(@Param("id") Integer id);

    List<Finalist> findByParticipantEmail(String email);

    @Query("select max(p.id) from Finalist p")
    public Integer findMaxId();

}