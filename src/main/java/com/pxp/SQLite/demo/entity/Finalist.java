package com.pxp.SQLite.demo.entity;

import javax.persistence.*;

@Entity
public class Finalist {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "participant_id", referencedColumnName = "id")
    private Participant participant;

    private String hackerRankId;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public String getHackerRankId() {
        return hackerRankId;
    }

    public void setHackerRankID(String hackerRankId) {
        this.hackerRankId = hackerRankId;
    }

    @Override
    public String toString() {
        return "Finalist{" +
                "id=" + id +
                ", participant=" + participant +
                ", hackerRankId='" + hackerRankId + '\'' +
                '}';
    }
}
