/** 
 * 项目名称:网站分析
 * 文件名：ExportPOIUtil.java
 * author:Administrator
 * 版本信息： 
 * 日期：2015年11月19日
 * Copyright 颢云科技 2015 版权所有 
 */
package com.gome.wa.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 */
public class ExportPOIUtil {
	
	
	/**
	 * 导出成poi格式的excel
	 * @param title   导出文件名 称
	 * @param headList   excel 头部列名
	 * @param matchList  数据匹配字符串
	 * @param typeList   数据类型
	 * @param dataList   数据
	 * @param response
	 * @throws IOException
	 */
	public static void export(String title,List<String> headList,List<String> matchList,List<String> typeList,List dataList,HttpServletResponse response) throws IOException{
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("default");
        
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        
        HSSFCellStyle style = wb.createCellStyle();  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置居中样式  
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 水平居右 
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中  
        
        //创建头部
        HSSFRow row_head = sheet.createRow((int) 0);  
    	for (int i = 0; i < headList.size(); i++) {    
    		HSSFCell cell = row_head.createCell(i);    
    		cell.setCellValue(headList.get(i).toString());
    		cell.setCellStyle(style);  
    		//sheet.autoSizeColumn(i);    
    	}
    	
    	//创建数据
    	JSONArray DataArray = JSONArray.fromObject(dataList);
    	for (int i = 0; i < DataArray.size(); i++) {
			JSONObject dataObj = (JSONObject) DataArray.get(i);
			HSSFRow row = sheet.createRow(i+1); 
    		for (int j = 0; j < matchList.size(); j++) {
				String matchStr = matchList.get(j);
				String type = typeList.get(j);
				HSSFCell cell = row.createCell(j);
				try {
					if("Long".equals(type)){
						cell.setCellType(cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(dataObj.getLong(matchStr));
					}else if("Double".equals(type)){
						cell.setCellType(cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(dataObj.getDouble(matchStr));
					}else if("Integer".equals("type")){
						cell.setCellType(cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(dataObj.getInt(matchStr));
					}else{
						cell.setCellType(cell.CELL_TYPE_STRING); // 设置数据格式为String
						cell.setCellValue(dataObj.getString(matchStr));
					}
					cell.setCellStyle(style); 
				} catch (Exception e) {
				}	
				
			}
		}
    	
		 response.setContentType("application/vnd.ms-excel");    
        String fileName=new String(title.getBytes("GBK"), "ISO-8859-1");
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();   
	}
	
}
