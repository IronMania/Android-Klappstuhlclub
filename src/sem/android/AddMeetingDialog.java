package sem.android;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.View.OnClickListener;

public class AddMeetingDialog extends Dialog implements OnClickListener{
	Button okButton;
	Button cancelButton;
	static final int ADD_MEETING_DIALOG_ID = 0;
	
	public AddMeetingDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/** Design the dialog in main.xml file */
		setContentView(R.layout.addmeetingdialog);
		okButton = (Button) findViewById(R.id.okButton);
		cancelButton = (Button) findViewById(R.id.cancelButton);
		okButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		
	}
	
	
	public void onClick(View v) {
	/** When OK Button is clicked, save the dialog */
	if (v == okButton)this.dismiss();
	//TODO save the stuff
	/** When OK Button is clicked, save the dialog */
	if (v == cancelButton)
	this.dismiss();
	}
	
}
