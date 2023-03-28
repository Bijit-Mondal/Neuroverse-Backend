package com.pxp.SQLite.demo.controller;

import com.pxp.SQLite.demo.entity.Participant;
import com.pxp.SQLite.demo.genarator.ExcelGenarator;
import com.pxp.SQLite.demo.service.MailService;
import com.pxp.SQLite.demo.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/participant")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private MailService notificationService;

    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public ParticipantService.CreateParticipantResponse createParticipant(@RequestBody Participant participant){
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = LocalDate.parse("2023-03-25"); // change this to the date you want to restrict access until
        if (currentDate.isAfter(endDate)) {
            throw new RuntimeException("Registration has been close");
        }
        ParticipantService.CreateParticipantResponse response =  participantService.createParticipant(participant);
        try {
            notificationService.sendNotificationEmail(participant);
        } catch (MailException mailException) {
            System.out.println(mailException);
        } catch (MessagingException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return response;
    }



    @GetMapping("/excel-data")
    public void excelData(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=student" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List <Participant> listOfStudents = participantService.readStudents();
        ExcelGenarator generator = new ExcelGenarator(listOfStudents);
        generator.generateExcelFile(response);
    }
}
