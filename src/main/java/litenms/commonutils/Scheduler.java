package litenms.commonutils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Scheduler extends HttpServlet
{
    @Override
    public void init()
    {
        SchedulerFactory schedulerFactory = null;

        try
        {
            Thread thread = new Thread(new TakeDataForPolling());

            thread.start();

            schedulerFactory = new StdSchedulerFactory();

            org.quartz.Scheduler scheduler = schedulerFactory.getScheduler();

            scheduler.start();

            JobDetail job = JobBuilder.newJob(SchedulePollingJob.class)
                    .withIdentity("testJob")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(60)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job,trigger);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
