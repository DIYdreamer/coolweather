package com.example.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.PublicKey;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.conn.params.ConnConnectionParamBean;

import android.location.Address;

public class HttpUtil {

	public static void sendHttpRequest(final String address, final HttpCallBackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpsURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpsURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection .getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String Line;
					while ((Line = reader.readLine()) != null) {
						response.append(Line);
					}
					if (listener != null) {
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onError(e);
					}
				}finally{
					if (connection != null) {
						connection.disconnect();
						
					}
				}
			}
		});
	}
}
