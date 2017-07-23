package com.example.simona.healthquest.log;

/**
 * Created by Risto Muchev on 02-May-16.
 */
public class Logger {

    private static boolean enabled = true;

    private boolean init = false;

    private static Logger instance = null;

    private ILogger log = null;

    private Logger(){}

    public void init(ILogger log){
        if(!init) {
            this.log = log;
            init = true;
        }
    }

    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }

    private ILogger getLog() {
        return log;
    }

    public static void log(LogLevel logLevel, String message) {
        if (enabled){
            ILogger iLogger = getInstance().getLog();
            if(iLogger!=null){
                iLogger.log(logLevel, "HealthLog", message);
            }
        }
    }

    public static void log(LogLevel logLevel, String tag, String message){
        if (enabled) {
            ILogger iLogger = getInstance().getLog();
            if(iLogger!=null){
                getInstance().getLog().log(logLevel, tag, message);
            }
        }
    }
}
