package litenms.commonutils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TakeDataIdForDiscovery implements Runnable
{
    @Override
    public void run()
    {
        ExecutorService service = null;

        try
        {
            service = Executors.newFixedThreadPool(3);

            while (true)
            {
                int id = CommonUtil.takeDiscoveryId();

                service.execute(new DiscoveryRunnable(id));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
