package com.example.restservice.model;

import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import com.example.restservice.generic.*;

@ClassAnotation(table="Avion")
public class Avion{
    @Attr(isPrimary=true)
	int idAvion;
    @Attr
	String marque;
    @Attr
    String immatriculation;

    double kilometrage;
    Date assurance_echeance;

	public int getIdAvion() {
		return idAvion;
	}

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

	public String getMarque() {
		return marque;
	}

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getImmatriculation() {
		return immatriculation;
	}

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public double getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(double kilometrage) {
        this.kilometrage = kilometrage;
    }

    public Date getAssurance_echeance() {
        return assurance_echeance;
    }

    public void setAssurance_echeance(Date assurance_echeance) {
        this.assurance_echeance = assurance_echeance;
    }

    public Avion(){}

    public Avion(int idAvion, String marque, String immatriculation){
        this.idAvion = idAvion;
        this.marque = marque;
        this.immatriculation = immatriculation;
    }

    public void getKilometrage(Connection con) throws Exception {
        Statement stm = con.createStatement();
        ResultSet res = stm.executeQuery("SELECT MAX FROM AVION_KILOMETRAGE WHERE AVIONID="+idAvion);
        res.next();
        setKilometrage(res.getDouble("max"));
    }

    public void getEcheance(Connection con) throws Exception {
        Statement stm = con.createStatement();
        ResultSet res = stm.executeQuery("SELECT MAX FROM AVION_ASSURANCE WHERE AVIONID="+idAvion);
        res.next();
        setAssurance_echeance(res.getDate("max"));
    }

    public Avion getAvion() throws Exception{
        Connection connect = new Connexion().getConnexion();
        ArrayList<Avion> liste = new ArrayList<Avion>();
        try{
            getKilometrage(connect);
            getEcheance(connect);
            return (Avion)GenericDAO.get(this,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
    }

    public ArrayList<Avion> listerAvion() throws Exception{
        Connection connect = new Connexion().getConnexion();
        String requete = "SELECT * FROM Avion WHERE idAvion NOT IN (SELECT AvionId FROM AvionSupprimer)";
        ArrayList<Avion> liste = new ArrayList<Avion>();
        try{
            liste = (ArrayList<Avion>)GenericDAO.findBySql(this,requete,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        System.out.println("listerAvion size : "+liste.size());
        return liste;
    }

    public void insertAvion() throws Exception{
        Connection connect = new Connexion().getConnexion();
	    try{
            GenericDAO.save(this,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
    }

    public void updateAvion() throws Exception{
        Connection connect = new Connexion().getConnexion();
	    try{
            GenericDAO.update(this,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
    }
    
    public void deleteAvion() throws Exception{
        Connection connect = new Connexion().getConnexion();
	    try{
            GenericDAO.delete(this,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
    }

}