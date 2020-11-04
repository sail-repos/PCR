package mapcov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.*;

public class LogUtil {

    public static Logger logger;
    private static String systemType = System.getProperty("os.name");
    private static String ROOT_PATH;

    public static Logger getLogger(String name){

        if(logger != null) {
            return logger;
        }
        logger = Logger.getLogger(name);
        logger.setLevel(Level.ALL);
        FileHandler handler = null;
        try {
            handler = new FileHandler(getPath(name, ".log"),true);
            handler.setLevel(Level.ALL);
            handler.setFormatter(new LogFormatter());
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }
    public static String getPath(String name, String suffix ) {

        Date currentDate = new Date();
        if(ROOT_PATH == null){
            if (systemType.contains("Windows")) {
                ROOT_PATH = "./results/pcr-results";
            }else {
                ROOT_PATH = "./results/pcr-results";
            }
        }
        File root = new File(ROOT_PATH);
        if (!root.exists()){
            root.mkdir();
        }
        Path path = Paths.get(ROOT_PATH, name + suffix);
        try{
            File file = new File(path.toString());
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path.toString();
    }
}

class LogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        Date date = new Date();
        String sDate = date.toString();
        return           record.getMessage() + "\n";
    }
}


