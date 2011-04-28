package sem.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RouteActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Route tab");
        setContentView(textview);
    }
}