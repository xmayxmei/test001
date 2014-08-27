/**
 *-----------------------------------------------------------------------------
 * @ Copyright(c) 2012~2015   Technology Development Department. All Rights Reserved.
 *-----------------------------------------------------------------------------
 * FILE  NAME             : java
 * DESCRIPTION            :
 * PRINCIPAL AUTHOR       : Shenzhen Technology Development Department MAIIP Project Team
 * SYSTEM NAME            : MAIIP
 * MODULE NAME            : MAIIP 
 * LANGUAGE               : Java
 * DATE OF FIRST RELEASE  :
 *-----------------------------------------------------------------------------
 * @ Created on October 04, 2012
 * @ Release 1.0.0.0
 * @ Version 1.0
 * -----------------------------------------------------------------------------------
 * Date	       Author	      Version     Description
 * -----------------------------------------------------------------------------------
 * 2013-5-13    JiangChunJing   1.0 	  Initial Create
 * -----------------------------------------------------------------------------------
 */

package com.cfwx.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author JiangChunJing
 * @Description 
 */
public class ExcelUtils
{
	
	private static final Log		LOG						= LogFactory.getLog(ExcelUtils.class);
	
	
	/**
	 * 导出
	 * @param titleList：标题列<ul>
	 * <li>map中必须包含参数:title,beanAttribute,columnWidth(选填)</li>
	 * <li>例如：list:{map:{title:"列1",beanAttribute:"id",columnWidth:100}</li>
	 * <li>map:{title:"列2",beanAttribute:"name",columnWidth:100}</li>
	 * </ul> 
	 * @param dataList:获取的参数集合
	 * @param fileName:导出的文件名称，最好以功能名能
	 * @param response
	 * @throws Exception
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static void export(List<Map<String,String>> titleList,List dataList,String fileName,HttpServletResponse	response)throws Exception, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException{
		
		XSSFCell cell = null;
		CreationHelper createHelper = null;
		OutputStream outStream = null;
		try
		{
			// 创建共组薄
			Workbook workBook =  new XSSFWorkbook();
			XSSFSheet sheet = (XSSFSheet) workBook.createSheet("文件导出");
			createHelper = workBook.getCreationHelper();
			XSSFRow hssfRow = sheet.createRow(0);

			/*1.写入标题*/
			for(int i = 0; i<titleList.size();i++){
				Map<String,String> tempMap  = titleList.get(i);
				
				cell = hssfRow.createCell(i);
				cell.setCellValue(createHelper.createRichTextString(tempMap.get("title").toString()));
				//设置列宽
				if(tempMap.get("columnWidth")!= null){
					sheet.setColumnWidth(i,Integer.parseInt((tempMap.get("columnWidth").toString())));
				}
				else{
					sheet.autoSizeColumn(i);
				}
				cell.setCellStyle(getCellStyle(workBook));//设置单元格样式
			}
			
			/*2.写入内容*/
			int size = dataList.size();
			
			for(int i =0;i<size;i++){
				Object tempMSH = dataList.get(i);
				hssfRow = sheet.createRow(i+1);
				
				//从第二行开始
				for(int in = 0; in<titleList.size();in++){
					
					Map<String,String> tempMap  = titleList.get(in);
					cell = hssfRow.createCell(in);
					
					String cellValue =PropertyUtils.getProperty(tempMSH, tempMap.get("beanAttribute"))==null?"": PropertyUtils.getProperty(tempMSH, tempMap.get("beanAttribute").toString()).toString();
					
					cell.setCellValue(createHelper.createRichTextString(cellValue));
				}
			}
			
			for(int i = 0; i<titleList.size();i++){
				sheet.autoSizeColumn(i);
			}
			OutputStream os = new FileOutputStream("D:\\e.xlsx");
			workBook.write(os);
			os.close();
		}
		finally
		{
			if(outStream != null){
				outStream.close();
			}
		}
	}
	
	
	/**
	 * <ul>
	 * <li>获取excel页面(Sheet)</li>
	 * <li>如果文件能直接获取文件名,使用该方法</li>
	 * </ul>
	 * @param excelFile:excel文件
	 * @return excel第一页
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private   static Sheet getFileSheet(File excelFile) throws Exception{
		Sheet sheet = null;
		String excelFileName = excelFile.getName();
		//由于apache.poi.jar对excel类型读取方式不同，所以要判断类型
		String fileType = excelFileName.substring(excelFileName.lastIndexOf(".")+1, excelFileName.length());
		boolean isExcel2003 = fileType.toUpperCase().equals("XLS") ? false : true;
		InputStream inputStream= null;
		try
		{
			inputStream = new FileInputStream(excelFile);
			Workbook wookbook = null;
			//创建对Excel工作簿文件的引用
			if (isExcel2003)
			{
				wookbook = new XSSFWorkbook(inputStream);
			}
			else
			{
				wookbook = new HSSFWorkbook(inputStream);
			}
			// 在Excel文档中，第一sheet的缺省索引是0
			sheet = wookbook.getSheetAt(0);
		}
		catch (IOException e)
		{
			LOG.error(e.getMessage(),e);
			e.printStackTrace();
		}
		finally{
				try
				{
					inputStream.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
		}
		return sheet;
	}
	
	/**
	 * 文件类型校验
	 * @param 文件名称
	 * @param 类型校验数组(如{"xlsx","xls"})
	 * @return 是否匹配
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private  static  boolean checkFileType(String fileName,String[] checkType)throws Exception{
		
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		for (String str : checkType)
			if (str.equals(fileType))
				return true;
		return false;
	}
	
	private static XSSFCellStyle getCellStyle (Workbook workbook){
		// 设置字体
        XSSFFont font = (XSSFFont) workbook.createFont();
        
        font.setFontHeightInPoints((short) 14); //字体高度
        font.setColor(XSSFFont.BOLDWEIGHT_BOLD); //字体颜色
        font.setFontName("黑体"); //字体
        
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        cellStyle.setFont(font);
        
        return cellStyle;
        
	}
	
}
