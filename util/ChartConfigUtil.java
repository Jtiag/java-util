package com.gome.wa.util;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by jiangtao7 on 2017/6/29.
 */
public class ChartConfigUtil {
    public static JSONObject getChartConfig(String code, Map data) {
        try {
            String filePath = null;

            filePath = "yzcharts/" + code + ".json";
            String str = VelocityUtils.getFileContent(filePath, data);
            return JSONObject.fromObject(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
