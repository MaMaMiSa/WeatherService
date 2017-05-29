	$(document).ready(function(){
		
	    function sendSoapRequest(soap_req, callback) {
            
			var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('POST', 'http://localhost:8080/WeatherService/ForecastService', true);

            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState === 4) {
                    if (xmlhttp.status === 200) {
                        var res = xmlhttp.responseXML.getElementsByTagName("return")[0].childNodes[0].nodeValue;
						callback(res);
                    }
                }
            };
            /* Send the POST request */
            xmlhttp.setRequestHeader('Content-Type', 'text/xml');
            xmlhttp.send(soap_req);
        }
		
		function forecast_update(location_forecast) {
		
			/* Header of soap request */
			var sr_begin = 	'<?xml version="1.0" encoding="UTF-8"?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">' +
							'<SOAP-ENV:Header/>' +
								'<S:Body>';
			
			/* Param of soap request */
			var p = 				'<location>' + location_forecast + '</location>';
			
			/* Footer of a soap request */
			var sr_end = 		'</S:Body>' +
							'</S:Envelope>';
					
			/* Soap message Web service : WeatherService/ForecastService */
                        var sr_update_information 	= '<ns2:updateInformation xmlns:ns2="http://weather.mamamisa.com/">' + '</ns2:updateInformation>';
			var sr_day 			= '<ns2:getDay xmlns:ns2="http://weather.mamamisa.com/">' + '</ns2:getDay>';
			var sr_date			= '<ns2:getDate xmlns:ns2="http://weather.mamamisa.com/">' + '</ns2:getDate>';
			var sr_temperature 		= '<ns2:getTemperature xmlns:ns2="http://weather.mamamisa.com/">' 		+ p + '</ns2:getTemperature>';
			var sr_wind_speed               = '<ns2:getWindSpeed xmlns:ns2="http://weather.mamamisa.com/">' 	+ p + '</ns2:getWindSpeed>';
			var sr_wind_direction           = '<ns2:getWindDirection xmlns:ns2="http://weather.mamamisa.com/">' + p + '</ns2:getWindDirection>';
			var sr_forecast                 = '<ns2:getForecast xmlns:ns2="http://weather.mamamisa.com/">' 			+ p + '</ns2:getForecast>';
			var sr_humidity                 = '<ns2:getHumidity xmlns:ns2="http://weather.mamamisa.com/">' 			+ p + '</ns2:getHumidity>';

			/* Callback to display the response*/
                        var update_information_cb = function (res) {
                            // Not implemented;
                        }
                        
			var day_cb = function (res) {
				document.getElementById("div_day").innerHTML = res; 
			};
			
			var date_cb = function (res) {
				document.getElementById("div_date").innerHTML = res; 
			};
			
			var temperature_cb = function (res) {
				document.getElementById("div_temperature").innerHTML = res + '<sup>o</sup>C';			
			};
			
			var forecast_cb = function (res) {
                                let icon = "";
                                switch(res) {
                                    case "Thunderstorm":
                                        icon = "icon-12.svg";
                                        break;
                                    case "Drizzle":
                                        icon = "icon-10.svg";
                                        break;
                                    case "Rain":
                                        icon = "icon-9.svg";
                                        break;
                                    case "Snow":
                                        icon = "icon-14.svg";
                                        break;
                                    case "Atmosphere":
                                        icon = "icon-7.svg";
                                        break;
                                    case "Clear":
                                        icon = "icon-2.svg";
                                        break;
                                    case "Clouds":
                                        icon = "icon-6.svg";
                                        break;
                                    case "Extreme":
                                        icon = "icon-8.svg";
                                        break;
                                    default:
                                        icon = "icon-2.svg";
                                        break;     
                                }
                                document.getElementById("div_forecast").innerHTML = '<img src="images/icons/'+icon+'" alt="" width=90>';
			};
			
			var humidity_cb = function (res) {
				document.getElementById("div_humidity").innerHTML = '<img src="images/icon-umberella.png" alt="">' + res + '%</span>';
			};
			
			var wind_speed_cb = function (res) {
				document.getElementById("div_wind_speed").innerHTML = '<img src="images/icon-wind.png" alt="">' + res + 'km/h</span>';
			};
			
			var wind_direction_cb = function (res) {
				document.getElementById("div_wind_direction").innerHTML = '<img src="images/icon-compass.png" alt="">' + res + '</span>';
			};
			
			/* Send request */
                        sendSoapRequest(sr_begin + sr_update_information + sr_end, update_information_cb);
			sendSoapRequest(sr_begin + sr_day + sr_end, day_cb);
			sendSoapRequest(sr_begin + sr_date + sr_end, date_cb);
			sendSoapRequest(sr_begin + sr_temperature + sr_end, temperature_cb);
			sendSoapRequest(sr_begin + sr_wind_speed + sr_end, wind_speed_cb);
			sendSoapRequest(sr_begin + sr_wind_direction + sr_end, wind_direction_cb);
			sendSoapRequest(sr_begin + sr_forecast + sr_end, forecast_cb);
			sendSoapRequest(sr_begin + sr_humidity + sr_end, humidity_cb);
		}
                                
                find_forecast = function() {
                    document.getElementById("div_location").innerHTML = document.getElementById("input_user_location").value;
                    forecast_update(document.getElementById("div_location").innerHTML);
                };
		
	});
