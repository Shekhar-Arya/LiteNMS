package litenms.commonutils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/endpoint")
public class WebSocket
{

     private static Session session;

    @OnOpen
    public void handleOpen(Session session)
    {

        try
        {
//            session.setMaxIdleTimeout(1800000);

            WebSocket.session = session;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void handleMessage(String message)
    {

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

    public void sendMessage(String message)
    {
        try
        {
            WebSocket.session.getBasicRemote().sendText(message);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
