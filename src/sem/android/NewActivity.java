package sem.android;

import android.app.Activity;
import android.os.Bundle;

public class NewActivity extends Activity {
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
    }
}