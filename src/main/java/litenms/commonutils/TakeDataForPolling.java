package litenms.commonutils;

import litenms.models.MonitorModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TakeDataForPolling implements Runnable{

    @Override
    public void run() {

        ExecutorService service = Executors.newFixedThreadPool(5);

        while (true)
        {
            MonitorModel model = CommonUtil.takeModel();

            service.execute(new PollingRunnable(model));
        }
    }
}
