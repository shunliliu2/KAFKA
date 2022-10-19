package com.hxocr.idfpserver._31_oberver;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Idfp {

    Logger logger;

    private IdfpType currentWeather;
    private final List<IdfpObserver> observers;


    public Idfp() {
        observers = new ArrayList<>();
        currentWeather = IdfpType.SUNNY;
    }

    public void addObserver(IdfpObserver obs) {
        observers.add(obs);
    }

    public void removeObserver(IdfpObserver obs) {
        observers.remove(obs);
    }

    /**
     * Makes time pass for weather.
     */
    public void timePasses() {
        IdfpType[] values = IdfpType.values();

        currentWeather = values[(currentWeather.ordinal() + 1) % values.length];
        logger.info("The weather changed to {}.", currentWeather);
        notifyObservers();
    }

    private void notifyObservers() {
        observers.forEach(item -> {
            item.update(currentWeather);
        });
    }
}