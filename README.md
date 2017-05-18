# WeatherService

This web service was developed in Java. It gives weather forecasts whose data are generated randomly.

## Resources
[Weather API](https://openweathermap.org/)

## Build project with Maven and Git only

This assumes you have Maven 3+ and Git installed.

    Maven can be downloaded at: http://maven.apache.org/download.html
    Git can be downloaded at: http://git-scm.com/download

How to build the project ?

1) Open a terminal
2) git clone https://github.com/MaMaMiSa/WeatherService
3) cd WeatherService
4) mvn compile

Output :
```
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building WeatherServiceApp 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
Downloading: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-resources-plugin/2.6/maven-resources-pl
ugin-2.6.pom
....
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 11.510 s
[INFO] Finished at: 2017-05-18T16:00:11+02:00
[INFO] Final Memory: 23M/285M
[INFO] ------------------------------------------------------------------------
```
## Build project with Maven, Git and Netbeans

This assumes you have Maven 3+, Git and Netbeans installed.

    Maven can be downloaded at: http://maven.apache.org/download.html
    Git can be downloaded at: http://git-scm.com/download
    Netbeans can be downloaded at: https://netbeans.org/downloads/?pagelang=pt_BR
    (Choose the package which include a Glassfish or Tomcat server).

How to build the project ?

1) Open a terminal
2) git clone https://github.com/MaMaMiSa/WeatherService
3) import the WeatherService in NetBeans
4) Build the project : Right-click on the project and choose Build with dependencies. You should see this output : 
```
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building WeatherServiceApp 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
Downloading: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-resources-plugin/2.6/maven-resources-pl
ugin-2.6.pom
....
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 11.510 s
[INFO] Finished at: 2017-05-18T16:00:11+02:00
[INFO] Final Memory: 23M/285M
[INFO] ------------------------------------------------------------------------
```

5) Run the project : Right-click on the project and choose Run. A Web Browser should open :
![alt text](https://github.com/MaMaMiSa/WeatherService/raw/master/gui.png "Web Service")


