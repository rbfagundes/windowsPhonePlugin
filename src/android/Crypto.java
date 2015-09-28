package br.com.fullgauge.sitradmobile;

import java.io.UnsupportedEncodingException;

import android.os.Bundle;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Crypto extends CordovaPlugin {

	public CallbackContext callback;
	public static String TAG = "CryptoPlugin";
	
	/** Executes the request.	 
	 * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return                Whether the action was valid. 
	 */
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		Log.d(TAG,"callbackId="+callbackContext.getCallbackId());
		this.callback = callbackContext;
		String input = "";
		//PluginResult result =  new PluginResult(PluginResult.Status.NO_RESULT);

		try {
		
			// Input String
			if(args.length() > 0) {
				
				input = args.getString(0);
				//Log.d(TAG,"input="+input);
		    }
			
			Log.d(TAG,"action="+action);

			// if has some input
			if (input != null && !"".equals(input)) {

				if (action.equals("crypt")) {
					JSONObject crypted = crypt(input);
					//Log.d(TAG, "crypted=" + crypted);
					Log.d(TAG, "action crypt");
					callbackContext.success(crypted);
					return true;
					//result = new PluginResult(PluginResult.Status.OK, crypted);
				} else if (action.equals("decrypt")) {
					JSONObject decrypted = decrypt(input);
					//Log.d(TAG, "decrypted=" + decrypted);
					Log.d(TAG, "action decrypt");
					callbackContext.success(decrypted);
					return true;
					//result = new PluginResult(PluginResult.Status.OK, decrypted);
				} else if(action.equals("decodeBase64")){
					JSONObject decodedBase64 = decodeBase64(input);
					//Log.d(TAG, "decodedBase64=" + decodedBase64);
					Log.d(TAG, "action decodeBase64");
					callbackContext.success(decodedBase64);
					return true;
					//result = new PluginResult(PluginResult.Status.OK, decodedBase64);
				} else if(action.equals("encodeBase64")){
					JSONObject encodedBase64 = encodeBase64(input);
					//Log.d(TAG, "encodedBase64=" + encodedBase64);
					Log.d(TAG, "action encodeBase64");
					callbackContext.success(encodedBase64);
					return true;
					//result = new PluginResult(PluginResult.Status.OK, encodedBase64);
				}
				else {
					Log.d(TAG, "action not valid");
					// action not valid
					return false;
					//result = new PluginResult(
					//		PluginResult.Status.INVALID_ACTION);
				}
			}
			
			
		} catch (JSONException e) {			
			e.printStackTrace();	
			// some exception
			Log.d(TAG,"JSONException="+e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
			//result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);		
		} catch (Exception e) {
			
			e.printStackTrace();	
			// some exception
			Log.d(TAG,"Exception="+e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
			//result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
		}
			
		
		return false;
	}

	

	/**
	 * Encode base 64
	 * 
	 * @param toEncode String do encode
	 * @return encoded result
	 * @throws JSONException if any error
	 */
    private JSONObject encodeBase64(String toEncode) throws JSONException {
		
		JSONObject jsonResult = new JSONObject();
		
		try {
		
			StringBuffer sb = new StringBuffer(toEncode);
			
			String base64 = new String(Base64.encode(sb.toString().getBytes("ISO-8859-1")));
			
			//Log.d(TAG,"base64="+base64);
			Log.d(TAG,"encodeBase64 ok");

			jsonResult.put("result", base64);
			
		} catch (UnsupportedEncodingException e) {
			throw new JSONException(e.getMessage());
		}
		
		return jsonResult;
	}
	
    /**
     * Decode base64
     * 
     * @param toDecode String to decode
     * @return Decoded result
     * @throws JSONException if any error
     */
	private JSONObject decodeBase64(String toDecode) throws JSONException {
		
		JSONObject jsonResult = new JSONObject();
		
		try {
					
			String base64 = new String(Base64.decode(toDecode.toString()),"ISO-8859-1" );
			
			//Log.d(TAG,"base64="+base64);
			Log.d(TAG,"decodeBase64 ok");

			jsonResult.put("result", base64);
			
		} catch (UnsupportedEncodingException e) {
			throw new JSONException(e.getMessage());
		}
		
		return jsonResult;
	}
	
	
	/**
	 * Crypt the value
	 * 
	 * @param String text to be crypted
	 * @return JSONObject representation of directory list. e.g
	 *         {"result":result to be sent}
	 * @throws JSONException
	 */
	private JSONObject crypt(String toCrypt) throws JSONException {
		
		JSONObject jsonResult = new JSONObject();
		
		try {
		
			StringBuffer sb = new StringBuffer(toCrypt);
			
			FgCrypto.Criptografar(sb, sb.length());
			
			String base64 = new String(Base64.encode(sb.toString().getBytes("ISO-8859-1")));
			
			//Log.d(TAG,"base64="+base64);
			Log.d(TAG,"crypt ok");
			
			// ignore protocol first characters "װ" this will be included by Java Script communication  
			jsonResult.put("result", base64);
			
		} catch (UnsupportedEncodingException e) {
			throw new JSONException(e.getMessage());
		}
		
		return jsonResult;
	}
	
	
	/**
	 * Decrypt the value
	 * 
	 * @param String text to be decrypted
	 * @return JSONObject representation of directory list. e.g
	 *         {"result":result to be sent}
	 * @throws JSONException
	 */
	private JSONObject decrypt(String crypted) throws JSONException {
		
		JSONObject jsonResult = new JSONObject();
			
		try {
			String filtered;
			if (crypted.charAt(0) ==  '\uFFFD') {
				filtered = crypted.substring(1, crypted.length() - 2);
			} else {
				filtered = crypted;
			}
			
			Log.d(TAG,"filtered ="+filtered);
			
			StringBuffer sb = new StringBuffer(new String(Base64.decode(filtered.toString()),"ISO-8859-1" ));
			
			FgCrypto.Decriptografar(sb, sb.length());
			
			//Log.d(TAG,"base64="+sb);
			Log.d(TAG,"decrypt ok");
			
			jsonResult.put("result", sb);
			
		} catch (UnsupportedEncodingException e) {
			throw new JSONException(e.getMessage());
		}
		
		return jsonResult;
	}
	
	
	
	
	
}
