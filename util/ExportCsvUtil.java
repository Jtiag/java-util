/** 
 * 项目名称:网站分析
 * 文件名：ExportCsvUtil.java
 * author:Administrator
 * 版本信息： 
 * 日期：2015年11月17日
 * Copyright 颢云科技 2015 版权所有 
 */
package com.gome.wa.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 导出csv格式
 */
public class ExportCsvUtil {
	
	/** CSV文件列分隔符 */  
    private static final String CSV_COLUMN_SEPARATOR = ",";  
  
    /** CSV文件列分隔符 */  
    private static final String CSV_RN = "\r\n";  
  
//    /** 
//     *  
//     * 将检索数据输出的对应的csv列中 
//     * */  
//    public static String formatCsvData(LinkedHashMap<String,ShopSaleInfo > resultMap,  
//            String displayColNames) {  
//  
//        StringBuffer buf = new StringBuffer();  
//  
//        String[] displayColNamesArr = null;  
//  
//        displayColNamesArr = displayColNames.split(",");  
//  
//        // 输出列头  
//        for (int i = 0; i < displayColNamesArr.length; i++) {  
//            buf.append(displayColNamesArr[i]).append(CSV_COLUMN_SEPARATOR);  
//        }  
//        buf.append(CSV_RN);  
//  
//        if (null != resultMap) {  
//            // 输出数据  
//        	for(Iterator it = resultMap.entrySet().iterator();it.hasNext();){
//    			Entry<String, ShopSaleInfo> entry = (Entry<String, ShopSaleInfo>)it.next();
//    			ShopSaleInfo shopSaleInfo = entry.getValue();
//    			buf.append(shopSaleInfo.getUserId()).append(CSV_COLUMN_SEPARATOR);
//    			//buf.append(shopSaleInfo.getUsername()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getPhone()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getPv()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getUv()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getSaleCode()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getBranchNo2()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getBranchName2()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getPayOrderCount()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getPayOrderMoney()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getCompleteOrderCount()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getCompleteOrderMoney()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getRefundOrderCount()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getRefundOrderMoney()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getReturnOrderCount()).append(CSV_COLUMN_SEPARATOR);
//    			buf.append(shopSaleInfo.getReturnOrderMoney()).append(CSV_COLUMN_SEPARATOR);
//    			 buf.append(CSV_RN);   // 换行符
//    		}
//           /* for (int i = 0; i < resultMap.size(); i++) {  
//            	ShopSaleInfo saleInfo = resultMap.;
//            	 buf.append(data.get(i)).append(  
//                         CSV_COLUMN_SEPARATOR);  
////                for (int j = 0; j < matchColNamesMapArr.length; j++) {  
////                   
//////                    buf.append(data.get(i).get(matchColNamesMapArr[j])).append(  
//////                    		CSV_COLUMN_SEPARATOR);  
////                }  
//               
//            }  */
//           
//        }  
//        return buf.toString();  
//    }  
    
    
    /**
     * 将检索数据输出的对应的csv列中 
     * @param dataList
     * @param matchList
     * @param headStr
     * @return
     */
    public static String formatCsvData(List dataList, List<String> matchList,String headStr) {  
    	StringBuffer buf = new StringBuffer();  
        String[] headStrArry = null;  
        headStrArry = headStr.split(",");  
        // 输出列头  
        for (int i = 0; i < headStrArry.length; i++) {  
            buf.append(headStrArry[i]).append(CSV_COLUMN_SEPARATOR);  
        }  
        buf.append(CSV_RN);  
        JSONArray DataArray = JSONArray.fromObject(dataList);
    	try {
			for (int i = 0; i < DataArray.size(); i++) {
				JSONObject dataObj = (JSONObject) DataArray.get(i);
				for (int j = 0; j < matchList.size(); j++) {
					String matchStr = (String) matchList.get(j);
					buf.append(dataObj.getString(matchStr)).append(CSV_COLUMN_SEPARATOR);
				}
				 buf.append(CSV_RN);   // 换行符
			}
		} catch (Exception e) {
		}
        
    	return buf.toString();  
    }
    
    
    
    public static void exportCsv(String title, String content,  
            HttpServletResponse response) throws IOException {  
  
        // 设置文件名称编码
    	String fileName=new String(title.getBytes("GBK"), "ISO-8859-1");
        // 读取字符编码  
        String csvEncoding = "Utf-8";
  
        // 设置响应  
        response.setCharacterEncoding(csvEncoding);  
        response.setContentType("text/csv; charset=" + csvEncoding);  
        response.setHeader("Pragma", "public");  
        response.setHeader("Cache-Control", "max-age=30");  
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".csv");    
        // 写出响应  
        OutputStream os = response.getOutputStream();  
        os.write(content.getBytes("GBK"));  
        os.flush();  
        os.close();  
    }  
  
    
    
		
    	
}
