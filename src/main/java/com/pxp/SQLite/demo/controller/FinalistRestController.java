package com.pxp.SQLite.demo.controller;

import com.pxp.SQLite.demo.entity.Finalist;
import com.pxp.SQLite.demo.service.FinalistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/finalist")
public class FinalistRestController {

    @Autowired
    private FinalistService finalistService;

    @GetMapping("/show")
    @CrossOrigin(origins = "*")
    public List<Finalist> getFinalists() {
        return finalistService.getFinalists();
    }

    @PostMapping("/hackerRankID/{id}/{email}")
    @CrossOrigin(origins = "*")
    public FinalistService.addFinalistResponse hackerRankID(@PathVariable Integer id,@PathVariable String email,@RequestBody String HackerRankID) {
        FinalistService.addFinalistResponse response;
        try {
            response = finalistService.addHackerRankID(id, email, HackerRankID);
        } catch (Exception e) {
            throw e;
        }
        return response;
    }
}