package com.example.restservice.controller;

import com.example.restservice.Response.Error;
import com.example.restservice.Response.Response;
import com.example.restservice.Response.Success;
import com.example.restservice.generic.GenericDAO;
import com.example.restservice.model.Kilometrage;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.example.restservice.model.Avion;
import com.example.restservice.token.Token;
import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@CrossOrigin
@RequestMapping("/avions")
public class AvionController {
    Gson g = new Gson();
        
    @GetMapping
    public String allAvion() throws Exception{
        Response res = new Response();
        ArrayList<Avion> list = new Avion().listerAvion();
        res.setData(new Success("Liste des avions"));
        res.addAttribute("listavion", list);
        return g.toJson(res);
    }
    
    @GetMapping("/{idAvion}")
    public String getAvion(@PathVariable("idAvion") int id, @RequestParam String token) throws Exception {
        Response res = new Response();
        try {
            Token.check(token);
        } catch (ExpiredJwtException e) {
            res.setError(new Error(500, "Session Expired"));
            return g.toJson(res);
        } catch (Exception e) {
            res.setError(new Error(500, "Token Error; "+e.getMessage()));
            return g.toJson(res);
        }
        Avion vcl = new Avion();
        vcl.setIdAvion(id);
        vcl = vcl.getAvion();
        res.setData(new Success("Avion"));
        res.addAttribute("vcl", vcl);
        return g.toJson(res);
    }

    @PostMapping("/avion")
	public void createAvion(@RequestBody Avion v) throws Exception{
        v.insertAvion();
    }
    
    @PutMapping("/{idAvion}")
    public void updateAvion(@PathVariable("idAvion") int idAvion, @RequestBody Avion v) throws Exception{
        v.setIdAvion(idAvion);
        v.updateAvion();
    }

    @DeleteMapping("/{idAvion}")
	public void deleteAvion(@PathVariable("idAvion") int idAvion) throws Exception{
        Avion v = new Avion();
        v.setIdAvion(idAvion);
        v.deleteAvion();
    }
}