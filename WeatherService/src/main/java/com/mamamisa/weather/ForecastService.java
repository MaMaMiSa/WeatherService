/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mamamisa.weather;

import com.mamamisa.weather.http.HTTPRequest;
import com.mamamisa.weather.http.HTTPRequestMethod;
import com.mamamisa.weather.http.HTTPRequestResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.lang.model.element.Element;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Description : 
 * This SOAP service allows to display particular conditions 
 * (temperature, wind speed and direction, sun, pressure, humidity).
 * 
 * Author : Michaël Polla
 * Author : Magali Fröhlich
 * Author : Salvatore Cicciù 
 * Author : Maxime Burri
 */
@WebService(serviceName = "ForecastService")
public class ForecastService {
    
    String key = this.getAPIKey();
    String url = "http://api.openweathermap.org/data/2.5/weather?appid=" + key + "&q=";
    
    Information lastInformation;
    
    
    private String getAPIKey() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("../app.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
            return properties.getProperty("openweathermap_api_key");
        } catch (IOException ex) {
            Logger.getLogger(ForecastService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private Information requestInformation(String location){
        HTTPRequest httpReqGetSection = new HTTPRequest();
        HTTPRequestResult resultReq = httpReqGetSection.submit(this.url + location, HTTPRequestMethod.GET);
        Information info = new Information();
        info.location = location;
        
        try {
            JSONObject main = resultReq.jsonObject.getJSONObject("main");
            info.temperature = (int)Math.round(main.getDouble("temp") - 273.15);
            info.humidity = (int)Math.round(main.getDouble("humidity"));

            JSONObject wind = resultReq.jsonObject.getJSONObject("wind");
            info.windSpeed = (int)Math.round(wind.getDouble("speed"));
            Integer deg = wind.getInt("deg");
            String directions[] = {"North", "North East", "East", "South East", "South", "South West", "West", "North West"};
            info.windDirection = directions[ (int)Math.round(((double)(deg)) / 45) ];
            info.forecast = resultReq.jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }
    
    private void requestInformationOnce(String location){
       if(this.lastInformation == null || this.lastInformation.location != null 
               && !this.lastInformation.location.equals(location))
           this.lastInformation = requestInformation(location);
    }
     
    
    @WebMethod(operationName = "actualizeInformation")
    public void actualizeInformation(@WebParam(name = "location") String txt)  {    
        requestInformationOnce(txt);
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
        this.requestInformationOnce(txt);
        return this.lastInformation.temperature;
    }    
    
    @WebMethod(operationName = "getWindSpeed")
    public Integer getWindSpeed(@WebParam(name = "location") String txt) {
        this.requestInformationOnce(txt);
        return this.lastInformation.windSpeed;
    }

    @WebMethod(operationName = "getWindDirection")
    public String getWindDirection(@WebParam(name = "location") String txt) {
        this.requestInformationOnce(txt);
        return this.lastInformation.windDirection;
    }
    
    @WebMethod(operationName = "getForecast")
    public String getForecast(@WebParam(name = "location") String txt) {
        this.requestInformationOnce(txt);
        return this.lastInformation.forecast;
    }

    @WebMethod(operationName = "getHumidity")
    public Integer getHumidity(@WebParam(name = "location") String txt) {
        this.requestInformationOnce(txt);
        return this.lastInformation.humidity;
    }

}
