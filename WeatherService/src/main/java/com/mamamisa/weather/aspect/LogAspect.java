package com.mamamisa.weather.aspect;

import com.mamamisa.weather.Information;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.Debug;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Based on http://www.christianschenk.org/blog/profiling-with-aspectj/
 *
 * @author beat
 *
 */
@Aspect
public class LogAspect {
    
    @Around("execution(* com.mamamisa.weather.ForecastService.requestInformation(..))")
    public Object aroundRequestInformation(ProceedingJoinPoint proceedingJoinPointe){
        Object returnValue = null;
        try {
            returnValue = proceedingJoinPointe.proceed();
        } catch (Throwable e) {
                e.printStackTrace();
        }
        
        Information info = (Information)returnValue;
        
        System.out.println("theEnd");     
        String filename= "requests.log";
        FileWriter fw;
        
        try {
            fw = new FileWriter(filename,true); //the true will append the new data    
            fw.write(info.toString());//appends the string to the file
            fw.write('\n');
            // File is located into : [Glassfish root folder]/glassfish/domains/domain1/config/requests.log
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(LogAspect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return returnValue;
    }

}
