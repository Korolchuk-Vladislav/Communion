package com.dreanei.communion.models;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

@Root
public class Updates implements KvmSerializable {
    @ElementList
    private List<Update> updates;

    public Updates(){
        updates = new ArrayList<Update>();
    }

    public Updates(List<Update> updates){
        this.updates = updates;
    }

    public void setUpdates(List<Update> updates){
        this.updates = updates;
    }

    public List<Update> getUpdates(){
        return updates;
    }

    @Override
    public String toString() {
        String res = "";
        for (Update update : updates) {
            res += update.toString() + "\n";
        }
        return res;
    }

    public int size(){
        return updates.size();
    }

    public Updates after(int id){
        if (id <= 0)
            return new Updates(updates);
        ArrayList<Update> res = new ArrayList<>();
        for (Update update :updates) {
            if (update.getId() > id)
                res.add(update);
        }
        return new Updates(res);
    }

    public List<String> tables(){
        ArrayList<String> res = new ArrayList<>();
        for (Update update :updates) {
            if (!res.contains(update.table))
                res.add(update.table);
        }
        return res;
    }

    @Override
    public Object getProperty(int index) {
        if (index == 0) return updates;
        else return null;
    }

    @Override
    public int getPropertyCount() {
        return 1;
    }

    @Override
    public void setProperty(int index, Object value) {
        if (index == 0){
            updates = (List<Update>)value;
        }
    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        if (index == 0){
            info.type = new ArrayList<Update>().getClass();
            info.name = "updates";
        }
    }
}