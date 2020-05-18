package com.dreanei.communion.models;


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.sql.Date;
import java.util.Hashtable;

@Root
public class Update implements KvmSerializable {
    @Element
    public int id;
    @Element
    public String table;
    @Element
    public int idRow;

    public Update(int id, String table, int idRow) {
        this.id = id;
        this.table = table;
        this.idRow = idRow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getIdRow() {
        return idRow;
    }

    public void setIdRow(int idFrom) {
        this.idRow = idFrom;
    }

    @Override
    public String toString() {
        return id + ": " + table + "(" + idRow +  ");";
    }

    @Override
    public Object getProperty(int index) {
        switch(index)
        {
            case 0:
                return id;
            case 1:
                return table;
            case 2:
                return idRow;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 3;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch(index)
        {
            case 0:
                id = Integer.parseInt(value.toString());
                break;
            case 1:
                table = value.toString();
                break;
            case 2:
                idRow = Integer.parseInt(value.toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        switch(index)
        {
            case 0:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "id";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "table";
                break;
            case 2:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "idRow";
                break;
            default: break;
        }
    }
}
