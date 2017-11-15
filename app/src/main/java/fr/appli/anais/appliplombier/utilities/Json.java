package fr.appli.anais.appliplombier.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import fr.appli.anais.appliplombier.R;

public class Json {

    public static boolean isConnected(ConnectivityManager cm) {
        Log.d("STATE", "is connected");
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null)
            result.append(line);

        inputStream.close();
        return result.toString();
    }

    private static void GET(String url) {
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                updateJSON(inputStream);
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
    }

    private static void updateJSON(InputStream is) {

        File dcim = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "bd.json");

        try {
            Log.d("JSON_UPDATE", dcim.getCanonicalPath());
            String monJson = convertInputStreamToString(is);
            Log.d("JSON_UPDATE", monJson);
            OutputStream outStream = new FileOutputStream(dcim);
            outStream.write(monJson.getBytes());
            is.close();
            outStream.close();
        } catch (IOException ie) {
            Log.d("JSON_UPDATE", "Problème dans la mise à jour du fichier JSON");
        }
    }

    public static String recupJSON(Context context) {
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
        } catch (IOException ioe) {
            // si le fichier téléchargé lors de la première connexion n'existe pas,
            // on se rabat sur le fichier interne de l'appli
            return recupJSONLocal(context);
        }
        return res.toString();
    }

    private static String recupJSONLocal(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.bd_locale);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("Text Data", byteArrayOutputStream.toString());
        return byteArrayOutputStream.toString();
    }

    public static class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            GET(urls[0]);
            return "b";  //le string retourner n'est pas utile dans ce cas
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("STATE", "update finis");
        }
    }
}
