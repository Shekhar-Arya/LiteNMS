package litenms.commonutils;

import litenms.models.DiscoveryModel;
import litenms.service.DiscoveryService;

public class DiscoveryRunnable implements Runnable
{
    private int id ;

    DiscoveryRunnable(int id)
    {
        this.id = id;
    }

    private WebSocket socket = new WebSocket();

    @Override
    public void run()
    {
        try
        {
            DiscoveryModel model = DiscoveryService.getDiscoveryRow(id);

            if (DiscoveryService.runDiscovery(id))
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
