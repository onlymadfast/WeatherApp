package com.tsipadan.getRequest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Нужно сформировать и отправить запрос (желательно средствами RestAssured)
 * на получение данных о погоде в Санкт-Петербурге (по координатам) на данный момент.
 * <p>
 * Результатом будет некоторый JSON, который с помощью RestAssured можно распарсить в пользовательский тип данных.
 * <p>
 * Вывести на экран (или в файл) температуру воздуха, температуру комфорт (по ощущениям),
 * влажность, атмосферное давление, ветер (м/c), описание погоды,
 * дату и время (локальные данные географического объекта - в нашем случае в Санкт-Петербурге).
 */
public class GetData {

  @Test
  public void testResponseCode() {

    //for normal dateTime format
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    //response to weather API, openweathermap.org
    Response response = RestAssured.get("http://api.openweathermap.org/data/2.5/weather?lat=59.9386300&lon=30.3141300&units=metric&appid=89f161f53c2fdc3bed1b76477a0d50d5");
    JsonPath jsonPath = response.jsonPath();

    //print JSON
    response.jsonPath().prettyPrint();
    System.out.println("---------------------------------");

    //current date and time
    System.out.println("Today: " + formatter.format(date));

    //coordinate and local data
    LinkedHashMap<String, Float> map = jsonPath.get("coord");
    System.out.println("Coord -> " + " Lon: " + map.get("lon") + " Lat: " + map.get("lat"));
    System.out.println("LocalData -> " + "TimeZone: " + jsonPath.get("timezone")
        + " Name: " + jsonPath.get("name"));

    //temperature, feelsLike, humidity, pressure
    LinkedHashMap<String, Float> main = jsonPath.get("main");
    System.out.println("Temp: " + main.get("temp"));
    System.out.println("FeelsLike: " + main.get("feels_like"));
    System.out.println("Humidity: " + main.get("humidity"));
    System.out.println("Pressure: " + main.get("pressure"));

    //wind speed
    LinkedHashMap<String, Integer> wind = jsonPath.get("wind");
    System.out.println("WindSpeed: " + wind.get("speed") + " m/s");

    //weather description
    ArrayList<String> json = response.body().path("weather.description");
    String description = json.get(0);
    System.out.println("Weather description: " + description);

  }

}
