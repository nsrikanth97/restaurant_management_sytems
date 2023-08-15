package edu.neu.csye7374.logger;

public class FileLogger implements LogObserver{

    @Override
    public void log(String message){
        System.out.println("File Message: " + message);
    }
}
