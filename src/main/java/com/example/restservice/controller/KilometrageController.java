package com.example.restservice.controller;

import com.example.restservice.Response.Response;
import com.example.restservice.Response.Success;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.example.restservice.model.Kilometrage;
import com.example.restservice.generic.*;
import com.google.gson.*;

@RestController
@CrossOrigin
public class KilometrageController {
    Gson g = new Gson();
    
    @GetMapping("/kilometrages")
    public String allKilometrage() throws Exception{
        Response res = new Response();
        res.addAttribute("listkilometrage", new Kilometrage().listerKilometrage());
	    return g.toJson(res);
    }

    //Mbola mila amboarina
    @PostMapping("/avions/{idv}/kilometrages")
    public void save(@PathVariable int idv,@RequestBody Kilometrage kilometrage) throws Exception{
        kilometrage.save();
    }

    @PutMapping("/avions/{idv}/kilometrages/{idk}")
    public void update(@PathVariable int idv,@PathVariable int idk,@RequestParam(value="date") String date,@RequestParam(value="debut") double debut,@RequestParam(value="fin") double fin) throws Exception {
        Connection con = Connexion.getConnexion();
        // GenericDAO.update(new Kilometrage(idk,idv,new Date(date.replace("-","/")),debut,fin), con);
        GenericDAO.update(new Kilometrage(idk,idv,Date.valueOf(date),debut,fin), con);
        // Response r = new Response();
        // r.setData(new Success("Modification reussi"));
        // String res = g.toJson(r);
        // con.close();
        // return res;
    }

    @DeleteMapping("/avions/{idv}/kilometrages/{idk}")
    public void delete(@PathVariable int idv,@PathVariable int idk) throws Exception {
        Connection con = Connexion.getConnexion();
        Kilometrage km = new Kilometrage();
        km.setIdKilometrage(idk);
        GenericDAO.delete(km, con);
    }
}
