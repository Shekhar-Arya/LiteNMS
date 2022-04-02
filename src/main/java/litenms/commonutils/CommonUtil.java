package litenms.commonutils;

import litenms.models.MonitorModel;
import java.util.concurrent.LinkedBlockingQueue;

public class CommonUtil
{
    private static LinkedBlockingQueue<MonitorModel> discoveryModels = new LinkedBlockingQueue<>();

    private static LinkedBlockingQueue<Integer> runDiscoveryList = new LinkedBlockingQueue<>();

    public static void addModel(MonitorModel model)
    {
        try
        {
            discoveryModels.put(model);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static MonitorModel takeModel()
    {
        MonitorModel model = null;

        try
        {
            model =  discoveryModels.take();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return model;
    }

    public static void addDiscoveryId(int id)
    {
        try
        {
            runDiscoveryList.put(id);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static Integer takeDiscoveryId()
    {
        Integer id = null;

        try
        {
            id =  runDiscoveryList.take();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return id;
    }


}
