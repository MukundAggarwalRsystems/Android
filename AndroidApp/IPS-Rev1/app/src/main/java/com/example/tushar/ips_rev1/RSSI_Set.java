package com.example.tushar.ips_rev1;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.JsonReader;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tushar on 30-Jun-16.
 */
public class RSSI_Set {

    int Values_RSSI_A, Values_RSSI_B, Values_RSSI_C;
    String SourceDeviceAddress[], DiscoveredDeviceAddress;

    public RSSI_Set(Context context, String[] _SourceDeviceAddress, String _DiscoveredDeviceAddress)
    {
        this.Values_RSSI_A = 0;
        this.Values_RSSI_B = 0;
        this.Values_RSSI_C = 0;
        this.SourceDeviceAddress = _SourceDeviceAddress;
        this.DiscoveredDeviceAddress = _DiscoveredDeviceAddress;
        this.setValue(context);
    }

    protected void setValue(final Context context){
        try {
            AsyncHttpClient httpClient = new AsyncHttpClient();

            JSONArray sourceDevices = new JSONArray();
            sourceDevices.put(this.SourceDeviceAddress[0]);
            sourceDevices.put(this.SourceDeviceAddress[1]);
            sourceDevices.put(this.SourceDeviceAddress[2]);
            final JSONObject jsondata = new JSONObject();
            jsondata.put("SourceDeviceAddress", sourceDevices);
            jsondata.put("DiscoveredDeviceAddress", this.DiscoveredDeviceAddress);
            StringEntity entity = new StringEntity(jsondata.toString());

            sendPost(context, "http://192.168.16.99:1234/api/btapp/getDeviceData", jsondata);
            /*
            httpClient.post(context, "http://192.168.16.99:1234/api/btapp/getDeviceData", entity, "application/json", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int i, Header[] headers, byte[] responseBody) {
                    Toast.makeText(context, "Success",Toast.LENGTH_LONG).show();
                    if (responseBody != null && responseBody.length > 0) {
                        try {
                            Toast.makeText(context, "Success 1",Toast.LENGTH_LONG).show();
                            JSONArray jsonArray = new JSONArray(new String(responseBody));
                            Values_RSSI_A = jsonArray.getInt(0);
                            Values_RSSI_B = jsonArray.getInt(1);
                            Values_RSSI_C = jsonArray.getInt(2);
                        } catch (Exception e) {

                        }
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(context, "Failed : " + i,Toast.LENGTH_LONG).show();
                }
            });*/
        }catch(Exception ex){}
    }

    private void sendPost(Context context, String url, JSONObject parameters) {

        InputStream inputStream = null;
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            StringEntity se = new StringEntity(parameters.toString());
            httpPost.setEntity(se);

            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null) {
                //Toast.makeText(context, "Work", Toast.LENGTH_LONG).show();
                //JSONTokener tokenizer = new JSONTokener(new InputStreamReader(inputStream, "UTF-8").toString());
                String str = IOUtils.toString(inputStream);
                JSONObject dsf = new JSONObject(str.substring(1, str.length() - 1).replace("\\",""));
                JSONArray jsonArray = dsf.getJSONArray("Values");

                this.Values_RSSI_A = Integer.parseInt(jsonArray.get(0).toString());
                this.Values_RSSI_B = Integer.parseInt(jsonArray.get(1).toString());
                this.Values_RSSI_C = Integer.parseInt(jsonArray.get(2).toString());
            } else {
                Toast.makeText(context, "Data not found.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
