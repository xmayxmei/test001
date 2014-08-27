package com.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel导入工具
 * @author Administrator
 *
 */
public class ExcelPoiUtil {
	
	// 设置Cell之间以空格分割
	public static String EXCEL_LINE_DELIMITER = ";";
	/**
	 * 获取excel数据
	 * 
	 * @param response
	 *            response对象
	 * @param exportFileName
	 *            导出的文件名称
	 * @return
	 * @throws Exception
	 */
	public static List<StringBuffer> getExcelData(InputStream inputStream)
			throws Exception {
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook(inputStream);
		} catch (Exception e) {
			wb = new XSSFWorkbook(inputStream);
		}
		int numOfSheets = wb.getNumberOfSheets();

		if (numOfSheets > 0) {
			Sheet sheet = wb.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			for (int i = 0; i <= lastRowNum; i++) {
				Row row = sheet.getRow(i);
				int lastCellNum = row.getLastCellNum();
				// 创建字符创缓冲区
				StringBuffer buffer = new StringBuffer();
				for (int j = 0; j < lastCellNum; j++) {
					Cell cell = row.getCell(j);

					String cellvalue = "";
					if (null != cell) {
						// 判定当前Cell的Type
						switch (cell.getCellType()) {

						// 假如当前Cell的Type为NUMERIC
						case HSSFCell.CELL_TYPE_NUMERIC: {

							// 判定当前的cell是否为Date
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								// 假如是Date类型则，取得该Cell的Date值
								Date date = cell.getDateCellValue();
								// 把Date转换本钱地格式的字符串
								cellvalue = cell.getDateCellValue()
										.toLocaleString();
							}
							// 假如是纯数字
							else {
								// 取得当前Cell的数值
								double num = cell.getNumericCellValue();
								cellvalue = String.valueOf(num);
//								Integer num = new Integer(
//										(int) cell.getNumericCellValue());
								if(num<-180||num>180) {
									Integer num2 = new Integer(
										(int) cell.getNumericCellValue());
									cellvalue = String.valueOf(num2);
								}
							}
							break;
						}
						// 假如当前Cell的Type为STRIN
						case HSSFCell.CELL_TYPE_STRING: {
							// 取得当前的Cell字符串
							cellvalue = cell.getStringCellValue().replaceAll(
									"'", "''");
							if(cellvalue.indexOf("°")>0&&cellvalue.indexOf("′")>0&&cellvalue.indexOf("″")>0) {
								double jiao=Double.parseDouble(cellvalue.substring(cellvalue.indexOf("°")+1,cellvalue.indexOf("′")));
								String f=cellvalue.substring(cellvalue.indexOf("′")+1,cellvalue.indexOf("″"));
								double fen=Double.parseDouble(cellvalue.substring(cellvalue.indexOf("′")+1,cellvalue.indexOf("″")));
								int du=Integer.parseInt(cellvalue.substring(0,cellvalue.indexOf("°")));
								double finalpoint=du+(jiao*60+fen)/3600;
								cellvalue=String.valueOf(finalpoint);
							}
							break;
						}
						// 默认的Cell值
						default:
							cellvalue = "";
						}
					}

					// 在每个字段之间插进分割符
					buffer.append(cellvalue).append(EXCEL_LINE_DELIMITER);

				}
				// 放入list
				list.add(buffer);
			}
		}

		return list;
	}
	
	

}
