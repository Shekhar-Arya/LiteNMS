package litenms.commonUtil;

import litenms.models.DiscoveryModel;
import litenms.models.MonitorModel;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.LinkedBlockingQueue;

public class CommonUtil {
    private static LinkedBlockingQueue<MonitorModel> discoveryModels = new LinkedBlockingQueue<>();

    public static void addModel(MonitorModel model)
    {
        try {
            discoveryModels.put(model);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static MonitorModel takeModel()
    {
        MonitorModel model = null;
        try {
            model =  discoveryModels.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return model;
    }

}
