package com.edsys.framework.util;

import com.edsys.framework.globals.Constants;
import com.edsys.framework.log.DiagnosticLogger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.struts.upload.FormFile;

public class ZipUtil
{
  private static DiagnosticLogger logger = DiagnosticLogger.getLogger(ZipUtil.class);
  
  public static String unzip(FormFile imageZip)
  {
    if (!validate(imageZip)) {
      return null;
    }
    String rootPath = null;
    try
    {
      ZipInputStream zipInputStream = new ZipInputStream(imageZip.getInputStream());
      
      ZipEntry entry = null;
      
      File root = new File(Constants.TEMP_DIR + String.valueOf(System.currentTimeMillis()));
      
      root.mkdirs();
      
      rootPath = root.getAbsolutePath();
      while ((entry = zipInputStream.getNextEntry()) != null) {
        if (!entry.isDirectory())
        {
          File entryFile = new File(rootPath + "/" + entry.getName());
          
          byte[] buffer = new byte['?'];
          
          FileOutputStream fos = new FileOutputStream(rootPath + "/" + entryFile.getName());
          
          BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);
          int size;
          while ((size = zipInputStream.read(buffer, 0, buffer.length)) != -1) {
            bos.write(buffer, 0, size);
          }
          bos.flush();
          bos.close();
          fos.close();
        }
      }
      zipInputStream.close();
    }
    catch (IOException exception)
    {
      logger.error(exception);
    }
    return rootPath;
  }
  
  private static boolean validate(FormFile formFile)
  {
    return (isNull(formFile.getFileName())) && (isZIP(formFile.getFileName())) && (formFile.getFileSize() > 0);
  }
  
  private static boolean isNull(String fileName)
  {
    return (fileName != null) && (fileName.trim().length() > 0);
  }
  
  private static boolean isZIP(String fileName)
  {
    return fileName.toLowerCase().endsWith(".zip");
  }
}
