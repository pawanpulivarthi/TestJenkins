package com.edsys.framework.util;

import java.io.PrintStream;
import java.util.Random;

public class RandomPasswordMethod
{
  public final StringBuffer RandomPassword()
  {
    String str = new String("AB78CDEFKLMNO3rstu4abc56hijklmnopqvw0xyz9G12HIJdefgPQRSTUVWXYZ");
    
    StringBuffer sb = new StringBuffer();
    String ar = null;
    Random r = new Random();
    int te = 0;
    for (int i = 1; i <= 6; i++)
    {
      te = r.nextInt(62);
      ar = ar + str.charAt(te);
      sb.append(str.charAt(te));
    }
    char[] arr = str.toCharArray();
    System.out.println(sb.toString());
    return sb;
  }
  
  public static void main(String[] args) {}
}
