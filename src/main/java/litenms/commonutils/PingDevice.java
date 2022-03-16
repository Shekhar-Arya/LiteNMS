package litenms.commonutils;

import java.util.ArrayList;

public class PingDevice {

    static ArrayList<String> commandList = new ArrayList<String>();

   public static synchronized String pingDevice(String ip)
   {

       String result = "";
       try
       {
           commandList.clear();

           commandList.add("ping");

           commandList.add("-c");

           commandList.add("5");

           commandList.add(ip);

           result = ProcessBuilderUtil.runCommand(commandList);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

        return result;
   }
}
