package com.example.restservice.model;

import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.restservice.generic.Attr;
import com.example.restservice.generic.ClassAnotation;
import com.example.restservice.generic.Connexion;
import com.example.restservice.generic.GenericDAO;

@ClassAnotation(table = "assurance")
public class Assurance {
    @Attr(isPrimary = true)
    int idAssurance;

    @Attr
    int avionId;

    @Attr
    Date date_renouvellement;

    @Attr
    Date date_echeance;

    Avion avion;

    public Assurance() {
    }

    public Assurance(int avionId, Date date_renouvellement, Date date_echeance) {
        this.avionId = avionId;
        this.date_renouvellement = date_renouvellement;
        this.date_echeance = date_echeance;
    }

    public Assurance(int idAssurance, int avionId, Date date_renouvellement, Date date_echeance) {
        this.idAssurance = idAssurance;
        this.avionId = avionId;
        this.date_renouvellement = date_renouvellement;
        this.date_echeance = date_echeance;
    }

    public int getIdAssurance() {
        return idAssurance;
    }

    public int getAvionId() {
        return avionId;
    }

    public Date getDate_renouvellement() {
        return date_renouvellement;
    }

    public Date getDate_echeance() {
        return date_echeance;
    }

    

    public void setIdAssurance(int idAssurance) {
        this.idAssurance = idAssurance;
    }

    public void setAvionId(int avionId) {
        this.avionId = avionId;
    }

    public void setDate_renouvellement(Date date_renouvellement) {
        this.date_renouvellement = date_renouvellement;
    }

    public void setDate_echeance(Date date_echeance) {
        this.date_echeance = date_echeance;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Avion getAvion() throws Exception {
        if(this.avion!=null) return avion;
        String sql = "select * from avion where idavion="+this.getAvionId();
        //Connec
        try {
            Connection connection = Connexion.getConnexion();
            ArrayList<Avion> avions = GenericDAO.findBySql(new Avion(),sql, connection);
            if(avions.isEmpty())throw new Exception("There is no avion matched with the id "+this.getIdAssurance());
            return avions.get(0);
        } catch (Exception e) {
            throw e;
        }
       
    }

   
    public void insertAssurance() throws Exception{
        Connection connect = Connexion.getConnexion();
	    try{
            ///System.out.println("id: "+this.getIdAssurance());
            GenericDAO.save(this,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
    }

    public Assurance getAssurance() throws Exception{
        Connection connect =Connexion.getConnexion();
        try{
            return (Assurance)GenericDAO.get(this,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
    }

    public ArrayList<Assurance> listerAssurance() throws Exception{
        Connection connect = Connexion.getConnexion();
        String sql = "SELECT * FROM Assurance WHERE idAssurance NOT IN (SELECT assuranceid FROM AssuranceSupprimer)";
        ArrayList<Assurance> liste = new ArrayList<>();
        try{
            liste = (ArrayList<Assurance>)GenericDAO.findBySql(this,sql,connect);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return liste;
    }

    public ArrayList<Assurance> listerAssuranceExpiredIn(int month) throws Exception{
        Connection connect = Connexion.getConnexion();
        String sql = "SELECT * FROM v_assurance";
        ArrayList<Assurance> listeAll;
        ArrayList<Assurance> newList;
        try{
            listeAll = (ArrayList<Assurance>)GenericDAO.findBySql(this,sql,connect);//(ArrayList<Assurance>)GenericDAO.findBySql(this,requete,connect);
            newList = new ArrayList<>();
            for (Assurance assurance : listeAll) {
                if(assurance.isExpiredIn(month)){
                    newList.add(assurance);
                }
            }
        }
        catch(Exception e){
            throw e;
        }
        finally{
            connect.close();
        }
        return newList;
    }

    public ArrayList<Avion> listerAvionExpiredIn(int month) throws Exception{
        try {
            ArrayList<Assurance> assurancesExpired = this.listerAssuranceExpiredIn(month);
            ArrayList<Avion> avionExpired = new ArrayList<>();
            for (Assurance assurance : assurancesExpired) {
                avionExpired.add(assurance.getAvion());   
            }
            return avionExpired;
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateAssurance() throws Exception{
      
        Connection connect = Connexion.getConnexion();
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
    
    public void deleteAssurance() throws Exception{
        Connection connect = Connexion.getConnexion();
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

    //frequence par mois
    public boolean isExpiredIn(int frequence){
        System.out.println("test expiration");
        Calendar expiration = Calendar.getInstance();
        expiration.setTime(this.getDate_echeance());

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(Date.from(Instant.now()));
        currentDate.add(Calendar.MONTH, frequence);
        if(currentDate.before(expiration)){
            return false;
        }
        return true;

    }

       
    

    
}
