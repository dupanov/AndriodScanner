import java.io.BufferedWriter;
import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by admin on 16.03.2016.
 */
public class UserThread extends Thread {
    String AdminHost;
    String TM1ServerName;
    String user;
    String password;
    String[] cubesToRead;
    String[] cubesToWrite;
    String[] writeRestrictedDimensions;
    ArrayList<String>[] writeRestrictedDimensionElements;
    String[] readRestrictedDimensions;
    ArrayList<String>[] readRestrictedDimensionElements;
    boolean debug;
    BufferedWriter logFile;
    int sessionId;
    DateFormat dateFormat;
    int MAX_NUMBER_TO_WRITE;
    int secondsBetweenActions;



    public UserThread(String adminHost, String tm1ServerName, String user, String password) {
    }
}
