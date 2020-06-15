export class WeatherModel{
    dateTime : Date;
    temp : Number;
    description : string;
    time : string;

    constructor(dateTime : Date, temp : Number, description : string){
        this.dateTime = dateTime;
        this.temp = temp;
        this.description = description;
        this.time = this.dateTime.toTimeString();
    }

}