package com.mamamisa.weather.http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is for doing http request
 * This is a "static" class which just provide static methods to do a http request
 */
public class HTTPRequest {
    /**
     * Envoi la requete sans corps(pour toutes demande mais pas de POST)
     *
     * @param RESTServiceEndpoint Adresse du serveur
     * @param requestMethodType Methode HTTP de la requete
     * @return Resultat de la requete en HTTPRequestResult object
     */
    public static HTTPRequestResult submit(String RESTServiceEndpoint, HTTPRequestMethod requestMethodType) {
        return submit(RESTServiceEndpoint, requestMethodType, null);
    }

    /**
     * Envoie de requete
     *
     * @param RESTServiceEndpoint Adresse du serveur
     * @param requestMethodType Methode HTTP de la requete
     * @param requestBodyParameters
     * @return Resultat de la requete en HTTPRequestResult object
     */
    public static HTTPRequestResult submit(String RESTServiceEndpoint, HTTPRequestMethod requestMethodType, HashMap<String, String> requestBodyParameters) {
        HTTPRequestResult result = new HTTPRequestResult();
        String resultSTR = "";

        try {
            //Construction de la requete
            URL url = new URL(RESTServiceEndpoint);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(requestMethodType.toString());
            urlConnection.setUseCaches(false);

            if (requestMethodType == HTTPRequestMethod.POST)
                urlConnection.setDoOutput(true); // This method is juste for POST request

            //Test si nous avons besoin pour créer le corps de la demande
            if (requestBodyParameters != null) {
                String RequestBodyContentString = "";
                for(Map.Entry<String, String> entry : requestBodyParameters.entrySet())
                    RequestBodyContentString += entry.getKey() + "=" + entry.getValue() + "&";

                byte[] RequestBodyContent = RequestBodyContentString.getBytes();

                String charset = "UTF-8";
                String requestBodyContentType = "application/x-www-form-urlencoded;charset=" + charset;

                urlConnection.setRequestProperty("Accept-Charset", charset);
                urlConnection.setRequestProperty("Content-Type", requestBodyContentType);
                urlConnection.setRequestProperty("Content-Length", String.valueOf(RequestBodyContent.length));

                //Ecriture des paramètres dans la requete
                DataOutputStream requestStream = null;
                try {
                    requestStream = new DataOutputStream(urlConnection.getOutputStream());
                    requestStream.write(RequestBodyContent);

                } catch (Exception e) {
                    e.printStackTrace();
                    requestStream.flush();
                    requestStream.close();
                }
            }

            try {
                urlConnection.connect();

                //Prend la reponse
                result.responseCode = urlConnection.getResponseCode();

                //Lecture du resultat de cette requete
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    resultSTR += line + "\n";
                }
                bufferedReader.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
        } catch(Exception e) { }

        try {
            result.jsonObject = new JSONObject(resultSTR);
        } catch (JSONException e) {
            e.printStackTrace();
            result.jsonObject = null;
        }

        return result;
    }
}
