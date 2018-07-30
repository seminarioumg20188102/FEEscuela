/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.portalgarantias.util;

import java.io.Serializable;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 * An implementation of SelectableDataModel using a list as data
 */
public class PrimeDataModel<T> extends ListDataModel implements SelectableDataModel<T>, Serializable {

    //static final long serialVersionUID = 42L;
    public PrimeDataModel() {
    }

    public PrimeDataModel(Object data) {
        setWrappedData(data);
    }

    public Object getRowKey(T object) {
        throw new UnsupportedOperationException("Must be implemented");
    }

    public T getRowData(String rowKey) {
        throw new UnsupportedOperationException("Must be implemented");
    }
}

