package com.edsys.framework.scheduler;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.edsys.framework.globals.Constants;

public class AppScheduler
{
  private static AppScheduler appScheduler = null;
  private Scheduler scheduler = null;
  
  public static AppScheduler getScheduler()
  {
    if (appScheduler == null) {
      appScheduler = new AppScheduler();
    }
    return appScheduler;
  }
  
  public void schedule()
    throws SchedulerException
  {
    SchedulerFactory sf = new StdSchedulerFactory(Constants.QUARTZ_CONFIG_FILE);
    
    this.scheduler = sf.getScheduler();
    
    this.scheduler.start();
  }
  
  public void shutdown()
    throws SchedulerException
  {
    if (this.scheduler != null) {
      this.scheduler.shutdown();
    }
  }
}
