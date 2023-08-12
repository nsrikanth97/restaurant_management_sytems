package edu.neu.csye7374.logger;

public class ErrorLogger extends AbstractLogger{

    public ErrorLogger(int levels){
        this.levels = levels;
    }

    @Override
    public void display(String msg, LoggerSubject loggerSubject){
        loggerSubject.notifyAllObserver(levels, msg);
    }
}
