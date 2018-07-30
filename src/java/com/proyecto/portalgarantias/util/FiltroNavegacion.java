/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.portalgarantias.util;


import com.proyecto.portalgarantias.lista.Lista;
import com.proyecto.portalgarantias.menu.BeanMenu;
import com.proyecto.portalgarantias.webservice.Menu;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//public class FiltroNavegacion {
public class FiltroNavegacion implements Filter {

    FilterConfig fc;

    public void init(FilterConfig filterConfig) throws ServletException {
        fc = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        resp.setDateHeader("Expires", 0); // Proxies.
        HttpSession session = req.getSession(true);
        String pageRequested = req.getRequestURL().toString();

        try {

            System.out.println("aqui 1");

            if (session.getAttribute("currentUser") == null && !(pageRequested.contains("login.xhtml") || pageRequested.contains("plantilla.xhtml"))) {
                System.out.println("entrando a redirect filtrooo");
                resp.sendRedirect("/Garantias/faces/pages/gestiones/login.xhtml");
            } else {
                System.out.println("aqui 2");
                boolean tieneAcceso = false;
                BeanMenu menu = (BeanMenu) session.getAttribute("beanMenu");

                if (menu != null) {
                    for (String url : menu.getPrincipalUrl()) {
                        String auxUrl = url.replace("./..", "");
//                        System.out.println("la url es: " + auxUrl);
//                        System.out.println("el request es: " + pageRequested.trim());

                        if (pageRequested.trim().endsWith(auxUrl.trim())) {
//                            System.out.println("si tiene acceso");
                            tieneAcceso = true;
                        }
                    }
                    if (!tieneAcceso) {
                        for (Lista lista : menu.getListas()) {
                            for (Menu menux : lista.getListasHijos()) {
                                if (!menux.getSubmenu().equals("S")) {
                                    String auxUrl = menux.getUrl().replace("./..", "");

                                    if (pageRequested.trim().endsWith(auxUrl.trim())) {
                                        tieneAcceso = true;
                                    }
                                }
                            }

                        }
                    }


                    if (!tieneAcceso && pageRequested.endsWith("error.xhtml")) {
                        tieneAcceso = true;
                    }

                    if (!tieneAcceso && !(pageRequested.contains("login.xhtml"))) {

                        resp.sendRedirect("/Garantias/faces/pages/gestiones/error.xhtml");
                    } else {
                        chain.doFilter(request, response);
                    }
                } else {

                    chain.doFilter(request, response);
                }
            }
        } catch (Exception e) {
            System.out.println("error en filtro de navegacion [" + e.getMessage() + "]");
        }
    }

    public void destroy() {
    }
}
