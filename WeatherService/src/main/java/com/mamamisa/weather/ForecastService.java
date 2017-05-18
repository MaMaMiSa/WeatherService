/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mamamisa.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

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
        int n = (int)(Math.random() * ((40 - (-15)) + 1)) + (-15);
        return n;
    }    
    
    @WebMethod(operationName = "getVentilationSpeed")
    public Integer getVentilationSpeed(@WebParam(name = "location") String txt) {
        int n = (int)(Math.random() * ((100 - 0) + 1)) + 0;
        return n;
    }

    @WebMethod(operationName = "getVentilationOrigin")
    public String getVentilationOrigin(@WebParam(name = "location") String txt) {
        int n = (int)(Math.random() * ((3 - 0) + 1)) + 0;
        switch(n) {
            case 0:
                return "East";
            case 1:
                return "West";
            case 2:
                return "North";
            default:
                return "South";
        }
    }
    
    @WebMethod(operationName = "getForecast")
    public String getForecast(@WebParam(name = "location") String txt) {
        int n =  (int)(Math.random() * ((5 - 0) + 1)) + 0; 
        switch(n) {
            case 0:
                return "Fog";
            case 1:
                return "Snow";
            case 2:
                return "Sun";
            case 3:
                return "Cloud";
            case 4:
                return "Rain";
            default:
                return "Thunderstorm";
        }
    }

    @WebMethod(operationName = "getHumidity")
    public Integer getHumidity(@WebParam(name = "location") String txt) {    
        int n = (int)(Math.random() * ((100 - 0) + 1)) + 0; 
        return n;
    }

}
