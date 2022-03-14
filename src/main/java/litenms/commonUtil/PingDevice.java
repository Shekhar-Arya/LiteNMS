package litenms.commonUtil;

import java.util.ArrayList;

public class PingDevice {
   static ArrayList<String> commandList = new ArrayList<String>();

   public static String pingDevice(String ip)
   {

        commandList.clear();
        commandList.add("ping");
        commandList.add("-c");
        commandList.add("5");
        commandList.add(ip);
        String result = ProcessBuilderUtil.runCommand(commandList);
        return result;
   }
}
