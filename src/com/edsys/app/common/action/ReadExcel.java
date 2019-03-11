package com.edsys.app.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ReadExcel
{
  public static void main(String[] args)
  {
    InputStream inputStream = null;
    POIFSFileSystem fileSystem = null;
    try
    {
      inputStream = new FileInputStream(new File("D:\\wsession\\config\\help\\sample_user.xls"));
      
      fileSystem = new POIFSFileSystem(inputStream);
      HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
      HSSFSheet sheet = workBook.getSheetAt(0);
      Iterator<Row> rows = sheet.rowIterator();
      StringBuilder total = new StringBuilder(100);
      while (rows.hasNext())
      {
        Row row = (Row)rows.next();
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext())
        {
          Cell cell = (Cell)cellIterator.next();
          
          total = total.append(cell.toString().trim());
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
