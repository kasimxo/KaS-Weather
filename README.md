# KaS-Weather
 A weather app with a graphic interface. Uses AEMET OpenData API to get real, accurate data.<hr/>
 
 <h3><a href="https://www.aemet.es/es/portada">AEMET</a></h3>
 
 <b>AEMET</b> (Agencia Estatal de Meteorología) is the Spanish National Meteorology Agency. It has an oficial API (<a  href="https://www.aemet.es/es/datos_abiertos/AEMET_OpenData">AEMET OpenData</a>) to obtain every posible kind of   actual weather data (temperature, wind, humidity, waves...).<hr/>
 
 <h3>MAEVEN</h3>
 
 <h6>Dependencies:</h6><br/>
 
 ![](https://img.shields.io/badge/unirest--java-3.14.1-red)
 
 This is what we use to do requests to the server and handle the responses.
 
 ![](https://img.shields.io/badge/json--simple-1.1.1-blue)
 
 Data received from AEMET OpenData comes in JSON format. We use this dependency to read and interpret this data easily.
 
 ![](https://img.shields.io/badge/sqlite--jdbc-3.8.7-brightgreen)
 
We use SQLite to save every request done and save the server from unnecesary trafic. Everytime the program start, it deletes old request to save memory.
 

