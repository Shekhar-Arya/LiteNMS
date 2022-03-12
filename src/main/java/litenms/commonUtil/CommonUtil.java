package litenms.commonUtil;

import litenms.models.DiscoveryModel;

import java.util.concurrent.LinkedBlockingQueue;

public class CommonUtil {
    private static LinkedBlockingQueue<DiscoveryModel> discoveryModels = new LinkedBlockingQueue<>();

    public static void addModel(DiscoveryModel model)
    {
        try {
            discoveryModels.put(model);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static DiscoveryModel takeModel()
    {
        DiscoveryModel model = null;
        try {
            model =  discoveryModels.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return model;
    }
}
