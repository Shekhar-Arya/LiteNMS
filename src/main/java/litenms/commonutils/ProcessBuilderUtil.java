package litenms.commonutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProcessBuilderUtil {

    public static String runCommand(ArrayList<String> commandList) {

        ProcessBuilder builder = new ProcessBuilder(commandList);

        String data = null;

        String result = "";

        BufferedReader input = null;

        BufferedReader error = null;

        try {
            Process process = builder.start();

            input = new BufferedReader(new InputStreamReader(process.getInputStream()));

            error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            while ((data = input.readLine()) != null)
            {
                result+=data;
            }

            while ((data = error.readLine()) != null)
            {
                result+=data;
            }

            return result;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                input.close();

                error.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }
}
