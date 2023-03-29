package com.pxp.SQLite.demo.controller;

import com.pxp.SQLite.demo.entity.Participant;
import com.pxp.SQLite.demo.service.FinalistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FinalistController {
    @Autowired
    private FinalistService finalistService;


    //private ParticipantService participantService;



    @GetMapping("/non-finalist/show")
    public String showNonFinalists(Model model) {
        List<Participant> nonFinalists = finalistService.getNonFinalists();
        model.addAttribute("nonFinalists", nonFinalists);
        //model.addAttribute("finalist", new Participant());
        return "non-finalists";
    }

    @PostMapping("/addFinalist")
    public String addFinalist(@RequestParam("email") String email) {
        Participant participant = finalistService.getParticipantByEmail(email);

        if (participant == null) {
            return "redirect:/non-finalist/show";
        }
        finalistService.addFinalist(participant);
        return "redirect:/non-finalist/show";
    }


}
