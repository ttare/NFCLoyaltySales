package com.example.edit2014client;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

import android.content.Intent;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	public static final String MIME_TEXT_PLAIN = "text/plain";
	public static final String TAG = "NfcDemo";
	
	private CardDetails kartica;
	SharedPreferences preferences;
	private NfcAdapter mNfcAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		// Za civanje ostvarenih loyalty poena
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		if (mNfcAdapter == null) {
			Toast.makeText(this, "Uredjaj ne podrzava NFC.", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		if (!mNfcAdapter.isEnabled()) {
			Toast.makeText(this, "NFC nije aktivan, molimo ukljucite ga..", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "NFC je aktivan.", Toast.LENGTH_LONG).show();
		}
		handleIntent(getIntent());
		
		
	}
	// Ukoliko dodje do pauziranja ocitavanja podataka
		@Override
		protected void onResume() {
			super.onResume();
			setupForegroundDispatch(this, mNfcAdapter);
		}
		@Override
		protected void onPause() {
			stopForegroundDispatch(this, mNfcAdapter);
			super.onPause();
		}
			
		@Override
		protected void onNewIntent(Intent intent) {
			
			handleIntent(intent);
		}
		
		private void handleIntent(Intent intent) {
			String action = intent.getAction();
			if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
				
				String type = intent.getType();
				if (MIME_TEXT_PLAIN.equals(type)) {

					Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
					new NdefReaderTask().execute(tag);
					
				} else {
					Log.d(TAG, "Wrong mime type: " + type);
				}
			} else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
				
				// In case we would still use the Tech Discovered Intent
				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				String[] techList = tag.getTechList();
				String searchedTech = Ndef.class.getName();
				
				for (String tech : techList) {
					if (searchedTech.equals(tech)) {
						new NdefReaderTask().execute(tag);
						break;
					}
				}
			}
		}
		
		/**
		 * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
		 * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
		 */
		public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
			final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

			final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

			IntentFilter[] filters = new IntentFilter[1];
			String[][] techList = new String[][]{};

			// Notice that this is the same filter as in our manifest.
			filters[0] = new IntentFilter();
			filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
			filters[0].addCategory(Intent.CATEGORY_DEFAULT);
			try {
				filters[0].addDataType(MIME_TEXT_PLAIN);
			} catch (MalformedMimeTypeException e) {
				throw new RuntimeException("Check your mime type.");
			}
			
			adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
		}

		/**
		 * @param activity The corresponding {@link BaseActivity} requesting to stop the foreground dispatch.
		 * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
		 */
		public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
			adapter.disableForegroundDispatch(activity);
		}
		
		
		private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

			@Override
			protected String doInBackground(Tag... params) {
				Tag tag = params[0];
				
				Ndef ndef = Ndef.get(tag);
				if (ndef == null) {
					// NDEF is not supported by this Tag. 
					return null;
				}

				NdefMessage ndefMessage = ndef.getCachedNdefMessage();

				NdefRecord[] records = ndefMessage.getRecords();
				for (NdefRecord ndefRecord : records) {
					if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
						try {
							return readText(ndefRecord);
						} catch (UnsupportedEncodingException e) {
							Log.e(TAG, "Unsupported Encoding", e);
						}
					}
				}

				return null;
			}
			
			private String readText(NdefRecord record) throws UnsupportedEncodingException {

				byte[] payload = record.getPayload();

				// Get the Text Encoding
				String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

				// Get the Language Code
				int languageCodeLength = payload[0] & 0063;
				
				// String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
				// e.g. "en"
				
				// Get the Text
				return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
			}
			
			@Override
			protected void onPostExecute(String result) {
				if (result != null) {
					
					kartica = new CardDetails(result);
					// kreiranje novog activitya
					Intent intent = new Intent(getBaseContext(), UserActivity.class);
					intent.putExtra("brojKartice", kartica.getBrojKartice());
					intent.putExtra("urlSlikeKupca", kartica.getUrlSlikeKupca());
					intent.putExtra("imeKupca", kartica.getImeKupca());
					intent.putExtra("prezimeKupca", kartica.getPrezimeKupca());
					intent.putExtra("brojBodova", kartica.getBrojBodova());
					intent.putExtra("datumZadnjePromjene", kartica.getDatumZadnjePromjene());
					
					startActivity(intent);
					/*TextView txt = new TextView(getBaseContext());
					txt = (TextView) findViewById(R.id.textView1);
					txt.setText(kartica.getImeKupca()+ " " + kartica.getPrezimeKupca());*/
				}
			}
		}
}
