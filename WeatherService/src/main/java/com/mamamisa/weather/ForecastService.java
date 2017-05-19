/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mamamisa.weather;

import com.mamamisa.weather.http.HTTPRequest;
import com.mamamisa.weather.http.HTTPRequestMethod;
import com.mamamisa.weather.http.HTTPRequestResult;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import jdk.nashorn.internal.runtime.Debug;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description : 
 * The SOAP service developed allows to display particular conditions 
 * (temperature, ventilation, sun, pressure, humidity).
 * 
 * The service will look for different values on different sites : 
 * - google api
 * 
 * We hypothesize that some sites are better for temperature or wind, etc.
 * 
 * Author : Michael Polla
 * Author : Magali Fröhlich
 * Author : Salvatore Cicciù 
 * Author : Maxime Burri
 */
@WebService(serviceName = "ForecastService")
public class ForecastService {
    
    String url = "http://api.openweathermap.org/data/2.5/weather?appid=???&q=";
    
    
    class Information {
        Integer temperature;
        Integer ventilationSpeed;
        String ventilationOrigin;
        String forecast;
        Integer humidity;
    }
    
    Information lastInformation;
    String lastLocation;
    
    public void actualizeInformation(String location){
        if(this.lastLocation != location){
            HTTPRequest httpReqGetSection = new HTTPRequest();
            HTTPRequestResult resultReq = httpReqGetSection.submit(this.url + location, HTTPRequestMethod.GET);
            this.lastInformation = new Information();

            try {
                JSONObject main = resultReq.jsonObject.getJSONObject("main");
                this.lastInformation.temperature = (int)Math.round(main.getDouble("temp") - 273.15);
                this.lastInformation.humidity = (int)Math.round(main.getDouble("humidity"));

                JSONObject wind = resultReq.jsonObject.getJSONObject("wind");
                this.lastInformation.ventilationSpeed = (int)Math.round(wind.getDouble("speed"));
                Integer deg = wind.getInt("deg");
                String directions[] = {"North", "North East", "East", "South East", "South", "South West", "West", "North West"};
                this.lastInformation.ventilationOrigin = directions[ (int)Math.round(((double)(deg)) / 45) ];
                this.lastInformation.forecast = resultReq.jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.lastLocation = location;
        }
    }

    @WebMethod(operationName = "getDay")
    public String getDay() {    
        SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return  Character.toUpperCase(strDate.charAt(0)) + strDate.substring(1);
    }
    
    @WebMethod(operationName = "getDate")
    public String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("d MMM");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return  strDate;
    }
  
    @WebMethod(operationName = "getTemperature")
    public Integer getTemperature(@WebParam(name = "location") String txt) {
        this.actualizeInformation(txt);
        return this.lastInformation.temperature;
    }    
    
    @WebMethod(operationName = "getVentilationSpeed")
    public Integer getVentilationSpeed(@WebParam(name = "location") String txt) {
        this.actualizeInformation(txt);
        return this.lastInformation.ventilationSpeed;
    }

    @WebMethod(operationName = "getVentilationOrigin")
    public String getVentilationOrigin(@WebParam(name = "location") String txt) {
        this.actualizeInformation(txt);
        return this.lastInformation.ventilationOrigin;
    }
    
    @WebMethod(operationName = "getForecast")
    public String getForecast(@WebParam(name = "location") String txt) {
        this.actualizeInformation(txt);
        return this.lastInformation.forecast;
    }

    @WebMethod(operationName = "getHumidity")
    public Integer getHumidity(@WebParam(name = "location") String txt) {
        this.actualizeInformation(txt);
        return this.lastInformation.humidity;
    }

}
