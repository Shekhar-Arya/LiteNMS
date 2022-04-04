package litenms.commonutils;

import litenms.service.DiscoveryService;

public class DiscoveryRunnable implements Runnable
{
    private Integer id = null;

    DiscoveryRunnable(Integer id)
    {
        this.id = id;
    }

    DiscoveryService discoveryService = new DiscoveryService();

    WebSocket socket = new WebSocket();

    @Override
    public void run()
    {
        try
        {
            if (discoveryService.runDiscovery(id))
            {
                socket.sendMessage("Discovery Successfull");
            }
            else
            {
                socket.sendMessage("Discovery Unsuccessfull");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
