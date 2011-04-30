package sem.android;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.os.Bundle;


public class Map extends MapActivity  {
	private MapView mapView;
	private MapController mc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		

		
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map); //use map.xml Layout
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true); //add Zoomcontrols
		mc = mapView.getController();
		startup(); //created a new startup method for other classes
    }
	
	//startup method for other classes
	public void startup(){
		
	}
	
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
	public MapView getMapView() {
		return mapView;
	}

	public MapController getMc() {
		return mc;
	}
	
	//calculate a GeoPoint from latidue or longitude
	public GeoPoint calculateGeoPoint(double lat ,double lng ){
		GeoPoint gp = new GeoPoint((int)(lat * 1E6), (int)(lng * 1E6));
		return gp;
		
	}
    
}
