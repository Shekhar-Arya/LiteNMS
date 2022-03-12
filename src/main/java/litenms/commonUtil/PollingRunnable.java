package litenms.commonUtil;

import litenms.models.DiscoveryModel;

public class PollingRunnable implements Runnable{
    private DiscoveryModel model;

    PollingRunnable(DiscoveryModel model)
    {
        this.model = model;
    }

    @Override
    public void run() {

    }
}
