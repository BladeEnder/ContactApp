package com.example.td2;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {

    private String nom;
    private String prenom;
    private String datenaiss;
    private String email;
    private String adresse;
    private String cp;
    private String tel;
    private String sexe;
    private ProxyBitmap img;
    public static ArrayList<Contact> listeContact = new ArrayList<>();

    public Contact(String nom, String prenom, String datenaiss, String email, String adresse, String cp, String tel, String sexe, ProxyBitmap img) {
        this.nom = nom;
        this.prenom = prenom;
        this.datenaiss = datenaiss;
        this.email = email;
        this.adresse = adresse;
        this.cp = cp;
        this.tel = tel;
        this.sexe = sexe;
        this.img = img;
    }

    public Contact(String nom, String prenom, String datenaiss, String email, String adresse, String cp, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.datenaiss = datenaiss;
        this.email = email;
        this.adresse = adresse;
        this.cp = cp;
        this.tel = tel;
    }

    public Contact(String nom, String prenom, String datenaiss, String email, String adresse, String cp, String tel, String sexe) {
        this.nom = nom;
        this.prenom = prenom;
        this.datenaiss = datenaiss;
        this.email = email;
        this.adresse = adresse;
        this.cp = cp;
        this.tel = tel;
        this.sexe = sexe;
    }

    public ProxyBitmap getImg() {
        return img;
    }

    public void setImg(ProxyBitmap img) {
        this.img = img;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public static ArrayList<Contact> getListeContact() {
        return listeContact;
    }

    public static void setListeContact(ArrayList<Contact> listeContact) {
        Contact.listeContact = listeContact;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDatenaiss() {
        return datenaiss;
    }

    public void setDatenaiss(String datenaiss) {
        this.datenaiss = datenaiss;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
