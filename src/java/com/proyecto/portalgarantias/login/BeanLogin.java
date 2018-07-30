/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.portalgarantias.login;


import com.proyecto.portalgarantias.menu.BeanMenu;
import com.proyecto.portalgarantias.webservice.RespuestaLogin;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.StateManager;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AHERNANDEZ
 */
public class BeanLogin implements Serializable {

    //  static final long serialVersionUID = 42L;
    private String usuario;
    private String password;
    private List<String> opciones;
    private String idserviciotemp;
    private String muestraBTemp = "N";
    private String redireccion = "T";
    private List<com.proyecto.portalgarantias.login.Respuesta> codigosCobro = new LinkedList<com.proyecto.portalgarantias.login.Respuesta>();

    public void setCodigosCobro(List<Respuesta> codigosCobro) {
        this.codigosCobro = codigosCobro;
    }

    public List<Respuesta> getCodigosCobro() {
        return codigosCobro;
    }

    /**
     * Creates a new instance of BeanLogin
     */
    public BeanLogin() {
        FacesContext context = FacesContext.getCurrentInstance();

        String us = context.getExternalContext().getRequestParameterMap().get("prueba");
        System.out.println("el parametro " + us);
        //usuario =us;

    }

    public void enviarBeeper(ActionEvent actionEvent) {
//        SendBepeer b = new SendBepeer();
//        try {
//            b.sendPost();
//        } catch (Exception ex) {
//            Logger.getLogger(BeanLogin.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void validarCredenciales(ActionEvent actionEvent) {
        System.out.println("proceso 1 " + new Date());
        FacesMessage msg = null;
        boolean loggedIn = false;
        RequestContext context = RequestContext.getCurrentInstance();

        if (usuario == null || usuario.trim().length() == 0) {

            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de Login: Debe de escribir su usuario", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.addCallbackParam("loggedIn", loggedIn);

        } else if (password == null || password.trim().length() == 0) {

            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de Login: Debe de escribir su contraseña", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.addCallbackParam("loggedIn", loggedIn);

        } else {

       
            System.out.println("proceso 1.1 IIS" + new Date());
            RespuestaLogin respuesta = validaCredenciales("PRUEBA");
            //com.proyecto.portalgarantias.login.Respuesta res = pars.parseoRespuesta(respuesta.getXml());

            System.out.println("respuesta ws = " + respuesta);
//            Respuesta res = new Respuesta();
//            res.setCodigo("S");
//            res.setOpciones("CALLTOMADESERVICIOS,");
            System.out.println("proceso 2 " + new Date());
            if (true) {

                if (true) {
                   
                    opciones = new LinkedList<String>();
                    opciones.add("inicio");

                   
//                    for (String letra : opciones) {
//                        System.out.println("opcion es [" + letra + "]");
//                    }
                }

                System.out.println("proceso 3 " + new Date());

                FacesContext context2 = FacesContext.getCurrentInstance();
                context2.getExternalContext().getSessionMap().put("beanMenu", new BeanMenu());
                HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                loggedIn = true;
                ses.setAttribute("currentUser", usuario);
                context.addCallbackParam("loggedIn", loggedIn);
                System.out.println("proceso 4 " + new Date());
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("./inicio.xhtml");
                } catch (IOException ex) {
                    System.out.println("Error al redireccionar");
                }

            } else {
                loggedIn = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales invalidas", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.addCallbackParam("loggedIn", loggedIn);
            }

        }
    }

    public void logout() {

        HttpSession ses = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        //ses.setAttribute("currentUser", null);
        // ses.removeAttribute("currentUser");
        ses.invalidate();

        FacesContext context = FacesContext.getCurrentInstance();

        context.getExternalContext().getSessionMap().clear();
        try {
            context.getExternalContext().redirect("/Garantias/faces/pages/gestiones/login.xhtml");
        } catch (IOException ex) {
            System.out.println("Error de logout [" + ex.getMessage() + "]");
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setIdserviciotemp(String idserviciotemp) {
        this.idserviciotemp = idserviciotemp;
    }

    public String getIdserviciotemp() {
        return idserviciotemp;
    }

    public void setMuestraBTemp(String muestraBTemp) {
        this.muestraBTemp = muestraBTemp;
    }

    public String getMuestraBTemp() {
        return muestraBTemp;
    }

    public void setRedireccion(String redireccion) {
        this.redireccion = redireccion;
    }

    public String getRedireccion() {
        return redireccion;
    }

    private static RespuestaLogin validaCredenciales(java.lang.String name) {
        com.proyecto.portalgarantias.webservice.GarantiasWS_Service service = new com.proyecto.portalgarantias.webservice.GarantiasWS_Service();
        com.proyecto.portalgarantias.webservice.GarantiasWS port = service.getGarantiasWSPort();
        return port.validaCredenciales(name);
    }



}
