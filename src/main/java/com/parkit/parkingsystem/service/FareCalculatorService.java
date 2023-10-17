package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.text.DecimalFormat;

public class FareCalculatorService {


    private Double getTwoPoints(Double input){
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(input));
    }

    private Double getDuration(Ticket ticket){
        double inDays = ticket.getInTime().getDay();
        double outDays = ticket.getOutTime().getDay();

        double durationHours = (outDays - inDays)*24;
        System.out.println("durationDays " + (outDays - inDays));


        double inHours = ticket.getInTime().getHours();
        double outHours = ticket.getOutTime().getHours();
        System.out.println("durationHours " + (outHours - inHours));
         durationHours += outHours - inHours;


        double inMinutes = ticket.getInTime().getMinutes();
        double outMinutes = ticket.getOutTime().getMinutes();

        System.out.println("durationMinutes " + (outMinutes - inMinutes));
        durationHours += (outMinutes - inMinutes)/60;  
        return getTwoPoints(durationHours);
    }

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
    
        double duration = getDuration(ticket);

        System.out.println("duration " + duration + "   " + ticket.getId());

        if(duration <= 0.5){
            System.out.println("free of charge " );
            duration = 0;
        }
        

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}