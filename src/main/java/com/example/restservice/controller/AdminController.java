package com.example.restservice.controller;
import com.example.restservice.Response.Response;
import com.example.restservice.Response.Success;
import com.example.restservice.Response.Error;
import java.util.concurrent.atomic.AtomicLong;
import com.example.restservice.model.Admin;
import com.example.restservice.model.Avion;
import com.example.restservice.generic.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;
import java.util.*;
import com.google.gson.*;
import com.example.restservice.token.*;

@RestController
@CrossOrigin
public class AdminController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

    @PostMapping("/loginAdmin")
    public String admin(@RequestParam(value="login") String login,@RequestParam(value="mdp") String mdp) throws Exception{
        Connection con=Connexion.getConnexion();
        ArrayList<Admin> listAdmin = new ArrayList<Admin>();
        Gson g = new Gson();
        String res = "";
        Response r = new Response();
        try {
            listAdmin = GenericDAO.findBySql(new Admin(),"Select login,mdp from admin where login='"+login+"' and mdp='"+mdp+"'",con);
            if(!listAdmin.isEmpty()){
                Admin admin = listAdmin.get(0);
                Token token = new Token(admin.getIdAdmin());
                r.setData(new Success("Authentification reussi"));
                r.addAttribute("token", token.getToken());
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

}