/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.portalgarantias.login;


/**
 *
 * @author AHERNANDEZ
 */
public class Respuesta {

    private String codigo;
    private String descripcion;
    private String opciones;
    private String tipoLogin;
    private String micodcob;



    
    

    public void setMicodcob(String micodcob) {
        this.micodcob = micodcob;
    }

    public String getMicodcob() {
        return micodcob;
    }
    
    

    public void setTipoLogin(String tipoLogin) {
        this.tipoLogin = tipoLogin;
    }

    public String getTipoLogin() {
        return tipoLogin;
    }
    
    

    public void setOpciones(String opciones) {
        this.opciones = opciones;
    }

    public String getOpciones() {
        return opciones;
    }


    

    public Respuesta() {
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
