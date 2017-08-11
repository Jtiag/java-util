package com.gome.wa.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExportPOIUtil2007 {
	
	public static void export(String title,List<String> headList,List<String> matchList,List<String> typeList,List dataList,HttpServletResponse response) throws IOException{
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("default");
		
        sheet.setDefaultColumnWidth(15);
        
        XSSFCellStyle style = wb.createCellStyle();  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置居中样式  
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 水平居右 
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
        
      //创建头部
        XSSFRow row_head = sheet.createRow((int) 0);  
    	for (int i = 0; i < headList.size(); i++) {    
    		XSSFCell cell = row_head.createCell(i);    
    		cell.setCellValue(headList.get(i).toString());
    		cell.setCellStyle(style);  
    		//sheet.autoSizeColumn(i);    
    	}
    	
    	//创建数据
    	JSONArray DataArray = JSONArray.fromObject(dataList);
    	for (int i = 0; i < DataArray.size(); i++) {
			JSONObject dataObj = (JSONObject) DataArray.get(i);
			XSSFRow row = sheet.createRow(i+1); 
    		for (int j = 0; j < matchList.size(); j++) {
				String matchStr = matchList.get(j);
				String type = typeList.get(j);
				XSSFCell cell = row.createCell(j);
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
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close(); 
	}
	
	

}
