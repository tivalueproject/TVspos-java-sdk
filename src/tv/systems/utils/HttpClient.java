package tv.systems.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class HttpClient {

    public static String get(String url) throws IOException {

        HttpURLConnection con = null;

        try {
            URL webURL = new URL(url);
            if (webURL.getProtocol().equals("https")) {
                con = (HttpsURLConnection) webURL.openConnection();
            } else {
                con = (HttpURLConnection) webURL.openConnection();
            }
            con.setRequestMethod("GET");
            return getResponse(con);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    public static String post(String url, String json) throws IOException {

        HttpURLConnection con = null;
        byte[] postData = json.getBytes(StandardCharsets.UTF_8);

        try {
            URL webURL = new URL(url);
            if (webURL.getProtocol().equals("https")) {
                con = (HttpsURLConnection) webURL.openConnection();
            } else {
                con = (HttpURLConnection) webURL.openConnection();
            }
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            return getResponse(con);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    private static String getResponse(HttpURLConnection con) throws IOException {
        int statusCode = con.getResponseCode();
        InputStream inputStream;
        if (statusCode >= 200 && statusCode < 400) {
            inputStream = con.getInputStream();
        } else {
            inputStream = con.getErrorStream();
        }
        StringBuilder content;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            content = new StringBuilder();

            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }
        return content.toString();
    }
}
