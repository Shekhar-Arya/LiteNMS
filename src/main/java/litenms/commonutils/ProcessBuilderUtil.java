package litenms.commonutils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProcessBuilderUtil
{
    public static String runCommand(ArrayList<String> commandList)
    {
        ProcessBuilder builder = null;

        String data = null;

        String result = "";

        BufferedReader input = null;

        Process process = null;

        try
        {
            builder = new ProcessBuilder(commandList);

            process = builder.start();

            input = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((data = input.readLine()) != null)
            {
                result+=data;
            }

            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (input!=null)
                {
                    input.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                if (process!=null)
                {
                    process.destroy();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }
}
