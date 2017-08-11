package com.gome.wa.util;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by jiangtao7 on 2017/7/5.
 */
public class SortByMapValue  implements Comparator<Map.Entry<String, Integer>>{
    @Override
    // 以value 降序排
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        return o2.getValue() - o1.getValue();
    }
}
