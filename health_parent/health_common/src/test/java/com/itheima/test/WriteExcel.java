package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteExcel {

    @Test
    public void createExcel() throws IOException {
        //创建工作簿
        Workbook workbook = new XSSFWorkbook();
        //创建工作表
        Sheet sht = workbook.createSheet("测试写excel");
        //在工作表下创建行
        Row row = sht.createRow(0);
        //使用行创建单元格
        Cell cell = row.createCell(0);
        //给单元格赋值
        //表头
        cell.setCellValue("姓名");
        row.createCell(1).setCellValue("年龄");
        row.createCell(2).setCellValue("所在地");

        row= sht.createRow(1);
        row.createCell(0).setCellValue("小明");
        row.createCell(1).setCellValue(20);
        row.createCell(2).setCellValue("北京");

        row = sht.createRow(2);
        row.createCell(0).setCellValue("小李");
        row.createCell(1).setCellValue(30);
        row.createCell(2).setCellValue("南京");
        //保存工作簿，持久化本地硬盘里
        workbook.write(new FileOutputStream(new File("d:\\\\createExcel.xlsx")));
        //关闭工作簿
        workbook.close();

    }
}
