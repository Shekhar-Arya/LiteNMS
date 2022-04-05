package litenms.commonutils;

import litenms.models.DiscoveryModel;
import litenms.service.DiscoveryService;

public class DiscoveryRunnable implements Runnable
{
    private Integer id = null;

    DiscoveryRunnable(Integer id)
    {
        this.id = id;
    }

    private DiscoveryService discoveryService = new DiscoveryService();

    private WebSocket socket = new WebSocket();

    @Override
    public void run()
    {
        try
        {
            DiscoveryModel model = discoveryService.getDiscoveryRow(id);

            if (discoveryService.runDiscovery(id))
            {
                socket.sendMessage(model.getIp()+" Discovery Successfull");
            }
            else
            {
                socket.sendMessage(model.getIp()+" Discovery Unsuccessfull");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
