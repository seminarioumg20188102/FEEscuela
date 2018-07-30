/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.portalgarantias.menu;


import com.proyecto.portalgarantias.lista.Lista;
import com.proyecto.portalgarantias.login.BeanLogin;
import com.proyecto.portalgarantias.webservice.Menu;
import com.proyecto.portalgarantias.webservice.Opciones;
import com.proyecto.portalgarantias.webservice.RespuestaMenu;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

//import org.primefaces.component.menuitem.MenuItem;
//import org.primefaces.component.submenu.Submenu;
//import org.primefaces.model.DefaultMenuModel;
//import org.primefaces.model.MenuModel;
/**
 *
 * @author AHERNANDEZ
 */
public class BeanMenu implements Serializable {

    //  static final long serialVersionUID = 42L;
    private MenuModel modal;
    private String SI = "S";
    private String INICIOURL = "./../gestiones/inicio.xhtml";
    private String INICIOLABEL = "Inicio";
    List<String> principalUrl;
    List<String> principalLabel;

    List<Lista> listas;
    //  private Listas listas = new Listas();

    /**
     * Creates a new instance of BeanMenu
     */
    public BeanMenu() {

        FacesContext context = FacesContext.getCurrentInstance();
        BeanLogin objLogin = (BeanLogin) context.getExternalContext().getSessionMap().get("beanLogin");
        List<String> opciones = new LinkedList<String>();
        opciones = objLogin.getOpciones();

        modal = new DefaultMenuModel();

        principalUrl = new LinkedList<String>();
        principalLabel = new LinkedList<String>();

//
//        opciones.add("AGREGAR");
//        opciones.add("CONSULTAR");
//        opciones.add("ELIMINAR");
//        opciones.add("REPORTE");
        Opciones op = new Opciones();
        try {
            op.getOpciones().addAll(opciones);
        } catch (Exception e) {
            System.out.println("no hay perfil en el cliente parametrizado");
        }

        //  System.out.println("tamanio opciones es:" + op.getOpciones().size());
        System.out.println("menu 1 " + new Date());

        RespuestaMenu menuarbol = consultaMenuOpciones("test");
        //  System.out.println("tamanio menu:" + menuarbol.getMenus().size());
        System.out.println("menu 2 " + new Date());
        List<Menu> menus = menuarbol.getMenus();

        for (Menu mi : menus) {
            System.out.println("mi menuyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy:" + mi.getDescripcion());
        }

        listas = new LinkedList<Lista>();
        for (Menu menu : menus) {
            //  if (menu.getSubmenu().equals("N")) {
            boolean existePadre = false;
            for (Lista list : listas) {
                if (list.getId().equals(menu.getPadre())) {
                    existePadre = true;
                }
            }

            if (!existePadre) {
                Lista lis = new Lista();
                lis.setId(menu.getPadre());
                listas.add(lis);

            }

            for (Lista list : listas) {
                if (list.getId().equals(menu.getPadre())) {
                    list.getListasHijos().add(menu);
                }
            }
            // }
        }

        for (Lista list : listas) {
            System.out.println("NUEVA LISTA______________________");
            System.out.println("Lista Id:" + list.getId());
            System.out.println("Lista Hijos:" + list.getListasHijos().size());

            System.out.println("FIN NUEVA LISTA______________________");
        }
        System.out.println("menu 3 " + new Date());
        try {

            principalUrl.add(INICIOURL);
            principalLabel.add(INICIOLABEL);
            
            
            principalUrl.add("./../gestiones/consultarguias.xhtml");
            principalLabel.add("Consulta de Guias");
                    
            //agregando menus de INICIO
            if (principalUrl.size() > 0) {

                for (int b = 0; b < 1; b++) {

                    
                    //menu inicial
                    DefaultMenuItem itemconsulta = new DefaultMenuItem();
                    itemconsulta.setValue(principalLabel.get(b));
                    itemconsulta.setUrl(principalUrl.get(b));
                    itemconsulta.setIcon("ui-icon-home");
                    //itemconsulta.setTransient(true);

                    modal.addElement(itemconsulta);
                }

            }

            for (Lista list : listas) {

                if (list.getId().equals("RAIZ")) {

                    List<Menu> menld = ordenaLista(list.getListasHijos());

                    for (Menu menu : menld) {

                        if (menu.getActivo().equals("S")) {

                            if (menu.getSubmenu().equals("S")) {
                                DefaultSubMenu submens = new DefaultSubMenu();
                                submens.setLabel(menu.getDescripcion());
                                modal.addElement(submens);
                                System.out.println("antes de agrega menu: " + menu.getDescripcion());
                                agregarMenu(listas, menu, submens);
                                System.out.println("DESPUES de agrega menu");

                            } else {

                                DefaultMenuItem item = new DefaultMenuItem();
                                item.setValue(menu.getDescripcion());
                                item.setUrl(menu.getUrl());
                                if (menu.getDescripcion() != null && menu.getDescripcion().equals("Modificar Servicios")) {
                                    item.setRendered(false);
                                } else if (menu.getDescripcion() != null && menu.getDescripcion().equals("Nuevo Existente")) {
                                    item.setRendered(false);
                                } else if (menu.getDescripcion() != null && menu.getDescripcion().equals("IMPRESION")) {
                                    item.setRendered(false);
                                } else if (menu.getDescripcion() != null && menu.getDescripcion().equals("IMPRESIONHIJAS")) {
                                    item.setRendered(false);
                                }
                                // item.setTransient(true);
                                modal.addElement(item);

                            }
                        }
                        System.out.println("entra 2oooooooooooooooooo");
                    }
                }
                System.out.println("entra 1iiiiiiiiii");
            }
            System.out.println("saliendoooooo de ese fooooooooooooooooooooooooooooor!");
        } catch (Exception e) {
            System.out.println("error de menu");
            e.printStackTrace();
        }

        System.out.println("menu 4 " + new Date());
    }

