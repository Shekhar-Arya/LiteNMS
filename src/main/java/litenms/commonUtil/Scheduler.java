package litenms.commonUtil;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Scheduler extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        Thread thread = new Thread(new TakeDataForPolling());
        thread.start();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
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
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
