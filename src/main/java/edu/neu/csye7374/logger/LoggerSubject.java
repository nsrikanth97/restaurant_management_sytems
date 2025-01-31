package edu.neu.csye7374.logger;

import java.util.*;

public class LoggerSubject {

    Map<Integer, List<LogObserver>> logObservers = new HashMap<>();

    void addObserver(int level,LogObserver logObserver){
        List<LogObserver> currentLogger = logObservers.getOrDefault(level, new ArrayList<>());
        currentLogger.add(logObserver);
        this.logObservers.put(level, currentLogger);
    }

    void removeObserver(LogObserver logObserver){
        for( Map.Entry<Integer, List<LogObserver>> entry : logObservers.entrySet()){
            entry.getValue().remove(logObserver);
        }
    }

    void notifyAllObserver(int level, String message){
        for( Map.Entry<Integer, List<LogObserver>> logObserver : logObservers.entrySet()){
            if (logObserver.getKey() == level){
                logObserver.getValue().forEach(observer -> observer.log(message));
            }
        }
    }
}
