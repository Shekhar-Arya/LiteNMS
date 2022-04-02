package litenms.commonutils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/login/websocket/discovery")
public class WebSocket
{
    @OnOpen
    public void handleOpen(Session session)
    {
        try
        {
            System.out.println("hello");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @OnMessage
    public String handleMessage(String message)
    {

        /*System.out.println("From Client to server : "+message);
        System.out.println("Sending to the Client : "+message+"FromServer");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return message;
    }



    @OnClose
    public void handleClose()
    {

    }

    @OnError
    public void handleError(Throwable throwable)
    {
        throwable.printStackTrace();
    }


}
