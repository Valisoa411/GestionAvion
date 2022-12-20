package com.example.restservice.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.model.Assurance;
import com.example.restservice.model.Avion;
import com.google.gson.Gson;

@RequestMapping("/assurances")
@RestController
public class AssuranceController {
    Gson parser = new Gson();

    // @GetMapping("/{idAssurance}")
    // public String getAssurance(@PathVariable("idAssurance") int idAssurance) throws Exception{
    //     Assurance assurance = new Assurance();
    //     assurance.setIdAssurance(idAssurance);
    //     assurance = assurance.getAssurance();
    //     return parser.toJson(assurance);
    // }

    // @GetMapping("{month}")
    // public String getAllAssurance(@PathVariable("month") int month) throws Exception{
    //     ArrayList<Assurance> assurances = new Assurance().listerAssuranceExpiredIn(month);//new ArrayList<>();
    //     //assurances.add(new Assurance(1, 1, null, null));
    //     return parser.toJson(assurances);
    // }
    @GetMapping("/expired/{month}")
    public String getExpiredAvion(@PathVariable("month") int month) throws Exception{
        ArrayList<Avion> avions = new Assurance().listerAvionExpiredIn(month);
        //assurances.add(new Assurance(1, 1, null, null));
        return parser.toJson(avions);
    }

    // @GetMapping("/assurances")
    // public String getAllAssurance(@PathVariable("idAssurance") int idAssurance) throws Exception{ 
    //     ArrayList<Assurance> list = new Assurance().listerAssuranceNotExpiredIn(1);
    //     return parser.toJson(list);
    // }

    @PostMapping
    public void createAssurance(@RequestBody Assurance assurance) throws Exception{
        assurance.insertAssurance();

    }

    @PutMapping("/{idAssurance}")
    public void updateAssurance(@PathVariable("idAssurance")int idAssurance,@RequestBody Assurance assurance) throws Exception{
        assurance.setIdAssurance(idAssurance);
        assurance.updateAssurance();
    }
    
    @DeleteMapping("/{idAssurance}")
    public void deleteAssurance(@PathVariable int idAssurance) throws Exception{
        Assurance assurance = new Assurance();
        assurance.setIdAssurance(idAssurance);
        assurance.deleteAssurance();
    }

    


}
