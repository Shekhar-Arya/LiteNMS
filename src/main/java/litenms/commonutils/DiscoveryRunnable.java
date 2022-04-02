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
        System.out.println(discoveryService.runDiscovery(id));

        if (discoveryService.runDiscovery(id))
        {
            socket.handleMessage("Discovery Successfull of "+id);
        }
        else
        {
            socket.handleMessage("Discovery Unsuccessfull of "+id);
        }

    }
}
