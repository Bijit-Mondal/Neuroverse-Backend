package com.pxp.SQLite.demo.service;

import com.pxp.SQLite.demo.entity.Finalist;
import com.pxp.SQLite.demo.entity.Participant;
import com.pxp.SQLite.demo.repository.FinalistRepository;
import com.pxp.SQLite.demo.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinalistService {
    @Autowired
    private FinalistRepository finalistRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private MailService notificationService;


    public class addFinalistResponse{
        private String message;
        private Integer status;
        public addFinalistResponse(String message) {
            this.message = message;
            this.status = 200;
        }
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

    public addFinalistResponse addFinalist(Participant participant) {
        try {
            if(!finalistRepository.existsByEmail(participant.getEmail())) {
                Finalist finalist = new Finalist();
                finalist.setParticipant(participant);
                finalistRepository.save(finalist);
                notificationService.sendFinalistEmail(finalist);
                return new addFinalistResponse(participant.getFirstName()+" is now in finals");
            } else {
                throw new RuntimeException(participant.getFirstName()+" already in finals");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public addFinalistResponse addHackerRankID(Integer Id,String email, String hackerRankId) {
        try {
            Finalist finalist = finalistRepository.getFinalistById(Id);
            if (finalist != null && finalist.getParticipant().getEmail().equals(email)) {
                finalist.setHackerRankID(hackerRankId);
                finalistRepository.save(finalist);
                return new addFinalistResponse("Success HackerRank ID added successfully");
            } else {
                throw new RuntimeException("Error Invalid finalist or email");
            }
        } catch (Exception e) {
            throw e;
        }
    }



    public List<Participant> getNonFinalists() {
        List<Participant> participants = participantRepository.findAll();
        List<Participant> nonFinalists = new ArrayList<>();

        for (Participant participant : participants) {
            if (!finalistRepository.existsByEmail(participant.getEmail())) {
                nonFinalists.add(participant);
            }
        }

        return nonFinalists;
    }


    public List<Finalist> getFinalists() {
        return finalistRepository.findAll();
    }

    public Participant getParticipantByEmail(String email) {
        return participantRepository.findByEmailParticipant(email);
    }

}
