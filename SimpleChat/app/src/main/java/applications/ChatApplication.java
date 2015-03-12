package applications;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by amundada on 3/4/15.
 */
public class ChatApplication extends Application {
    public static final String YOUR_APPLICATION_ID = "fRGZRJSOSE91mK4ejT9Hz32LIwBHP6q2nR1EbSLB";
    public static final String YOUR_CLIENT_KEY = "Pi88VvDe6KP3rfrPATtWwMqZueHRuadHnJLPVqUX";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
}
