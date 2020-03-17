package utils;

import com.alibaba.fastjson.JSONObject;

public class JSONParser {
    JSONObject internalJSON;

    public String getLoanStatus(JSONObject jo) {
        String status = "";
        try {
            JSONObject internalJSON = jo.getJSONObject("data");
            JSONObject internalJSON2 = internalJSON.getJSONObject("app");
            JSONObject internalJSON3 = internalJSON2.getJSONObject("loan");
            status = internalJSON3.getString("status");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}