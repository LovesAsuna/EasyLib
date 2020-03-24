package org.sct.plugincore.util.function.language;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.sct.plugincore.data.CoreData;
import org.sct.plugincore.util.function.stack.StackTrace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author LovesAsuna
 * @date 2020/2/22 21:45
 */

public class TranslateUtil {

    public String translate(String langFrom, String langTo, String word) {
        try {
            String urlString = "https://translate.googleapis.com/translate_a/single?" +
                    "client=gtx&" +
                    "sl=" + langFrom +
                    "&tl=" + langTo +
                    "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36");
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            conn.disconnect();
            return parseResult(response.toString());
        } catch (IOException e) {
            return null;
        }
    }

    private static String parseResult(String inputJson) {
        JsonNode root = null;
        try {
            root = CoreData.getMapper().readTree(inputJson);
        } catch (JsonProcessingException e) {
            StackTrace.printStackTrace(e);
        }
        String result = root.get(0).get(0).get(0).asText();
        return result;
    }

}
