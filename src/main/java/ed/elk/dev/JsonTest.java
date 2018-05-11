package ed.elk.dev;

import com.oracle.javafx.jmx.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


public class JsonTest {

    public Map<String, Object> readJsonFromUrl(String url) throws JSONException {
        String inline = "";
        JSONObject object = null;
        Map<String, Object> jmap = new HashMap<String, Object>();

        try {
            URL urls = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode != 200)
                throw new RuntimeException("httpResponseCode : " + responsecode);
            else {
                Scanner sc = new Scanner(urls.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                System.out.println("\nJSON Response in String format");
                System.out.println(inline);
                sc.close();

            }


            org.json.simple.parser.JSONParser parse = new org.json.simple.parser.JSONParser();

            List<Map<String, Object>> jsonLMapList = new ArrayList<Map<String, Object>>();


            System.out.println("\n");
            jsonLMapList = (List<Map<String, Object>>) parse.parse(inline);

            int index = 0;
            for (Map<String, Object> mop : jsonLMapList) {
                for (Map.Entry<String, Object> entry : mop.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    jmap.put(key, value);
                    //System.out.println(jmap.keySet() +" : "+ jmap.values());
                }

                index++;
            }

            for (int i = 0; i < jsonLMapList.size(); i++) {
                //System.out.println(jsonLMapList.get(i));
                System.out.println("\n");
                for (Map.Entry<String, Object> entry : jsonLMapList.get(i).entrySet()) {
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                }
            }


//            JSONArray arr = (JSONArray) parse.parse(inline);

//            System.out.println(arr);


            /*for (Object o : arr.toArray()) {
                System.out.println(o);
            }*/

            //object.put("coin",arr);

//            System.out.println(object.get("code"));


//            for (Object o : arr) {
//                JSONObject obj = (JSONObject) o;
//                String code = obj.get("code").toString();
//                Double trdPrice = (Double)obj.get("tradePrice");
//                String candleDateTimeKst = (String)obj.get("candleDateTimeKst");
//
//                System.out.println("code : " + code.substring(11));
//                System.out.println("tradePrice : " + String.format("%.2f", trdPrice));
//                System.out.println("Date : " + candleDateTimeKst.substring(0,10));
//                System.out.println("Time : " + candleDateTimeKst.substring(11,19));
//                System.out.println("\n");
//            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return jmap;


    }

    public static void main(String[] args) {
        JsonTest jt = new JsonTest();

        String[] myCoin = {"BTC"};//,"ICX","ETH","ETC","QTUM","ADA","XRP"};

        for (String coin : myCoin) {
            jt.readJsonFromUrl("https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-" + coin + "&count=2&to");
        }
    }
}