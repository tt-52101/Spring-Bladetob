package org.springblade.common.tool;

import org.apache.commons.lang3.ArrayUtils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel导入导出工具类
 *
 * @author
 */
public class ExcelUtil {
    /**
     * excel导入数据（校验表头）
     *
     * @param stream   excel数据流
     * @param fileType 文件类型
     * @param header   表头
     * @return
     * @throws IOException
     */
    public static Map<String, Object> readNoSheetEXCL(InputStream stream, String fileType, String[] header) throws IOException {
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
        for (int i = 0; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            /**表头校验*/
            if (i == 0) {
                if (!check(row, header)) {
                    result.put("status", "EXCEL_HEADER_ERROR");
                    return result;
                }
                int cellNum = header.length;
                for (int cell = 0; cell < cellNum; cell++) {
                    Cell cellData = row.getCell(cell);
                    header[cell] = cellData.getStringCellValue();
                }
            }
            if (i == 0) {
                continue;
            }
            if (row == null) {
                continue;
            }

            Map<String, Object> values = new HashMap<String, Object>();
            for (int j = 0; j < totalCell; j++) {
                Cell cell = row.getCell(j);
                //if(cell != null){
                values.put(header[j], getExcelVal(cell));
                //}
            }
            data.add(values);
        }

        /**空数据校验*/
        if (data.size() == 0) {
            result.put("status", "EXCEL_DATA_NULL");
        } else {
            result.put("status", "success");
            result.put("data", data);
        }

        return result;
    }

    /**
     * excel导入数据（不校验表头）
     *
     * @param stream   excel数据流
     * @param fileType 文件类型
     * @return
     * @throws IOException
     */
    public static Map<String, Object> readNoSheetEXCL(InputStream stream, String fileType) throws IOException {
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
        String[] header = new String[totalCell];
        int n = 0;
        for (Cell cell : row1) {
            String value = cell.getStringCellValue();
            if (value != null && !"".equals(value.trim())) {
                header[n] = value;
            }
            n++;
        }

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            /**表头校验*/
            /**表头校验*/
            if (i == 0) {
                if (!check(row, header)) {
                    result.put("status", "EXCEL_HEADER_ERROR");
                    return result;
                }
                int cellNum = row.getLastCellNum();
                for (int cell = 0; cell < cellNum; cell++) {
                    Cell cellData = row.getCell(cell);
                    header[cell] = cellData.getStringCellValue();
                }
            }
            if (i == 0) {
                continue;
            }
            if (row == null) {
                continue;
            }

            Map<String, Object> values = new HashMap<String, Object>();
            for (int j = 0; j < totalCell; j++) {
                Cell cell = row.getCell(j);
                values.put(header[j], getExcelVal(cell));
            }
            data.add(values);
        }

        /**空数据校验*/
        if (data.size() == 0) {
            result.put("status", "EXCEL_DATA_NULL");
        } else {
            result.put("status", "success");
            result.put("data", data);
            result.put("header", header);
            result.put("sheetName", sheet.getSheetName());
        }

        return result;
    }

    private static String getExcelVal(Cell cell) {
        String value = "";
        if (cell == null) {
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
                    value = new DecimalFormat("#.###").format(cell.getNumericCellValue());
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
                value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                break;
            default:
                value = "";
        }
        // 去除空格, 回车换行符;
        return value.replace("\\s", "").replace("\n", "").replace("\r", "");
    }

    public static boolean check(Row row, String[] titles) {
        int length = titles.length;
        if (row == null || length == 0) {
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
        if (length != index) {
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
                if (value == null || "".equals(value)) {
                    return false;
                }
                for (int i = 0; i < titles.length; i++) {
                    String title = titles[i];
                    if (value.equals(title)) {
                        titles = ArrayUtils.remove(titles, i);
                        break;
                    }
                }
            } catch (IllegalStateException e) {
                return false;
            }

        }
        if (titles.length == 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 页面导出Excel
     *
     * @param fileName  文件名
     * @param sheetName 工作表名称
     * @param title     表头
     * @param values    内容
     * @param response
     */
    public static void exportExcel(
            String fileName, String sheetName, String[] title, String[][] values,
            HttpServletResponse response, HttpServletRequest request) {
        try {
            HSSFWorkbook hssfWorkbook = getHSSFWorkbook(sheetName, title, values, null);

            if ("IE".equals(getBrowser(request))) {
                fileName = new String(URLEncoder.encode(fileName, "UTF-8"));
            }else{
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");

            OutputStream os = response.getOutputStream();
            hssfWorkbook.write(os);
            os.flush();
            os.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 导出Excel
     *
     * @param sheetName 工作簿名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中 设置字体格式
        //字体格式
        HSSFFont font = wb.createFont();
        //font.setFontName("宋体");
        //加粗
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //字体大小
        font.setFontHeightInPoints((short)11);

        //单元格格式
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);


        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }

        return wb;
    }

    /**
     * 判断浏览器
     * @param request
     * @return
     */
    private static String getBrowser(HttpServletRequest request) {
        String UserAgent = request.getHeader("User-Agent").toLowerCase();
        if (UserAgent.indexOf("firefox") >= 0){
            return "FF";
        }else if(UserAgent.indexOf("safari") >= 0 ){
            return "Chrome";
        }else{
            return "IE";
        }
    }


}
