package com.hci.activitydj;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Button;


public class SettingActivity extends PreferenceActivity {
	
	private EditTextPreference serverAddress;
	private CheckBoxPreference networkConnection;
	private ApplicationCenter appCenter;
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        setContentView(R.layout.setting);
        
        /** get instance **/
        appCenter = (ApplicationCenter) this.getApplication();
        serverAddress = (EditTextPreference) findPreference(getResources().getString(R.string.server_address_key));
        networkConnection = (CheckBoxPreference) findPreference(getResources().getString(R.string.network_connection_key));
        
        // show latest address if have
        if (serverAddress.getText() != null)
        	serverAddress.setSummary(getResources().getString(R.string.server_address_summary_setted) + serverAddress.getText());
        
        /** button click listener setup **/
        
       Button backMain = (Button) findViewById(R.id.back_main);
       backMain.setOnClickListener(new View.OnClickListener() {
		
    	   @Override
    	   public void onClick(View view) {
    		   Intent toMain = new Intent(getBaseContext(), MainActivity.class);
    		   startActivity(toMain);
    		   //finish();
    	   }
       });
       
       /** set preference listener **/
       
       serverAddress.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
		
    	   @Override
    	   public boolean onPreferenceChange(Preference preference, Object newValue) {
    		   String addr = newValue.toString();
    		   if (addr == null || addr.isEmpty())
    			   serverAddress.setSummary(getResources().getString(R.string.server_address_summary));
    		   else
    			   serverAddress.setSummary(getResources().getString(R.string.server_address_summary_setted) + addr);
    		   return true;
    	   }
       });
       
       networkConnection.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
		
    	   @Override
    	   public boolean onPreferenceChange(Preference preference, Object newValue) {
    		   if ((Boolean)newValue) {
    			   return appCenter.setupNetwork(serverAddress.getText(), SettingActivity.this);
    		   } else {
    			   appCenter.disconnectNetwork();
    		   }
    		   return true;
    	   }
       });
       
	}

}
