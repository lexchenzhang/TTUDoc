package com.ttu.main;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {

	private Workbook wb;
	private Sheet sheet;
	private Row row;
 
	public ExcelParser(String filepath) {
		if(filepath==null){
			return;
		}
		String ext = filepath.substring(filepath.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(filepath);
			if(".xls".equals(ext)){
				wb = new HSSFWorkbook(is);
			}else if(".xlsx".equals(ext)){
				wb = new XSSFWorkbook(is);
			}else{
				wb = null;
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.toString());
		} catch (IOException e) {
			System.err.println(e.toString());
		}
	}
	
	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param InputStream
	 * @return String 表头内容的数组
	 * @author zengwendong
	 */
	public String[] readExcelTitle() throws Exception{
		if(wb==null){
			throw new Exception("Workbook对象为空！");
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("colNum:" + colNum);
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			// title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = row.getCell(i).getCellFormula();
		}
		return title;
	}
 
	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 * @author zengwendong
	 */
	public Map<Integer, Map<Integer,Object>> readExcelContent() throws Exception{
		if(wb==null){
			throw new Exception("Workbook对象为空！");
		}
		Map<Integer, Map<Integer,Object>> content = new HashMap<Integer, Map<Integer,Object>>();
		
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			Map<Integer,Object> cellValue = new HashMap<Integer, Object>();
			while (j < colNum) {
				Object obj = getCellFormatValue(row.getCell(j));
				cellValue.put(j, obj);
				j++;
			}
			content.put(i, cellValue);
		}
		return content;
	}
 
	/**
	 * 
	 * 根据Cell类型设置数据
	 * 
	 * @param cell
	 * @return
	 * @author zengwendong
	 */
	private Object getCellFormatValue(Cell cell) {
		Object cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			case NUMERIC:// 如果当前Cell的Type为NUMERIC
			case FORMULA: {
				// 判断当前的cell是否为Date
				if (DateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式
					// data格式是带时分秒的：2013-7-10 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();
					// data格式是不带带时分秒的：2013-7-10
					java.util.Date date = cell.getDateCellValue();
					cellvalue = date;
				} else {// 如果是纯数字
 
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case STRING:// 如果当前Cell的Type为STRING
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:// 默认的Cell值
				cellvalue = "";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}
	
	/** 
     * 读取Excel2003的主表数据 （单个sheet）
     * @param filePath 
     * @return 
     */  
	private static List<SubInfo> readFromXLS2003(String filePath) {  
        File excelFile = null;// Excel文件对象  
        InputStream is = null;// 输入流对象  
        String cellStr = null;// 单元格，最终按字符串处理  
        List<SubInfo> employeeList = new ArrayList<SubInfo>();// 返回封装数据的List  
        SubInfo employee = null;// 每一个雇员信息对象  
        try {  
            excelFile = new File(filePath);  
            is = new FileInputStream(excelFile);// 获取文件输入流  
            HSSFWorkbook workbook2003 = new HSSFWorkbook(is);// 创建Excel2003文件对象  
            HSSFSheet sheet = workbook2003.getSheetAt(0);// 取出第一个工作表，索引是0  
            // 开始循环遍历行，表头不处理，从1开始  
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {  
            	HSSFRow row = sheet.getRow(i);// 获取行对象 
            	employee = new SubInfo();// 实例化Student对象  
                if (row == null) {// 如果为空，不处理  
                    continue;  
                }  
                // 循环遍历单元格  
                for (int j = 0; j < row.getLastCellNum(); j++) {  
                    HSSFCell cell = row.getCell(j);// 获取单元格对象  
                    if (cell == null) {// 单元格为空设置cellStr为空串  
                        cellStr = "";  
                    } else if (cell.getCellType() == CellType.BOOLEAN) {// 对布尔值的处理  
                        cellStr = String.valueOf(cell.getBooleanCellValue());  
                    } else if (cell.getCellType() == CellType.NUMERIC) {// 对数字值的处理  
                        cellStr = cell.getNumericCellValue() + "";
                    } else {// 其余按照字符串处理  
                        cellStr = cell.getStringCellValue();  
                    }  
                    // 下面按照数据出现位置封装到bean中  
//                    if (j == 0) {  
//                    	employee.setName(cellStr);  
//                    } else if (j == 1) {  
//                    	employee.setGender(cellStr);  
//                    } else if (j == 2) {  
//                    	employee.setAge(new Double(cellStr).intValue());  
//                    } else if (j == 3) {  
//                    	employee.setDepartment(cellStr);  
//                    } else if(j == 4){  
//                    	employee.setSalary(new Double(cellStr).intValue());  
//                    }else {  
//                    	employee.setDate(cellStr);  
//                    }  
                }  
                employeeList.add(employee);// 数据装入List  
            }  
		} catch (IOException e) {  
            e.printStackTrace();  
        } finally {// 关闭文件流  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return employeeList;  
    }  
	
	/**
	 * 读取Excel2003的表头
	 * @param filePath 需要读取的文件路径
	 * @return
	 */
	public static String[] readHeaderFromXLS2003(String filePath){
		String[] excelTitle = null;
		FileInputStream is = null;
		try{
			File excelFile = new File(filePath);
			is = new FileInputStream(excelFile);
			HSSFWorkbook workbook2003 = new HSSFWorkbook(is);
			//循环读取工作表
			for (int i = 0; i < workbook2003.getNumberOfSheets(); i++) {
				 HSSFSheet hssfSheet = workbook2003.getSheetAt(i);			 
				//*************获取表头是start*************
		            HSSFRow sheetRow = hssfSheet.getRow(i);  
		            excelTitle = new String[sheetRow.getLastCellNum()];
		            for (int k = 0; k < sheetRow.getLastCellNum(); k++) {
		            	HSSFCell hssfCell = sheetRow.getCell(k);
		            	excelTitle[k] = hssfCell.getStringCellValue();
//		            	System.out.println(excelTitle[k] + " ");
		            }
		    		//*************获取表头end*************
			}
		}catch (IOException e) {  
            e.printStackTrace();  
        } finally {// 关闭文件流  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  		
		return excelTitle;
	}
	
	/**
	 * 读取Excel2007的示例方法 （单个sheet）
	 * @param filePath
	 * @return
	 */
	public static List<SubInfo> readFromXLSX2007(String filePath) {  
        File excelFile = null;// Excel文件对象  
        InputStream is = null;// 输入流对象  
        String cellStr = null;// 单元格，最终按字符串处理  
        List<SubInfo> studentList = new ArrayList<SubInfo>();// 返回封装数据的List  
        SubInfo employee = null;// 每一个雇员信息对象  
        try {  
            excelFile = new File(filePath);  
            is = new FileInputStream(excelFile);// 获取文件输入流  
//            XSSFWorkbook workbook2007 = new XSSFWorkbook(is);// 创建Excel2007文件对象
            org.apache.poi.ss.usermodel.Workbook workbook2007 = WorkbookFactory.create(is);
//            XSSFSheet sheet = workbook2007.getSheetAt(0);// 取出第一个工作表，索引是0 
            org.apache.poi.ss.usermodel.Sheet sheet = workbook2007.getSheetAt(0);
            // 开始循环遍历行，表头不处理，从1开始  
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {  
            	employee = new SubInfo();// 实例化Student对象  
//            	HSSFRow row = sheet.getRow(i);// 获取行对象 
            	Row row = sheet.getRow(i);// 获取行对象  
                if (row == null) {// 如果为空，不处理
                    continue;  
                }  
                // 循环遍历单元格  
                for (int j = 0; j < row.getLastCellNum(); j++) {  
//                    XSSFCell cell = row.getCell(j);// 获取单元格对象 
                	Cell cell = row.getCell(j);// 获取单元格对象 
                    if (cell == null) {// 单元格为空设置cellStr为空串  
                        cellStr = "";  
                    } else if (cell.getCellType() == CellType.BOOLEAN) {// 对布尔值的处理  
                        cellStr = String.valueOf(cell.getBooleanCellValue());  
                    } else if (cell.getCellType() == CellType.NUMERIC) {// 对数字值的处理  
                        cellStr = cell.getNumericCellValue() + "";  
                    } else {// 其余按照字符串处理  
                        cellStr = cell.getStringCellValue();  
                    }  
                    // 下面按照数据出现位置封装到bean中  
//                    if (j == 0) {  
//                    	employee.setName(cellStr);  
//                    } else if (j == 1) {  
//                    	employee.setGender(cellStr);  
//                    } else if (j == 2) {  
//                    	employee.setAge(new Double(cellStr).intValue());  
//                    } else if (j == 3) {  
//                    	employee.setDepartment(cellStr);  
//                    } else if(j == 4){  
//                    	employee.setSalary(new Double(cellStr).intValue());  
//                    } else {  
//                    	employee.setDate(cellStr);  
//                    }  
                }  
                studentList.add(employee);// 数据装入List  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally {// 关闭文件流  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return studentList;  
    }  
	
	/**
	 * 读取Excel的示例方法 （多个sheet）
	 * @param filePath
	 * @return
	 */
	public List<SubInfo> readMoreSheetFromXLS(String filePath){
		List<SubInfo> employeeList = new ArrayList<SubInfo>();
		String cellStr = null;//单元格，最终按字符串处理
		//创建来自excel文件的输入流
		try {
			FileInputStream is = new FileInputStream(filePath);
			//创建WorkBook实例
			Workbook workbook = null;
			if (filePath.toLowerCase().endsWith("xls")) {//2003
				workbook = new HSSFWorkbook(is);
			}else if(filePath.toLowerCase().endsWith("xlsx")){//2007
				workbook = WorkbookFactory.create(is);
			}
			//获取excel文件的sheet数量
			int numOfSheets = workbook.getNumberOfSheets();
			//挨个遍历sheet
			for (int i = 0; i < numOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				//挨个遍历sheet的每一行
				for (Iterator<Row> iterRow = sheet.iterator();iterRow.hasNext();) {
					Row row = iterRow.next();
					SubInfo employee = new SubInfo();
					int j = 0;//标识位，用于标识第几列
					//挨个遍历每一行的每一列
					for (Iterator<Cell> cellIter = row.cellIterator();cellIter.hasNext();) {
						Cell cell = cellIter.next();//获取单元格对象
						if (j == 0) {
							if (cell == null) {// 单元格为空设置cellStr为空串  
		                        cellStr = "";  
		                    } else if (cell.getCellType() == CellType.BOOLEAN) {// 对布尔值的处理  
		                        cellStr = String.valueOf(cell.getBooleanCellValue());  
		                    } else if (cell.getCellType() == CellType.NUMERIC) {// 对数字值的处理  
		                        cellStr = cell.getNumericCellValue() + "";  
		                    } else {// 其余按照字符串处理  
		                        cellStr = cell.getStringCellValue();  
		                    }  
//							employee.setName(cellStr); 
							j ++;
						}
//						employee.setGender(cellStr); j == 1
//						employee.setAge(new Double(cellStr).intValue()); j == 2
//						employee.setDepartment(cellStr); j == 3
//						employee.setSalary(new Double(cellStr).intValue());  j == 4
//						employee.setDate(cellStr); j == 5
						employeeList.add(employee);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return employeeList;
	}

}
