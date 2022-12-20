package com.example.restservice.controller;
import com.example.restservice.Response.Response;
import com.example.restservice.Response.Success;
import com.example.restservice.Response.Error;
import java.util.concurrent.atomic.AtomicLong;
import com.example.restservice.model.Admin;
import com.example.restservice.model.Utilisateur;
import com.example.restservice.model.Avion;
import com.example.restservice.token.Token;
import com.example.restservice.generic.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;
import java.util.*;
import com.google.gson.*;

import io.jsonwebtoken.ExpiredJwtException;
@RestController
@CrossOrigin
public class UtilisateurController {
    Gson g = new Gson();
    
    @PostMapping("/login")
    public String utilisateur(@RequestParam(value="login") String login,@RequestParam(value="mdp") String mdp) throws Exception{
        Connection con=Connexion.getConnexion();
        ArrayList<Utilisateur> listUtilisateur = new ArrayList<Utilisateur>();
        Gson g = new Gson();
        String res = "";
        Response r = new Response();
        try {
            listUtilisateur = GenericDAO.findBySql(new Utilisateur(),"Select * from utilisateur where login='"+login+"' and mdp='"+mdp+"'",con);
            if(!listUtilisateur.isEmpty()){
                Utilisateur user = listUtilisateur.get(0);
                Token token = new Token(user.getIdUtilisateur());
                GenericDAO.save(token, con);
                r.setData(new Success("Login Success"));
                r.addAttribute("token", "bearer "+token.getToken());
            }
            else{
                r.setError(new Error(1,"Authentification echouee"));
            }
            res = g.toJson(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(con!=null)con.close();
        }
        return res;
    }

    @GetMapping("/logout")
    public String deconnexion(@RequestParam String token) throws Exception {
        Response res = new Response();
        try (Connection con = Connexion.getConnexion()) {
            Token t = Token.check(token);
            t.kill(con);
            res.setData(new Success("Deconnexion reussi"));
            return g.toJson(res);
        } catch (ExpiredJwtException e) {
            res.setData(new Success("Deconnexion reussi"));
            return g.toJson(res);
        } catch (Exception e) {
            throw e;
        }
    }
}