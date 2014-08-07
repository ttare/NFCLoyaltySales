package com.comtrade.edit2014sales;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import models.CardDetails;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.comtrade.edit2014salesTarik.R;
import com.koushikdutta.ion.Ion;



public class NFCCardActivity extends Activity {

	public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private CardDetails kartica;
 
    private TextView mTextView, broj, ime, prezime, noviPoeni, posjeta, poeni;
    private Button upisi;
    ImageView slika;
    private String urlSlike;
    private NfcAdapter mNfcAdapter;
    public boolean upis = false;
    private Dialog dialog;
    private Tag _tag;
    
	SharedPreferences preferences;
	String points;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfccard);
		
		
		ime = (TextView) findViewById(R.id.txtIme);
		prezime = (TextView) findViewById(R.id.txtPrezime);
		posjeta = (TextView) findViewById(R.id.txtPosjeta);
		broj = (TextView) findViewById(R.id.txtBroj);
		poeni = (TextView) findViewById(R.id.txtPoeni);
		slika = (ImageView) findViewById(R.id.imageView1);
		noviPoeni = (TextView) findViewById(R.id.txtNoviPoeni);
		upisi = (Button) findViewById(R.id.btnUpis);
		
		
		preferences = PreferenceManager.getDefaultSharedPreferences(NFCCardActivity.this);
		points = preferences.getString("loyaltyPoints", "");
		if(! points.equalsIgnoreCase(""))
			noviPoeni.setText(points);
		else
			noviPoeni.setText("0");

		
		upisi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				upis = true;
				dialog = new Dialog(NFCCardActivity.this);
				dialog.setContentView(R.layout.custom_dialog_upis);
				dialog.setTitle("Prinesite loyalty karticu.");
				dialog.setCancelable(false);
				dialog.show();
				
				dialog.setOnKeyListener(new OnKeyListener() {
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode,
							KeyEvent event) {
						if (keyCode == event.KEYCODE_BACK) {
							upis = false;
							dialog.dismiss();
						}
						return true;
					}
				});

			}
		});
		
			mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
	
			if (mNfcAdapter == null) {
				// Stop here, we definitely need NFC
				Toast.makeText(this, "Uređaj ne podržava NFC.",
						Toast.LENGTH_LONG).show();
				finish();
				return;
	
			}
	
			if (!mNfcAdapter.isEnabled()) {
				Toast.makeText(this, "NFC nije uključen. Molimo uključite NFC.", Toast.LENGTH_LONG).show();
			} else {
			//	Toast.makeText(this, "NFC is enabled.", Toast.LENGTH_LONG).show();
			}

				handleIntent(getIntent());
	}

	
	
	@Override
	protected void onResume() {
		super.onResume();

		/**
		 * It's important, that the activity is in the foreground (resumed).
		 * Otherwise an IllegalStateException is thrown.
		 */
		setupForegroundDispatch(this, mNfcAdapter);
	}

	@Override
	protected void onPause() {
		/**
		 * Call this before onPause, otherwise an IllegalArgumentException is
		 * thrown as well.
		 */
		stopForegroundDispatch(this, mNfcAdapter);

		super.onPause();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		/**
		 * This method gets called, when a new Intent gets associated with the
		 * current activity instance. Instead of creating a new activity,
		 * onNewIntent will be called. For more information have a look at the
		 * documentation.
		 * 
		 * In our case this method gets called, when the user attaches a Tag to
		 * the device.
		 */
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
			String action = intent.getAction();
	    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
	        
	        String type = intent.getType();
	        if (MIME_TEXT_PLAIN.equals(type)) {
	            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	            if(!upis)
	            	new NdefReaderTask().execute(tag);
	            if(upis){
	            	 if(writableTag(tag)) {  
	                     //writeTag here  
	                     WriteResponse wr = writeTag(getTagAsNdef(), tag);  
	                     String message = (wr.getStatus() == 1? "Novi bodovi uspješno uneseni." : "Failed: "); 
	                     Toast.makeText(getBaseContext(), message,Toast.LENGTH_SHORT).show();  
	                } else {  
	                     Toast.makeText(getBaseContext(),"This tag is not writable",Toast.LENGTH_SHORT).show();  
	                     //Sounds.PlayFailed(context, silent);  
	                }  
	    		}
	             
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

	private NdefMessage createRecord(String text) throws UnsupportedEncodingException {

	    //create the message in according with the standard
	    String lang = "en";
	    byte[] textBytes = text.getBytes();
	    byte[] langBytes = lang.getBytes("US-ASCII");
	    int langLength = langBytes.length;
	    int textLength = textBytes.length;

	    byte[] payload = new byte[1 + langLength + textLength];
	    payload[0] = (byte) langLength;

	    // copy langbytes and textbytes into payload
	    System.arraycopy(langBytes, 0, payload, 1, langLength);
	    System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

	    NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
	    NdefRecord[] records = { recordNFC };
	    NdefMessage message = new NdefMessage(records); 
	    return message;
	}
	
	 public WriteResponse writeTag(NdefMessage message, Tag tag) {  
	     int size = message.toByteArray().length;  
	     String mess = "";  
	     try {  
	       Ndef ndef = Ndef.get(tag);  
	       if (ndef != null) {  
	         ndef.connect();  
	         if (!ndef.isWritable()) {  
	           return new WriteResponse(0,"Tag is read-only");  
	         }  
	         if (ndef.getMaxSize() < size) {  
	           mess = "Tag capacity is " + ndef.getMaxSize() + " bytes, message is " + size  
	               + " bytes.";  
	           return new WriteResponse(0,mess);  
	         }  
	         //ndef.writeNdefMessage(message); 
	         ndef.writeNdefMessage(createRecord(kartica.UpisKartice()));  
	         upis = false;
	         dialog.dismiss();
	         NFCCardActivity.this.finish();
	         points = "";
	         
	     //    if(writeProtect) ndef.makeReadOnly();  
	         mess = "Wrote message to pre-formatted tag.";  
	         return new WriteResponse(1,mess);  
	       } else {  
	         NdefFormatable format = NdefFormatable.get(tag);  
	         if (format != null) {  
	           try {  
	             format.connect();  
	             format.format(message);  
	             mess = "Formatted tag and wrote message";  
	             return new WriteResponse(1,mess);  
	           } catch (IOException e) {  
	             mess = "Failed to format tag.";  
	             return new WriteResponse(0,mess);  
	           }  
	         } else {  
	           mess = "Tag doesn't support NDEF.";  
	           return new WriteResponse(0,mess);  
	         }  
	       }  
	     } catch (Exception e) {  
	       mess = "Failed to write tag";  
	       return new WriteResponse(0,mess);  
	     }  
	   }  
	   
	 private class WriteResponse {  
	        int status;  
	        String message;  
	        WriteResponse(int Status, String Message) {  
	             this.status = Status;  
	             this.message = Message;  
	        }  
	        public int getStatus() {  
	             return status;  
	        }  
	        public String getMessage() {  
	             return message;  
	        }  
	   }  
	   private boolean writableTag(Tag tag) {  
	     try {  
	       Ndef ndef = Ndef.get(tag);  
	       if (ndef != null) {  
	         ndef.connect();  
	         if (!ndef.isWritable()) {  
	           Toast.makeText(getBaseContext(),"Tag is read-only.",Toast.LENGTH_SHORT).show();  
	           //Sounds.PlayFailed(context, silent);  
	           ndef.close();   
	           return false;  
	         }  
	         ndef.close();  
	         return true;  
	       }   
	     } catch (Exception e) {  
	       Toast.makeText(getBaseContext(),"Failed to read tag",Toast.LENGTH_SHORT).show();  
	       //Sounds.PlayFailed(context, silent);  
	     }  
	     return false;  
	   } 
	   private NdefMessage getTagAsNdef() {  
	        boolean addAAR = false;  
	        String uniqueId = "smartwhere.com/nfc.html";      
	     byte[] uriField = uniqueId.getBytes(Charset.forName("US-ASCII"));  
	     byte[] payload = new byte[uriField.length + 1];       //add 1 for the URI Prefix  
	     payload[0] = 0x01;                        //prefixes http://www. to the URI  
	     System.arraycopy(uriField, 0, payload, 1, uriField.length); //appends URI to payload  
	     NdefRecord rtdUriRecord = new NdefRecord(  
	       NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_URI, new byte[0], payload);  
	     if(addAAR) {  
	          // note: returns AAR for different app (nfcreadtag)  
	          return new NdefMessage(new NdefRecord[] {  
	       rtdUriRecord, NdefRecord.createApplicationRecord("com.tapwise.nfcreadtag")  
	     });   
	     } else {  
	          return new NdefMessage(new NdefRecord[] {  
	               rtdUriRecord});  
	     }  
	   } 
	
	
	/**
	 * @param activity
	 *            The corresponding {@link Activity} requesting the foreground
	 *            dispatch.
	 * @param adapter
	 *            The {@link NfcAdapter} used for the foreground dispatch.
	 */
	public static void setupForegroundDispatch(final Activity activity,
			NfcAdapter adapter) {
		final Intent intent = new Intent(activity.getApplicationContext(),
				activity.getClass());
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		final PendingIntent pendingIntent = PendingIntent.getActivity(
				activity.getApplicationContext(), 0, intent, 0);

		IntentFilter[] filters = new IntentFilter[1];
		String[][] techList = new String[][] {};

		// Notice that this is the same filter as in our manifest.
		filters[0] = new IntentFilter();
		filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
		filters[0].addCategory(Intent.CATEGORY_DEFAULT);
		try {
			filters[0].addDataType(MIME_TEXT_PLAIN);
		} catch (MalformedMimeTypeException e) {
			throw new RuntimeException("Check your mime type.");
		}

		adapter.enableForegroundDispatch(activity, pendingIntent, filters,
				techList);
	}

	/**
	 * @param activity
	 *            The corresponding {@link BaseActivity} requesting to stop the
	 *            foreground dispatch.
	 * @param adapter
	 *            The {@link NfcAdapter} used for the foreground dispatch.
	 */
	public static void stopForegroundDispatch(final Activity activity,
			NfcAdapter adapter) {
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
                String[] rezultat = result.split("\\*");
                kartica = new CardDetails(rezultat[0], rezultat[2], rezultat[3], rezultat[5], 
                		rezultat[1], rezultat[4]);
                
                broj.setText(kartica.getBrojKartice());
                ime.setText(kartica.getImeKupca());
                prezime.setText(kartica.getPrezimeKupca());
                poeni.setText(kartica.getBrojBodova());
                posjeta.setText(kartica.getDatumZadnjePromjene());
                urlSlike = kartica.getUrlSlikeKupca();
                ImageView slika = (ImageView)NFCCardActivity.this.findViewById(R.id.imageView1);
                Ion.with(slika)
                .load(kartica.getUrlSlikeKupca());
                
                float novi = Float.parseFloat(kartica.getBrojBodova()) + Float.parseFloat(noviPoeni.getText().toString());
                kartica.setBrojBodova(String.valueOf(novi));
		   	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		 		Date date = new Date();
	   	         
	   	        kartica.setDatumZadnjePromjene(dateFormat.format(date));
                
            }
	    }
	}
}



