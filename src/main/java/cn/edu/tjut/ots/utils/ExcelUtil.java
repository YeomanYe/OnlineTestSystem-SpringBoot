package cn.edu.tjut.ots.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.edu.tjut.ots.po.Paper;
import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.po.SubjectItem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtil {
    final static String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 导出Excel
     *
     * @param clazz
     * @param os
     * @param list
     * @throws IOException
     */
    public static void excelExport(Class clazz, OutputStream os, List list) throws IOException {
        Workbook wb = new XSSFWorkbook();
        if (clazz.equals(Subject.class)) {
            exportSubject(wb, getHeadStyle(wb), getCellStyle(wb), list);
        }else if(clazz.equals(Paper.class)){
            exportPaper(wb,getHeadStyle(wb),getCellStyle(wb),list);
        }
        wb.write(os);
    }

    /**
     * 导入Excel
     *
     * @param clazz
     * @param is
     * @return
     * @throws IOException
     */
    public static List excelImport(Class clazz, InputStream is) throws IOException {
        Workbook wb = new XSSFWorkbook(is);
        List retList = null;
        if (clazz.equals(Subject.class)) {
            retList = readSubject(wb);
        }
        return retList;
    }

    /**
     * 一般单元格样式
     *
     * @param wb
     * @return
     */
    private static CellStyle getCellStyle(Workbook wb) {
        return wb.createCellStyle();
    }

    /**
     * 表头样式
     *
     * @param wb
     * @return
     */
    private static CellStyle getHeadStyle(Workbook wb) {
        CellStyle cs = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        cs.setFont(font);
        return cs;
    }

    /**
     * 通过Excel批量导入试题
     * 注意:subjectType还需要改为对应的UUID
     *
     * @param wb
     * @return
     */
    private static List readSubject(Workbook wb) {
        List retList = new LinkedList<>();
        Sheet sheet = wb.getSheetAt(0);
        sheet.forEach(
                row -> {
                    try {
                        if (row.getRowNum() < 1) return;
                        List<SubjectItem> items = new LinkedList<>();
                        Map map = new HashMap<>();
                        Subject subject = new Subject();

                        Cell cell = row.getCell(0);
                        subject.setSubjectName(cell.getStringCellValue());
                        cell = row.getCell(1);
                        subject.setSubjectType(cell.getStringCellValue());
                        cell = row.getCell(2);
                        subject.setSubjectScore((int) cell.getNumericCellValue());
                        cell = row.getCell(3);
                        subject.setSubjectParse(cell.getStringCellValue());
                        int j = 4;
                        List<String> cellValues = new ArrayList();
                        for (; j < 20; j++) {
                            cell = row.getCell(j);
                            if (null == cell) break;
                            String cellValue = cell.getStringCellValue();
                            cellValues.add(cellValue);
                        }
                        //获取是否为答案数组
                        String ansStr = cellValues.get(cellValues.size() - 1);
                        List<Boolean> isAnswers = new LinkedList();
                        String[] ansStrArr = ansStr.split("");
                        //设置答案值
                        for (int i = 0, len = cellValues.size() - 1; i < len; i++) {
                            isAnswers.add(false);
                        }
                        for (int i = 0, len = ansStrArr.length; i < len; i++) {
                            isAnswers.set(ALPHA.indexOf(ansStrArr[i]), true);
                        }
                        //添加试题ID
                        String subjectId = UUID.randomUUID().toString().replace("-", "");
                        subject.setUuid(subjectId);
                        //装配item
                        for (int i = 0, len = cellValues.size() - 1; i < len; i++) {
                            SubjectItem subjectItem = new SubjectItem();
                            subjectItem.setAnswer(isAnswers.get(i));
                            subjectItem.setName(cellValues.get(i));
                            subjectItem.setSubjectId(subjectId);
                            subjectItem.setUuid(UUID.randomUUID().toString().replace("-", ""));
                            items.add(subjectItem);
                        }
                        //设置图片路径为空,导入成果后才能更新图片
                        subject.setImgPath("");
                        map.put("subject", subject);
                        map.put("subjectItemList", items);
                        retList.add(map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        return retList;
    }

    /**
     * 导出试题Excel表格
     *
     * @param wb
     * @param cellStyle
     * @param headStyle
     * @param datas     数据
     * @return
     */
    private static void exportSubject(Workbook wb, CellStyle headStyle, CellStyle cellStyle, List<Map<String, Object>> datas) {
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("试题表");
        //创建表头
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("试题名");
        cell.setCellStyle(headStyle);

        cell = row.createCell(1);
        cell.setCellValue("试题类型");
        cell.setCellStyle(headStyle);

        cell = row.createCell(2);
        cell.setCellValue("试题分数");
        cell.setCellStyle(headStyle);

        cell = row.createCell(3);
        cell.setCellValue("试题解析");
        cell.setCellStyle(headStyle);

        cell = row.createCell(4);
        cell.setCellValue("创建者");
        cell.setCellStyle(headStyle);

        cell = row.createCell(5);
        cell.setCellValue("创建时间");
        cell.setCellStyle(headStyle);

        cell = row.createCell(6);
        cell.setCellValue("更新者");
        cell.setCellStyle(headStyle);

        cell = row.createCell(7);
        cell.setCellValue("更新时间");
        cell.setCellStyle(headStyle);

        cell = row.createCell(8);
        cell.setCellValue("试题选项");
        cell.setCellStyle(headStyle);
        int i = 1;
        for (Map m : datas) {
            Subject subject = (Subject) m.get("subject");
            List<SubjectItem> items = (List) m.get("subjectItemList");
            //创建列
            row = sheet.createRow(i);
            ++i;
            //填写试题数据
            cell = row.createCell(0);
            cell.setCellValue(subject.getSubjectName());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellValue(subject.getSubjectType());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue(subject.getSubjectScore());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellValue(subject.getSubjectParse());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellValue(subject.getCreateBy());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(5);
            cell.setCellValue(df.format(subject.getCreateWhen()));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(6);
            cell.setCellValue(subject.getUpdateBy());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(7);
            cell.setCellValue(df.format(subject.getUpdateWhen()));
            cell.setCellStyle(cellStyle);

            //试题项数据填写
            int j = 8;
            StringBuilder ans = new StringBuilder();
            for (SubjectItem item : items) {
                cell = row.createCell(j);
                cell.setCellValue(item.getName());
                cell.setCellStyle(cellStyle);
                if (item.isAnswer()) {
                    ans.append(ALPHA.charAt(j - 8));
                }
                j++;
            }
            //插入答案
            cell = row.createCell(j);
            cell.setCellValue(ans.toString());
            cell.setCellStyle(cellStyle);
        }
    }

    private static void exportPaper(Workbook wb, CellStyle headStyle, CellStyle cellStyle, List<Paper> datas) {
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("试卷表");
        //创建表头
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("试卷名");
        cell.setCellStyle(headStyle);

        cell = row.createCell(1);
        cell.setCellValue("试卷描述");
        cell.setCellStyle(headStyle);

        cell = row.createCell(2);
        cell.setCellValue("试卷类型");
        cell.setCellStyle(headStyle);

        cell = row.createCell(3);
        cell.setCellValue("试题量");
        cell.setCellStyle(headStyle);

        cell = row.createCell(4);
        cell.setCellValue("分值");
        cell.setCellStyle(headStyle);

        cell = row.createCell(5);
        cell.setCellValue("答卷时间");
        cell.setCellStyle(headStyle);

        cell = row.createCell(6);
        cell.setCellValue("创建者");
        cell.setCellStyle(headStyle);

        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell.setCellStyle(headStyle);

        cell = row.createCell(8);
        cell.setCellValue("更新者");
        cell.setCellStyle(headStyle);

        cell = row.createCell(9);
        cell.setCellValue("更新时间");
        cell.setCellStyle(headStyle);
        int i = 1;
        for (Paper p : datas) {
            //创建列
            row = sheet.createRow(i);
            ++i;
            //填写试题数据
            cell = row.createCell(0);
            cell.setCellValue(p.getPaperName());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellValue(p.getPaperDesc());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue(p.getPaperType());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellValue(p.getSubjectCnt());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellValue(p.getPaperScore());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(5);
            cell.setCellValue(p.getAnsTime());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(6);
            cell.setCellValue(p.getCreateBy());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(7);
            cell.setCellValue(df.format(p.getCreateWhen()));
            cell.setCellStyle(cellStyle);

            cell = row.createCell(8);
            cell.setCellValue(p.getUpdateBy());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(9);
            cell.setCellValue(df.format(p.getUpdateWhen()));
            cell.setCellStyle(cellStyle);

        }
    }
}
