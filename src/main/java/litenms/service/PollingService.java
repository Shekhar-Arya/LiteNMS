package litenms.service;

import litenms.dao.PollingDao;
import litenms.models.PollingModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PollingService {

    public static void addPollingData(PollingModel model)
    {
        PollingDao.addDataOfPolling(model);
    }

    public static PollingModel getPollingLatestData(int id)
    {
      return PollingDao.getPollingLatestData(id);
    }

    public static List<PollingModel> getPollingLastTwentyFourHourData(int id)
    {
        List<PollingModel> result = null;


        try
        {
            result = new ArrayList<>();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            long millis = 0;

            Date date = formatter.parse(formatter.format(new Date()));

            millis = date.getTime();

            for (int i = 24; i > 0; i--)
            {
                String startTime = formatter.format(new Date(millis- TimeUnit.HOURS.toMillis(i)));

                String endTime = formatter.format(new Date(millis- TimeUnit.HOURS.toMillis(i-1)));

                List<PollingModel>  models = PollingDao.getPollingLastTwentyFourHourData(id,startTime,endTime);

                PollingModel model = new PollingModel();

                if (models!=null && !models.isEmpty())
                {
                    double avgPacketLoss = 0.0;

                    for (PollingModel model1:models)
                    {
                        avgPacketLoss+=model1.getPacketLoss();
                    }
                    model.setPacketLoss(avgPacketLoss/models.size());

                    model.setLabelForBar(startTime+"--"+endTime);

                    result.add(model);
                }
                else
                {
                    model.setPacketLoss(100);

                    model.setLabelForBar(startTime+"--"+endTime);

                    result.add(model);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static int getListForAvailabaility(int id)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String endTime = format.format(new Date());

        String startTime = format.format(new Date(new Date().getTime()-TimeUnit.HOURS.toMillis(24)));

        int availability = 0;

        List<PollingModel> dataForAvailability = PollingDao.getPollingLastTwentyFourHourData(id,startTime,endTime);

        for (PollingModel model:dataForAvailability)
        {
            availability+=model.getAvailability();
        }

        return (availability*100)/dataForAvailability.size();
    }

}
