package at.abraxas.amarino.plugins.phonestate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import at.abraxas.amarino.AmarinoIntent;
import at.abraxas.amarino.plugins.R;

public class EditActivity extends Activity {
	
	private static final String TAG = "TimeTick EditActivity";
	
	static final String KEY_VISUALIZER = "at.abraxas.amarino.plugins.phonestate.visualizer";
	static final String KEY_PLUGIN_ID = "at.abraxas.amarino.plugins.phonestate.id";
	
	Button okBtn;
	Button discardBtn;
	int pluginId;
	
	private boolean cancelled = true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.phonestate_edit);
        
        Intent intent = getIntent();
        if (intent != null){
        	pluginId = intent.getIntExtra(AmarinoIntent.EXTRA_PLUGIN_ID, -1);
	        
	        // we need to know the ID Amarino has assigned to this plugin
	        // in order to identify sent data
	        PreferenceManager.getDefaultSharedPreferences(EditActivity.this)
				.edit()
				.putInt(KEY_PLUGIN_ID, pluginId)
				.commit();
        }

       
        okBtn = (Button)findViewById(R.id.saveBtn);
        discardBtn = (Button)findViewById(R.id.discardBtn);
        
        okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelled = false;
				finish();
			}
		});
        
        discardBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }

	@Override
	public void finish() {
		if (cancelled) {
			setResult(RESULT_CANCELED);
		}
		else {
			final Intent returnIntent = new Intent();
			
			returnIntent.putExtra(AmarinoIntent.EXTRA_PLUGIN_NAME, getString(R.string.phonestate_plugin_name));
			returnIntent.putExtra(AmarinoIntent.EXTRA_PLUGIN_DESC, getString(R.string.phonestate_plugin_desc));
			returnIntent.putExtra(AmarinoIntent.EXTRA_PLUGIN_SERVICE_CLASS_NAME, "at.abraxas.amarino.plugins.phonestate.BackgroundService"); 
			returnIntent.putExtra(AmarinoIntent.EXTRA_PLUGIN_ID, pluginId);	
			returnIntent.putExtra(AmarinoIntent.EXTRA_PLUGIN_VISUALIZER, AmarinoIntent.VISUALIZER_TEXT);
			returnIntent.putExtra(AmarinoIntent.EXTRA_VISUALIZER_MIN_VALUE, 0f);
			returnIntent.putExtra(AmarinoIntent.EXTRA_VISUALIZER_MAX_VALUE, 2f);
			
			setResult(RESULT_OK, returnIntent);
		}
		super.finish();
	}
	
    
}