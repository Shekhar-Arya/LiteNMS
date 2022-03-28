package litenms.commonutils;

import litenms.models.MonitorModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TakeDataForPolling implements Runnable
{
    @Override
    public void run()
    {
        ExecutorService service = null;
        try
        {
            service = Executors.newFixedThreadPool(5);

            while (true)
            {
                MonitorModel model = CommonUtil.takeModel();

                service.execute(new PollingRunnable(model));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
