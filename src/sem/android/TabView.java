package sem.android;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;


//main App for creating Tabs
public class TabView extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, RouteActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    //for Navigation Tab
	    spec = tabHost.newTabSpec("Routes").setIndicator("Route",
	                      res.getDrawable(R.drawable.ic_tab_route))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    //tab for adding new Meetings
	    intent = new Intent().setClass(this, NewActivity.class);
	    spec = tabHost.newTabSpec("Maps").setIndicator("New",
	                      res.getDrawable(R.drawable.ic_tab_new))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    //tab for editing details of the meeting.
	    intent = new Intent().setClass(this, DetailsActivity.class);
	    spec = tabHost.newTabSpec("Details").setIndicator("Detail",
	                      res.getDrawable(R.drawable.ic_tab_details))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}
}