package edu.neu.csye7374.logger;

public class DebugLogger extends AbstractLogger{
        public DebugLogger(int levels){
            this.levels = levels;
        }

        @Override
        public void display(String msg, LoggerSubject loggerSubject){
            loggerSubject.notifyAllObserver(levels, msg);
        }

}
