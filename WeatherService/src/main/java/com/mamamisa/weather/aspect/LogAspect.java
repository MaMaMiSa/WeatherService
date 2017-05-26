package com.mamamisa.weather.aspect;

import com.mamamisa.weather.ForecastService;
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


    @Pointcut("execution(* com.mamamisa.weather.ForecastService.actualizeInformation(..))")
    public void onExit(){}
        
    
    @After("onExit()")
    public void theEnd(JoinPoint joinPoint){
        System.out.println("theEnd");
        Object[] arguments = joinPoint.getArgs();

        String filename= "requests.log";
        FileWriter fw;
        try {
            fw = new FileWriter(filename,true); //the true will append the new data    
            fw.write(arguments[0].toString());//appends the string to the file
            // File is located into : [Glassfish root folder]/glassfish/domains/domain1/config/requests.log
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(LogAspect.class.getName()).log(Level.SEVERE, null, ex);
        }
        //PerformanceCounter.getInstance().printSummary();
    }

}
