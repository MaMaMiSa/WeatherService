/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mamamisa.weather;

/**
 *
 * @author maxime
 */
public class Information {
    public String location;
    public Integer temperature;		
    public Integer windSpeed;		
    public String windDirection;		
    public String forecast;		
    public Integer humidity;
    
    @Override
    public String toString(){
        return String.format("%s:%s\t%s:%d\t%s:%d\t%s:%s\t%s:%s\t%s:%d\t",
                "location", location,
                "temperature", temperature,
                "windSpeed", windSpeed,
                "windDirection", windDirection,
                "forecast", forecast,
                "humidity", humidity);
    }
}
