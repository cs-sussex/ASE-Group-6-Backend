package uk.ac.sussex.group6.backend.Services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import uk.ac.sussex.group6.backend.Payloads.LongLatCoordinates;
import uk.ac.sussex.group6.backend.Payloads.PoliceResponse;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;

@Service
public class PoliceService {

    public PoliceResponse generateData(LongLatCoordinates longLatCoordinates) {
        int numberOfCrimes = 0;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String yearAsString = String.valueOf(year);
        int month = cal.get(Calendar.MONTH);

        ArrayList<String> dates = new ArrayList<>();
        for (int i = month; i != 0; i--) {
            String monthAsString = String.valueOf(i);
            if (monthAsString.length() == 1) {
                monthAsString = "0" + monthAsString;
            }
            dates.add(yearAsString + "-" + monthAsString);
        }
        for (String date : dates) {
            JSONArray array = getData(longLatCoordinates, date);
            numberOfCrimes += array.length();
        }
        return new PoliceResponse(numberOfCrimes);
    }


    private JSONArray getData(LongLatCoordinates longLatCoordinates, String date) {
        try {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < longLatCoordinates.getLonglats().size(); i++) {
                s.append(longLatCoordinates.getLonglats().get(i));
                if (i < longLatCoordinates.getLonglats().size() - 1) {
                    s.append(":");
                }
            }
            URL url = new URL("https://data.police.uk/api/crimes-street/all-crime?poly="+ s +"&date=" + date);//your url i.e fetch com.boogieSearch.boogieSearch.data from .
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Connection","keep-alive");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode()+conn.getResponseMessage());
            }
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            JSONArray jsonArray = new JSONArray(responseStrBuilder.toString());
            conn.disconnect();
            return jsonArray;

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
            return null;
        }
    }
}
