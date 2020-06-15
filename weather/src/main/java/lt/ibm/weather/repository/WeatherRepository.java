package lt.ibm.weather.repository;

import lt.ibm.weather.model.Weather;
import net.minidev.json.JSONObject;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    Weather findWeatherByDateTime(Date dateTime);

    @Query(value = "SELECT * from weather where CAST(date_time AS DATE) = :date", nativeQuery = true)
    List<Weather> findWeatherByDate(@Param("date") String date);

    @Query(value = "SELECT CAST(min(date_time) AS DATE) as date FROM `weather`", nativeQuery = true)
    JSONObject findLastRecordDate();

    @Query(value = "SELECT CAST(max(date_time) AS DATE) as date FROM `weather`", nativeQuery = true)
    JSONObject findFurthestRecordDate();
}
