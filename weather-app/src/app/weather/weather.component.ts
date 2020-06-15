import { Component, OnInit } from '@angular/core';
import { NgbDateStruct, NgbCalendar, NgbDatepicker } from '@ng-bootstrap/ng-bootstrap';
import { Observable, throwError } from 'rxjs';
import { WeatherModel } from './weather-model'
import { WeatherService } from './weather.service';
import { DateModel } from './date-model';
import { IndexedSlideList } from 'ngx-bootstrap/carousel/models';

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent implements OnInit {
  findByDateUrl = 'http://localhost:8080/weather/findByDate/';
  findMaxDate = 'http://localhost:8080/weather/furthestDate/';
  findMinDate = 'http://localhost:8080/weather/lastDate/';
  inputDate: string;
  result : WeatherModel[];
  maxDate : DateModel;
  minDate : DateModel;
  errorMessage = "";
  
  constructor(private calendar: NgbCalendar, private weatherService : WeatherService) { 
    this.weatherService.getDate(this.findMaxDate).subscribe(maxDate => this.maxDate = maxDate);
    this.weatherService.getDate(this.findMinDate).subscribe(minDate => this.minDate = minDate);
  }

  
  ngOnInit(): void {
     
  }

  model: NgbDateStruct;
  date: { year: number, month: number, day:number };
  dp: NgbDatepicker;

  selectToday() {
    this.model = this.calendar.getToday();
  }

  setCurrent() {
    //Current Date
    this.dp.navigateTo()
  }
  setDate() {
    //Set specific date
    this.dp.navigateTo({ year: 2013, month: 2, day: 3});
    
  }

  navigateEvent(event) {
    this.date = event.next;
    console.log(this.date.year + '-' + this.date.month + '-' + this.date.day);
  }
  changeEvent(event) {
    this.getWeatherData(this.model.year + '-' + this.model.month + '-' + this.model.day);
    
  }
  
  getWeatherData(inputDate : string) : void {
    this.weatherService.getWeather(this.findByDateUrl + inputDate).subscribe(result => this.result = result,);
    if(this.result.length == 0){
      this.errorMessage = "There is no awailable weather data of that date"
    }else{
      this.errorMessage = "";
    }
  }
}
