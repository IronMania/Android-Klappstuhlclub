package sem.android;

import java.util.ArrayList;
import java.util.List;


import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class MapItemOverlay extends ItemizedOverlay {
	private List<OverlayItem> items;
	private Drawable marker;
 
	//constructor
	public MapItemOverlay(Drawable defaultMarker) {
		super(defaultMarker);
		items = new ArrayList<OverlayItem>();
		marker = defaultMarker;
	}
 
	@Override
	protected OverlayItem createItem(int index) {
		return (OverlayItem)items.get(index);
	}
 
	@Override
	public int size() {
		return items.size();
 
	}
 
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.google.android.maps.ItemizedOverlay#draw(android.graphics.Canvas,
	 * com.google.android.maps.MapView, boolean)
	 */
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		boundCenterBottom(marker); //defining position of marker/pic
 
	}
	
	//adding overlay item to the List
	public void addItem(OverlayItem item) {
		items.add(item);
		populate();
	}
 
}
