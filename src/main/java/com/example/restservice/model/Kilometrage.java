package com.example.restservice.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import com.example.restservice.generic.*;

@ClassAnotation(table = "kilometrage")
public class Kilometrage{
    @Attr(isPrimary = true)
    int idKilometrage;

    @Attr
    int avionId;

    @Attr
    Date date;

    @Attr
    double debut;

    @Attr
    double fin;


    public Kilometrage(int idKilometrage, int avionId, Date date, double debut, double fin) {
        this.idKilometrage = idKilometrage;
        this.avionId = avionId;
        this.date = date;
        this.debut = debut;
        this.fin = fin;
    }


    public Kilometrage(int avionId, Date date, double debut, double fin) {
        this.avionId = avionId;
        this.date = date;
        this.debut = debut;
        this.fin = fin;
    }

    public Kilometrage() {
    }

    public int getIdKilometrage() {
        return idKilometrage;
    }

    public void setIdKilometrage(int idKilometrage) {
        this.idKilometrage = idKilometrage;
    }

    public int getAvionId() {
        return avionId;
    }

    public void setAvionId(int avionId) {
        this.avionId = avionId;
    }

    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public double getDebut() {
        return debut;
    }


    public void setDebut(double debut) {
        this.debut = debut;
    }


    public double getFin() {
        return fin;
    }


    public void setFin(double fin) {
        this.fin = fin;
    }

    public ArrayList<Kilometrage> listerKilometrage() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Kilometrage WHERE idKilometrage NOT IN (SELECT kilometrageId FROM KilometrageSupprimer)";
        ArrayList<Kilometrage> liste = new ArrayList<Kilometrage>();
        try{
            liste = (ArrayList<Kilometrage>)GenericDAO.findBySql(this,requete,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return liste;
    }

    public void save() throws Exception{
        Connection connect = new Connexion().getConnexion();
        try {
            GenericDAO.save(this, connect);
        } catch (Exception e) {
            throw e;
        }
        finally{
            if(connect!=null)connect.close();
        }
    }

}
