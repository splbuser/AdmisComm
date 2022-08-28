package com.splb.service.utils;

import com.splb.model.dao.constant.Fields;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class VerifyReCaptcha {

    private static final Logger log = LogManager.getLogger(VerifyReCaptcha.class);

    private VerifyReCaptcha() {
    }
    public static boolean verify(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }
        try {
            URL verifyUrl = new URL(Fields.SITE_VERIFY_URL);

            HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String postParams = "secret=" + Fields.SECRET_KEY
                    + "&response=" + gRecaptchaResponse;

            conn.setDoOutput(true);

            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());

            outStream.flush();
            outStream.close();

            int responseCode = conn.getResponseCode();
            log.info("responseCode ==> {}", responseCode);

            InputStream is = conn.getInputStream();

            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            log.info("Response: {}", jsonObject);
            return jsonObject.getBoolean("success");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException(e.getMessage());
        }
    }
}

