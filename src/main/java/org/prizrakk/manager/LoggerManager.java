package org.prizrakk.manager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerManager {
    ColorManager color = new ColorManager();
    public void info(String message){
        SimpleDateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss]");
        String timestamp = dateFormat.format(new Date());
        String logLine = timestamp + color.ANSI_GREEN + "[INFO]" + color.ANSI_RESET + " " + message;
        System.out.println(logLine);
    }
    public void error(String message){
        SimpleDateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss]");
        String timestamp = dateFormat.format(new Date());
        String logLine = color.ANSI_RED + timestamp + "[ERROR]" + " " + message + color.ANSI_RESET;
        System.out.println(logLine);
    }
    public void warn(String message){
        SimpleDateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss]");
        String timestamp = dateFormat.format(new Date());
        String logLine = timestamp + color.ANSI_YELLOW +"[WARN]" + color.ANSI_RESET + " " + message;
        System.out.println(logLine);
    }
    public void command(String event, String message){
        SimpleDateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss]");
        String timestamp = dateFormat.format(new Date());
        String logLine = timestamp + color.ANSI_CYAN +"["+ event +"]" + color.ANSI_RESET + " " + message;
        System.out.println(logLine);
    }


}
