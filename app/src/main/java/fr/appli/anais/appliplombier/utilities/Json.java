package fr.appli.anais.appliplombier.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Json {

    public static String monJson = recupJSON();

    public static boolean isConnected(ConnectivityManager cm) {
        ConnectivityManager connMgr = cm;
        Log.d("STATE", "is connected");
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            GET(urls[0]);
            return "b";
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("STATE", "update finis");
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static void GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                updateJSON(inputStream);
                monJson = recupJSON();
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }


    public static void updateJSON(InputStream is) {

        File dcim = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "bd.json");

        try {
            Log.d("WOW", dcim.getCanonicalPath());
            String monJson = convertInputStreamToString(is);
            Log.d("WOW", monJson);
            OutputStream outStream = new FileOutputStream(dcim);
            outStream.write(monJson.getBytes());
            is.close();
            outStream.close();
        } catch (IOException ie) {
            Log.d("STATE", "Problème dans la mise à jour du fichier JSON");
        }
    }

    public static String recupJSON() {
        StringBuilder res = new StringBuilder();
        try {
            File dcim = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "bd.json");
            BufferedReader br = new BufferedReader(new FileReader(dcim));
            String line = br.readLine();
            while (line != null) {
                res.append(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException fe) {
            Log.d("STATE", "recupJson failed");
        } catch (IOException ie) {
            Log.d("STATE", "recupJson failed");
        }
        return res.toString();
    }
}
