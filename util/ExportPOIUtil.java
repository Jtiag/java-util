import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class ExportPoiUtil {
	/**
	 * 导出实体类 各个字段的类型（String,Integer）
	 */
	public static List<String> typeList = new ArrayList<>();
	/**
	 * 获取对应的 name值(fildsName)
	 */
	public static List<String> nameList = new ArrayList<>();

	private static Log logger = LogFactory.getLog(ExportPoiUtil.class);

	/**
	 * 导出成poi格式的excel
	 *
	 * @param title    导出文件名 称
	 * @param headList excel 头部列名 注意其顺序需要和 domain里面的相同
	 * @param nameList 数据匹配字符串
	 * @param typeList 数据类型
	 * @param dataList 数据
	 * @param response
	 * @throws IOException
	 */
	public static void export(String title, List<String> headList, List<String> nameList, List<String> typeList, List dataList, HttpServletResponse response) throws IOException {
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
		// 水平居右
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		// 垂直居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		//创建头部
		HSSFRow rowHead = sheet.createRow(0);
		for (int i = 0; i < headList.size(); i++) {
			HSSFCell cell = rowHead.createCell(i);
			cell.setCellValue(headList.get(i));
			cell.setCellStyle(style);
			//sheet.autoSizeColumn(i);
		}

		//创建数据
		JSONArray dataArray = JSONArray.fromObject(dataList);
		for (int i = 0; i < dataArray.size(); i++) {
			JSONObject dataObj = (JSONObject) dataArray.get(i);
			HSSFRow row = sheet.createRow(i + 1);
			for (int j = 0; j < nameList.size(); j++) {
				String matchStr = nameList.get(j);
				String type = typeList.get(j);
				HSSFCell cell = row.createCell(j);
				try {
					if ("Long".equals(type)) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(dataObj.getLong(matchStr));
					} else if ("Double".equals(type)) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(dataObj.getDouble(matchStr));
					} else if ("Integer".equals(type)) {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(dataObj.getInt(matchStr));
					} else {
						// 设置数据格式为String
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(dataObj.getString(matchStr));
					}
					cell.setCellStyle(style);
				} catch (Exception e) {
					logger.debug(e.getMessage());
				}

			}
		}

		response.setContentType("application/vnd.ms-excel");
		String fileName = new String(title.getBytes("GBK"), "ISO-8859-1");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	/**
	 * @param fields 导出字段的fields
	 * 如通过指定domain的Field[] fields = adDataInfo.getClass().getDeclaredFields();
	 */
	public static void setExportTypeAndMatchList(Field[] fields) {
		for (Field field : fields) {
			nameList.add(field.getName());
			String subType = field.getType().getName();
			subType = subType.substring(subType.lastIndexOf(".") + 1);
			typeList.add(subType);
		}
	}
}
