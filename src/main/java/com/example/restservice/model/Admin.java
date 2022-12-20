package com.example.restservice.model;
import  com.example.restservice.generic.*;

@ClassAnotation(table="admin")
public class Admin{
    @Attr(col="idadmin",isPrimary=true)
    int idAdmin;
    @Attr
    String login;
    @Attr
    String mdp;

     public Admin(){
     }
     public int getIdAdmin(){
        return idAdmin;
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
   public void setIdAdmin(int idAdmin) {
      this.idAdmin = idAdmin;
   }


}