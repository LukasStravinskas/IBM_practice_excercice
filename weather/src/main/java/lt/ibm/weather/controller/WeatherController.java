package lt.ibm.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.ibm.weather.model.Weather;
import lt.ibm.weather.repository.WeatherRepository;
import lt.ibm.weather.utils.Utils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.sql.Array;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path="/weather")
public class WeatherController {
    //will get weather data from api every hour (3600000 milliseconds)
    private final int scheduleTime = 3600000;
    //url to weather api
    private final String weatherApiUrl = "https://api.weatherbit.io/v2.0/forecast/hourly?city=Vilnius&key=1d017a5502824ebbae8ee91a8ceec866&hours=48";

    @Autowired
    private WeatherRepository weatherRepository;

    @GetMapping(path = "/get/weather/data/every/hour")
    @Async
    @CrossOrigin
    @Scheduled(fixedRate = scheduleTime)
    public Object getWeatherData() throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        JSONObject result = restTemplate.getForObject(weatherApiUrl, JSONObject.class);

        ArrayList<Object> jsonList = (ArrayList<Object>) result.get("data");
        for (Object obj: jsonList){
            Map<String, Object> tempMap = (Map<String, Object>) obj;

            Map<String, Object> tempWeather = (Map<String, Object>) tempMap.get("weather");
            String tempDateTimeString = (String) tempMap.get("datetime");

            Date tempDate = Utils.stringToDateConverter(tempDateTimeString);
            System.out.println(weatherRepository.findWeatherByDateTime(tempDate));
            if (weatherRepository.findWeatherByDateTime(tempDate) == null){
                Weather w = new Weather();
                w.setDateTime(Utils.stringToDateConverter(tempDateTimeString));
                w.setTemp(Double.parseDouble(tempMap.get("temp").toString()));
                w.setDescription(tempWeather.get("description").toString());
                weatherRepository.save(w);
            }else{
                Weather w = (Weather) weatherRepository.findWeatherByDateTime(tempDate);
                w.setDateTime(Utils.stringToDateConverter(tempDateTimeString));
                w.setTemp(Double.parseDouble(tempMap.get("temp").toString()));
                w.setDescription(tempWeather.get("description").toString());
                weatherRepository.save(w);
            }
        }
        return !result.isEmpty() ? "Success" : "Failed To retrieve data";
    }

    @GetMapping(path = "/findByDate/{date}")
    @CrossOrigin
    public List<Weather> queryByDateOnly(@PathVariable String date){
        return  weatherRepository.findWeatherByDate(date);
    }

    @GetMapping(path = "/lastDate")
    @CrossOrigin
    public JSONObject findLastDate(){
        return weatherRepository.findLastRecordDate();
    }

    @GetMapping(path = "/furthestDate")
    @CrossOrigin
    public JSONObject findFurthestDate(){
        return weatherRepository.findFurthestRecordDate();
    }
}
