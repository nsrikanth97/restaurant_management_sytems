package edu.neu.csye7374.logger;

public class InfoLogger extends AbstractLogger{

    public InfoLogger(int levels){
        this.levels = levels;
    }

    @Override
    public void display(String msg, LoggerSubject loggerSubject){
        loggerSubject.notifyAllObserver(levels, msg);
    }
}
