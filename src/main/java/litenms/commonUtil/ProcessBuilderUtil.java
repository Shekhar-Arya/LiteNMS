package litenms.commonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProcessBuilderUtil {

    public static StringBuilder runCommand(ArrayList<String> commandList) {
        ProcessBuilder builder = new ProcessBuilder(commandList);
        String data = null;
        StringBuilder builder1 = null;
        BufferedReader input = null;
        BufferedReader error = null;
        try {
            Process process = builder.start();
            input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            builder1 = new StringBuilder();
            while ((data = input.readLine()) != null) {
                builder1.append(data);
            }
            while ((data = error.readLine()) != null) {
                builder1.append(data);
            }
            return builder1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                input.close();
                error.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return builder1;
    }
}
