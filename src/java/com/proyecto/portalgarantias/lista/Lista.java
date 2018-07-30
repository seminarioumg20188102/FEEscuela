/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.portalgarantias.lista;

import com.proyecto.portalgarantias.webservice.Menu;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author AHERNANDEZ
 */
public class Lista implements  Serializable {
   // static final long serialVersionUID = 42L;
    public Lista() {
    }
    private String id;
    private String idPadre;
    private List<Menu> listasHijos = new LinkedList<Menu>();
    

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

    public String getIdPadre() {
        return idPadre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setListasHijos(List<Menu> listasHijos) {
        this.listasHijos = listasHijos;
    }

    public List<Menu> getListasHijos() {
        return listasHijos;
    }
}
