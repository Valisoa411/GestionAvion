package com.example.restservice.model;
import  com.example.restservice.generic.*;

@ClassAnotation(table="utilisateur")
public class Utilisateur{
    @Attr(col="idutilisateur",isPrimary=true)
    int idUtilisateur;
    @Attr
    String login;
    @Attr
    String mdp;

     public Utilisateur(){
     }
     public int getIdUtilisateur(){
        return idUtilisateur;
     }
     
    public void setLogin(String login){
        this.login=login;
    }
    public void setMdp(String mdp){
        this.mdp=mdp;
    }
    
     public String getLogin(){
        return login;
     }
     public String getMdp(){
        return  mdp;
     }
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
 
}
