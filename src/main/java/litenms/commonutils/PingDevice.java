package litenms.commonutils;

import java.util.ArrayList;

public class PingDevice
{
    private ArrayList<String> commandList = new ArrayList<String>();

    private ProcessBuilderUtil processBuilderUtil = new ProcessBuilderUtil();

   public String pingDevice(String ip)
   {

       String result = "";

       try
       {
           commandList.clear();

           commandList.add("ping");

           commandList.add("-c");

           commandList.add("5");

           commandList.add(ip);

           result = processBuilderUtil.runCommand(commandList);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

        return result;
   }
}
