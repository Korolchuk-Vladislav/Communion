package com.dreanei.communion.async;

import android.util.Log;

import com.dreanei.communion.CommunionApp;
import com.dreanei.communion.database.Database;
import com.dreanei.communion.database.Remote;
import com.dreanei.communion.models.Update;
import com.dreanei.communion.models.Updates;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static com.dreanei.communion.CommunionApp.TAG;
import static com.dreanei.communion.CommunionApp.database;


public class UpdateService implements Runnable{
    private static UpdateService instance;
    static {
        instance = new UpdateService();
    }

    private UpdateService(){

    }

    private static final String NAMESPACE = "http://cxf.soap.api.communion.herokuapp.com/";
    private static String URL="http://communion.herokuapp.com/updates/getUpdates?wsdl";
    private static final String METHOD_NAME = "getUpdatesFrom";
    private static final String SOAP_ACTION =  "";

    public static UpdateService getInstance(){
        return instance;
    }
    Database db = CommunionApp.database;
    @Override
    public void run() {
        while(true) {
            Log.d(TAG, "update runs");
            //from remote
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                PropertyInfo propInfo = new PropertyInfo();
                propInfo.name = "arg0";
                propInfo.type = PropertyInfo.INTEGER_CLASS;
                propInfo.setValue(database.getUpdatesCount());
                request.addProperty(propInfo);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject response = (SoapObject)envelope.getResponse();
                SoapObject update;
                ArrayList<Update> ressrc = new ArrayList<>();
                for(int i = 0; i < response.getPropertyCount(); i++){
                    update = (SoapObject) response.getProperty(i);
                    ressrc.add(new Update(Integer.valueOf(update.getProperty(0).toString()),
                            update.getProperty(1).toString(),
                            Integer.valueOf(update.getProperty(2).toString())));
                }
                Log.d(TAG, "newUpdatesCount: " + ressrc.size());
                database.update(new Updates(ressrc));
                Remote.getInstance().connected();
                sendFromLocal();
            }catch(Exception e){
                e.printStackTrace();
                Remote.getInstance().disconnected();
            }
            finally {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendFromLocal() {
        Log.d(TAG, "--sendFromLocal--");
        db.local.send();
        db.local.clearLocal();
    }
}

