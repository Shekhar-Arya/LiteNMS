package litenms.commonUtil;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class CacheStore {

    private static ConcurrentHashMap<String,Object> cacheList = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Object> getCacheList() {
        return cacheList;
    }

    public static void setCacheList(String name, Object object) {
        CacheStore.cacheList.put(name,object);
    }

    static {
        Thread thread = new Thread(new TakeDataForPolling());
        thread.start();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            JobDetail job = JobBuilder.newJob(SchedulePollingJob.class)
                    .withIdentity("testJob")
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(120)
                            .repeatForever())
                    .build();
            scheduler.scheduleJob(job,trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
