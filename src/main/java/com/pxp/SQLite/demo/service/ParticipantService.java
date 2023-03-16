package com.pxp.SQLite.demo.service;


import com.pxp.SQLite.demo.entity.Participant;
import com.pxp.SQLite.demo.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;


    public class CreateParticipantResponse {
        private String message;
        private Integer status;
        public CreateParticipantResponse(String message) {
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

    @Transactional
    public CreateParticipantResponse createParticipant(Participant participant){
        try{
            if (!participantRepository.existsByEmail(participant.getEmail())){
                //null == participantRepository.findMaxId()? 0 : participantRepository.findMaxId() + 1
                participant.setId(null == participantRepository.findMaxId() ? 0 :participantRepository.findMaxId() + 1);
                participantRepository.save(participant);
                return new CreateParticipantResponse("Student record created successfully.");
            }else {
                throw new RuntimeException("Student already exists in the database.");
            }
        }catch (Exception e){
            throw e;
        }
    }

    public List<Participant> readStudents(){
        return participantRepository.findAll();
    }

//    @Transactional
//    public String updateStudent(Student student){
//        if (participantRepository.existsByEmail(student.getEmail())){
//            try {
//                List<Student> students = participantRepository.findByEmail(student.getEmail());
//                students.stream().forEach(s -> {
//                    Student studentToBeUpdate = participantRepository.findById(s.getId()).get();
//                    studentToBeUpdate.setName(student.getName());
//                    studentToBeUpdate.setEmail(student.getEmail());
//                    participantRepository.save(studentToBeUpdate);
//                });
//                return "Student record updated.";
//            }catch (Exception e){
//                throw e;
//            }
//        }else {
//            return "Student does not exists in the database.";
//        }
//    }
}
