package com.lifetech.dhap.pathcloud.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMei on 2017-02-21.
 */
public class ExportExcel {

    private static final Logger logger = LoggerFactory.getLogger(ExportExcel.class);

    /**
     * 显示的导出表的标题
     */
    private String title;

    /**
     * 导出表的列名
     */
    private String[] rowName;

    /**
     * 单元格数据
     */
    private List<Object[]> dataList = new ArrayList<>();

    /**
     * 导出文件路径
     */
    private String filePath = System.getProperty("java.io.tmpdir");

    /**
     * 构造方法，传入要导出的数据
     *
     * @param title    标题
     * @param rowName  表头列名
     * @param dataList 单元格数据
     */
    public ExportExcel(String title, String[] rowName, List<Object[]> dataList) {
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
    }


    /**
     * 导出数据
     *
     * @param fileName 文件路径
     * @return 导出的文件
     * @throws Exception
     */
    public File export(String fileName) throws Exception {
        FileOutputStream out = null;
        try {
            // 创建工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 创建工作表
            HSSFSheet sheet = workbook.createSheet(title);
            // 设置标题和表头行
            setTitleAndHeader(sheet, workbook);
            // 单元格样式对象
            HSSFCellStyle style = this.getStyle(workbook);
            // 定义所需列数
            int columnNum = rowName.length;

            // 将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {
                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i + 3);//创建所需的行数
                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;//设置单元格的数据类型
                    if (j == 0) {
                        cell = row.createCell(j, CellType.NUMERIC);
                        cell.setCellValue(i + 1);
                    } else {
                        cell = row.createCell(j, CellType.STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            cell.setCellValue(obj[j].toString());//设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    }
                    cell.setCellStyle(style);//设置单元格样式
                }
            }

            //让列宽随着导出的列长自动适应
            colWidthAuto(sheet, columnNum);

            filePath = filePath + File.separator + fileName;
            File file = new File(filePath);
            out = new FileOutputStream(file);
            workbook.write(out);
            return file;
        } catch (Exception e) {
            logger.error("Exception export file:" + fileName, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("Exception close file:" + fileName, e);
            }
        }
        return null;
    }

    /**
     * 第一列数据每两行合并单元格
     *
     * @param fileName 文件路径
     * @return 导出的文件
     * @throws IOException
     */
    public File mergeCell(String fileName) throws IOException {

        FileOutputStream out = null;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title);
            //单元格样式
            HSSFCellStyle style = this.getStyle(workbook);
            //表头样式
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
            //设置标题和表头行
            setTitleAndHeader(sheet, workbook);

            //将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {
                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i + 3);//创建所需的行数
                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;//设置单元格的数据类型
                    if (j == 0) {
                        cell = row.createCell(j, CellType.STRING);
                        cell.setCellValue(String.valueOf(obj[0]));
                        cell.setCellStyle(columnTopStyle);
                        if (i % 2 == 0) {
                            CellRangeAddress cell1 = new CellRangeAddress(i + 3, i + 4, 0, 0);
                            sheet.addMergedRegion(cell1);
                        }
                    } else {
                        cell = row.createCell(j, CellType.STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            cell.setCellValue(obj[j].toString());//设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                        cell.setCellStyle(style);//设置单元格样式
                    }
                }
            }

            //让列宽随着导出的列长自动适应
            colWidthAuto(sheet, rowName.length);

            filePath = filePath + File.separator + fileName;
            File file = new File(filePath);
            out = new FileOutputStream(file);
            workbook.write(out);
            return file;
        } catch (IOException e) {
            logger.error("Exception merge file:" + fileName, e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("Exception close file:" + fileName, e);
                }
            }
        }
        return null;
    }

    /**
     * 设置表格标题和表头
     *
     * @param sheet    工作表
     * @param workbook 工作簿对象
     */
    public void setTitleAndHeader(HSSFSheet sheet, HSSFWorkbook workbook) {
        // 产生表格标题行
        HSSFRow rowm = sheet.createRow(0);
        HSSFCell cellTiltle = rowm.createCell(0);

        //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
        HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象

        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
        cellTiltle.setCellStyle(columnTopStyle);
        cellTiltle.setCellValue(title);

        // 定义所需列数
        int columnNum = rowName.length;
        HSSFRow rowRowName = sheet.createRow(2);// 在索引2的位置创建行(最顶端的行开始的第二行)

        // 将列头设置到sheet的单元格中
        for (int n = 0; n < columnNum; n++) {
            HSSFCell cellRowName = rowRowName.createCell(n);//创建列头对应个数的单元格
            cellRowName.setCellType(CellType.STRING);//设置列头单元格的数据类型
            HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
            cellRowName.setCellValue(text);//设置列头单元格的值
            cellRowName.setCellStyle(columnTopStyle);//设置列头单元格样式
        }
    }

    /**
     * 让列宽随着导出的列长自动适应
     *
     * @param sheet     工作表
     * @param columnNum 总列数
     */
    public void colWidthAuto(HSSFSheet sheet, int columnNum) throws UnsupportedEncodingException {
        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(colNum);
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes("UTF-8").length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            if (colNum == 0) {
                sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
            } else {
                sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
            }
        }
    }

    /**
     * 列头单元格样式
     *
     * @param workbook 工作簿
     * @return 单元格样式
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 12);
        //字体加粗
        font.setBold(true);
        //设置字体名字
        font.setFontName("微软雅黑");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 列数据信息单元格样式
     *
     * @param workbook 工作簿
     * @return 列头样式
     */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 10);
        //设置字体名字
        font.setFontName("微软雅黑");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

}
