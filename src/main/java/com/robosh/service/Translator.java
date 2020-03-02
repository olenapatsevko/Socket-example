package com.robosh.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.SneakyThrows;

public class Translator {


  private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
  private static final String CLIENT_SECRET = "PUBLIC_SECRET";
  private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

  @SneakyThrows
  public static String translate(String fromLang, String toLang, String text) {
    String jsonPayload = "{"
        + "\"fromLang\":\""
        + fromLang
        + "\","
        + "\"toLang\":\""
        + toLang
        + "\","
        + "\"text\":\""
        + text
        + "\""
        + "}";

    URL url = new URL(ENDPOINT);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
    conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
    conn.setRequestProperty("Content-Type", "application/json");

    OutputStream os = conn.getOutputStream();
    os.write(jsonPayload.getBytes());
    os.flush();
    os.close();

    int statusCode = conn.getResponseCode();
    System.out.println("Status Code: " + statusCode);
    BufferedReader br = new BufferedReader(new InputStreamReader(
        (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
    ));
    String output;
    StringBuilder result = new StringBuilder();
    while ((output = br.readLine()) != null) {
      result.append(output);
    }
    conn.disconnect();
    return result.toString();
  }

}