    boolean loagrego = false;

    public void agregarMenu(List<Lista> listasox, Menu menu, DefaultSubMenu submens) {
        System.out.println("entrando agregarMenu con menu:" + menu.getDescripcion());
        System.out.println("listasox siza:" + listasox.size());
        for (Lista list2 : listasox) {
            if (!list2.getId().equals("RAIZ")) {

                if (list2.getId().equals(menu.getCodigo())) {

                    System.out.println("entra a ordenar lista: " + list2.getListasHijos().size());
                    List<Menu> menld = ordenaLista(list2.getListasHijos());
                    System.out.println("sale de ordenar lista: " + list2.getListasHijos().size());

                    for (Menu hij : menld) {

                        if (hij.getActivo().equals("S")) {
                            if (hij.getSubmenu().equals("S")) {
                                DefaultSubMenu submens2 = new DefaultSubMenu();
                                submens2.setLabel(hij.getDescripcion());
                                submens.addElement(submens2);
                                agregarMenu(listasox, hij, submens2);

                            } else {
                                DefaultMenuItem item = new DefaultMenuItem();
                                item.setValue(hij.getDescripcion());
                                item.setUrl(hij.getUrl());
                                // item.setTransient(true);
                                submens.addElement(item);

                                if (!loagrego && submens.getLabel().equals("Consultas")) {
                                    DefaultMenuItem itemx = new DefaultMenuItem();
                                    itemx.setValue("Consulta de Guias");
                                    itemx.setUrl("./../gestiones/consultarguias.xhtml");
                                    // item.setTransient(true);
                                    submens.addElement(itemx);

                                    loagrego = true;
                                }

                            }
                        }
                        System.out.println("de aqui ya no salio:" + hij.getDescripcion());
                    }
                }
            }
            System.out.println("no sale:" + list2.getId());
        }

    }

    public String getINICIOLABEL() {
        return INICIOLABEL;
    }

    public String getINICIOURL() {
        return INICIOURL;
    }

    public String getSI() {
        return SI;
    }

    public MenuModel getModal() {
        return modal;
    }

    public List<String> getPrincipalLabel() {
        return principalLabel;
    }

    public List<String> getPrincipalUrl() {
        return principalUrl;
    }

    public void setINICIOLABEL(String INICIOLABEL) {
        this.INICIOLABEL = INICIOLABEL;
    }

    public void setINICIOURL(String INICIOURL) {
        this.INICIOURL = INICIOURL;
    }

    public void setSI(String SI) {
        this.SI = SI;
    }

    public void setModal(MenuModel modal) {
        this.modal = modal;
    }

    public void setPrincipalLabel(List<String> principalLabel) {
        this.principalLabel = principalLabel;
    }

    public void setPrincipalUrl(List<String> principalUrl) {
        this.principalUrl = principalUrl;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }

    public List<Lista> getListas() {
        return listas;
    }

    public List<Menu> ordenaLista(List<Menu> det) {

        for (Menu mi : det) {
            System.out.println("mi menuyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy2:" + mi.getDescripcion());
        }

        List<Menu> detalles = new LinkedList<Menu>();

        boolean lleno = false;
        int cont = 0;

//        for(Menu m : det){
//            System.out.println("a ordenar:"+m.getDescripcion());
//        }
        while (!lleno) {

            List<Menu> pasador = new LinkedList<Menu>();

            for (Menu de : det) {
                // System.out.println("encontrado de: "+de.isEncontrado());
                boolean rechazado = false;
                if (!de.isEncontrado()) {

                    for (Menu de2 : det) {

                        if (!de2.isEncontrado()) {
                            if (de.getPrioridad() > de2.getPrioridad()) {
                                rechazado = true;
                            }
                        }

                    }

                    if (!rechazado) {
                        de.setEncontrado(true);
                        detalles.add(de);
                        cont++;
                    }
                }

                pasador.add(de);

            }
            det = new LinkedList<Menu>();
            for (Menu mde : pasador) {
                det.add(mde);
            }

            //   System.out.println("cont: " + cont + " det.size: " + det.size());
            if (cont == det.size()) {
                lleno = true;
            }
            //  System.out.println("sigueeee cont:"+cont+" det.size:"+det.size());
        }

        return detalles;

    }

    private static RespuestaMenu consultaMenuOpciones(java.lang.String name) {
        com.proyecto.portalgarantias.webservice.GarantiasWS_Service service = new com.proyecto.portalgarantias.webservice.GarantiasWS_Service();
        com.proyecto.portalgarantias.webservice.GarantiasWS port = service.getGarantiasWSPort();
        return port.consultaMenuOpciones(name);
    }

  

 
}

