package litenms.commonUtil;

import java.util.ArrayList;

public class PingDevice {
   static ArrayList<String> commandList = new ArrayList<String>();

   public static StringBuilder pingDevice(String ip)
   {

        commandList.clear();
        commandList.add("ping");
        commandList.add("-c");
        commandList.add("5");
        commandList.add(ip);
        StringBuilder stringBuilder = ProcessBuilderUtil.runCommand(commandList);
        return stringBuilder;
   }
}
