import { Injectable } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { WeatherModel } from './weather-model';
import { Observable } from 'rxjs/Observable';
import { DateModel } from './date-model';


@Injectable({
  providedIn: 'root'
})
export class WeatherService {
  constructor(private http:HttpClient) { }

  getWeather(serviceUrl : string): Observable<WeatherModel[]>{
    return this.http.get<WeatherModel[]>(serviceUrl);
  }

  getDate(serviceUrl : string): Observable<DateModel>{
    return this.http.get<DateModel>(serviceUrl);
  }

}
