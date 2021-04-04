package cn.rzedu.sf.resource.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {	
	
	public static List<Map<String, Object>> readEXCL(InputStream stream, String fileType, int ignoreRows, String[] header) throws IOException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
	    Workbook wb = null;
	    int totalCell = header.length;
	    
	    if (fileType.equals("xls")) {
	    	wb = new HSSFWorkbook(stream);
	    } else if (fileType.equals("xlsx")) {
	    	wb = new XSSFWorkbook(stream);
	    } else {
	    	System.out.println("excel格式不正确");
	    	stream.close();
	    	return null;
	    }

	    for(int k=0; k<wb.getNumberOfSheets(); k++){
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	List<Map<String, String>> sheetContent = new ArrayList<Map<String, String>>();
	    	Sheet sheet1 = wb.getSheetAt(k);
	    	map.put("sheetTeamName", sheet1.getSheetName());   
	    	for (int i=0; i<sheet1.getPhysicalNumberOfRows(); i++) {    		
	    		//判断忽略行数
		    	if(i < ignoreRows){
		    		continue;
		    	}
		    	
		    	Row row = sheet1.getRow(i);
		    	if(row == null){
		    		continue;
		    	}
		    	//判断header是否正确	    	
		    	if(i == ignoreRows){
		    		boolean flag = true;
		    		String[] headerVals = new String[totalCell];
		    		for (int j=0; j< totalCell; j++) {
		    			Cell cell = row.getCell(j);
		    			if(cell != null){
		    				headerVals[j] = getExcelVal(cell);
		    			}else{
		    				headerVals[j] = "";
		    			}
		    		}	
		    		if(header != null && header.length>0){
		    			for(int l=0; l<header.length; l++){
			    			if(!header[l].equals(headerVals[l])){
			    				flag = false;
			    				break;
			    			}
			    		}
		    		}else{
		    			flag = false;
		    		}
		    		map.put("sheetFlag", flag);
		    		if(!flag){
		    			break;
		    		}
		    		continue;
		    	}
		    	
		    	Map<String, String> values = new HashMap<String, String>();
		    	for (int j=0; j< totalCell; j++) {
		    		Cell cell = row.getCell(j);
		    		if(cell != null){
		    			values.put(header[j], getExcelVal(cell));
		    		}    		
		    	}
		    	sheetContent.add(values);
		    }
	    	map.put("sheetContent", sheetContent);
	    	resultList.add(map);
	    }   
	    stream.close();
	    return resultList;
	}
	
	public static  Map<String, Object> readNoSheetEXCL(InputStream stream, String fileType, String[] header) throws IOException {
	    Map<String, Object> result = new HashMap<String, Object>();
	    
	    Workbook workbook = null;
	    /**文件格式校验*/
	    if ("xls".equals(fileType)) {
	    	workbook = new HSSFWorkbook(stream);
	    } else if ("xlsx".equals(fileType)) {
	    	workbook = new XSSFWorkbook(stream);
	    } else {
	    	result.put("status", "FILE_SUFFIX_ERROR");
	    	stream.close();
	    	return result;
	    }
    	
    	Sheet sheet = workbook.getSheetAt(0);
    	Integer rowNum = sheet.getPhysicalNumberOfRows();
    	int totalCell = header.length;

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    	for (int i=0; i<rowNum; i++) {
	    	Row row = sheet.getRow(i);
	    	/**表头校验*/
	    	if(i == 0) {
	    		if(!check(row, header)) {
	    			result.put("status", "EXCEL_HEADER_ERROR");
		    		return result;
	    		}
	    		int cellNum = header.length;
	    		for (int cell = 0; cell < cellNum; cell++) {
	    			Cell cellData = row.getCell(cell);
	    			header[cell] = cellData.getStringCellValue();
	    		}
	    	}
	    	if(i == 0){
	    		continue;
	    	}
	    	if(row == null){
	    		continue;
	    	}
	    	
	    	Map<String, Object> values = new HashMap<String, Object>();
	    	for (int j=0; j< totalCell; j++) {
	    		Cell cell = row.getCell(j);
	    		//if(cell != null){
	    		values.put(header[j], getExcelVal(cell));
	    		//}    		
	    	}
	    	data.add(values);
	    }
    	
    	/**空数据校验*/
    	if(data.size()==0) {
    		result.put("status", "EXCEL_DATA_NULL");
    	} else {
    		result.put("status", "success");
    		result.put("data", data);
    	}
    	
	    return result;
	}

	public static  Map<String, Object> readNoSheetEXCLWithoutHeader(InputStream stream, String fileType, String[] header) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();

		Workbook workbook = null;
		/**文件格式校验*/
		if ("xls".equals(fileType)) {
			workbook = new HSSFWorkbook(stream);
		} else if ("xlsx".equals(fileType)) {
			workbook = new XSSFWorkbook(stream);
		} else {
			result.put("status", "FILE_SUFFIX_ERROR");
			stream.close();
			return result;
		}

		Sheet sheet = workbook.getSheetAt(0);
		Integer rowNum = sheet.getPhysicalNumberOfRows();

		Row row1 = sheet.getRow(0);
		int totalCell = row1.getLastCellNum();
		header = new String[totalCell];
		int n = 0;
		for (Cell cell : row1) {
			String value = cell.getStringCellValue();
			if (value != null && !"".equals(value.trim())) {
				header[n] = value;
			}
			n++;
		}

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i=0; i<rowNum; i++) {
			Row row = sheet.getRow(i);
			/**表头校验*/
			/**表头校验*/
	    	if(i == 0) {
	    		if(!check(row, header)) {
	    			result.put("status", "EXCEL_HEADER_ERROR");
		    		return result;
	    		}
	    		int cellNum = row.getLastCellNum();
	    		for (int cell = 0; cell < cellNum; cell++) {
	    			Cell cellData = row.getCell(cell);
	    			header[cell] = cellData.getStringCellValue();
	    		}
	    	}
			if(i == 0){
				continue;
			}
			if(row == null){
				continue;
			}

			Map<String, Object> values = new HashMap<String, Object>();
			for (int j=0; j< totalCell; j++) {
				Cell cell = row.getCell(j);
				if(cell != null){
					values.put(header[j], getExcelVal(cell));
				}
			}
			data.add(values);
		}

		/**空数据校验*/
		if(data.size()==0) {
			result.put("status", "EXCEL_DATA_NULL");
		} else {
			result.put("status", "success");
			result.put("data", data);
			result.put("header", header);
		}

		return result;
	}

	
	private static String getExcelVal(Cell cell){
		String value ="";
		if(cell==null) {
			return "";
		}
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null) {
						value = new SimpleDateFormat("yyyy-MM-dd").format(date);
					} else {
						value = "";
					}
				} else {
				value = new DecimalFormat("0").format(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				if (!cell.getStringCellValue().equals("")) {
					value = cell.getStringCellValue();
				} else {
					value = cell.getNumericCellValue() + "";
				}
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				value = "";
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				value = (cell.getBooleanCellValue() == true ? "Y": "N");
				break;
			default:
				value = "";
		}	
		return value.replace("\\s","").replace("\n","").replace("\r","");// 去除空格, 回车换行符;
	}
	
	public static boolean check(Row row, String[] titles) {
		int length = titles.length;
		if(row==null || length==0) {
			return false;
		}
		int cellNum = row.getLastCellNum();
		int index = 0;
		for (int cell = 0; cell <= cellNum; cell++) {
			Cell cellData = row.getCell(cell);
			if (cellData != null && !"".equals(cellData.getStringCellValue())) {
				index++;
			}
		}
		if(length!=index) {
			return false;
		}
		/**循环列Cell*/
		for (int cell = 0; cell < index; cell++) {
			Cell cellData = row.getCell(cell);
			if (cellData == null) {
				continue;
			}
			try {
				String value = cellData.getStringCellValue();
				if(value==null || "".equals(value)) {
					return false;
				}
				for (int i = 0; i < titles.length; i++) {
					String title = titles[i];
					if(value.equals(title)) {
						titles = ArrayUtils.remove(titles, i);
						break;
					}
				}
			} catch (IllegalStateException e) {
				return false;
			}
			
		}
		if(titles.length==0) {
			return true;
		} else {
			return false;
		}
	}

	public static HSSFWorkbook exportExcel(HSSFWorkbook workBook, String sheetName, String[] headers, List<List<Object>> params, OutputStream stream) throws IOException {
        //创建工作表  工作表的名字叫helloWorld  
        HSSFSheet sheet = workBook.createSheet(sheetName);  
        sheet.autoSizeColumn(1, true);
        
        HSSFRow first = sheet.createRow(0); 
        for (int j = 0; j < headers.length; j++) {
        	sheet.setColumnWidth(j, 14 * 256);
            //创建单元格，操作第三行第三列  
            HSSFCell cell = first.createCell(j, HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(headers[j]);
		}
        
        for (int i = 0; i < params.size(); i++) {
        	HSSFRow row = sheet.createRow(i+1);
        	List<Object> param = params.get(i);
        	Iterator<Object> its = param.iterator();
        	
        	Integer index = 0;
        	while (its.hasNext()) {
        		HSSFCell cell = row.createCell(index, HSSFCell.CELL_TYPE_STRING);
        		Object value = its.next();
        		if(value!=null) {
                	cell.setCellValue(value.toString());
                } else {
                	cell.setCellValue("");
                }
                index++;
			}
		}
		return workBook;
	}
	
}
