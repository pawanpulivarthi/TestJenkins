package com.edsys.framework.scheduler;

import java.io.PrintStream;

public class WorkPractice
{
  static int x;
  static int y;
  int z;
  
  public static void main(String[] args)
  {
    x = 10;
    y = 20;
    System.out.println(x + "" + y);
  }
  
  public void method()
  {
    x = 30;
    y = 40;
    this.z = 0;
  }
}
