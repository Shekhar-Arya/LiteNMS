package litenms.service;

import litenms.dao.PollingDao;
import litenms.models.PollingModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PollingService
{
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

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            long millis = 0;

            Date date = formatter.parse(formatter.format(new Date()));

            millis = date.getTime();

            for (int i = 24; i > 0; i--)
            {

                Timestamp startTime = new Timestamp(new Date(millis- TimeUnit.HOURS.toMillis(i)).getTime());

                Timestamp endTime = new Timestamp(new Date(millis- TimeUnit.HOURS.toMillis(i-1)).getTime());

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
        int avgAvailability = 0;

        try
        {
            int availability = 0;

            long currentTimeInMillis = new Date().getTime();

            Timestamp endTime = new Timestamp(currentTimeInMillis);


            Timestamp startTime = new Timestamp(new Date(currentTimeInMillis-TimeUnit.HOURS.toMillis(24)).getTime());

            List<PollingModel> dataForAvailability = PollingDao.getPollingLastTwentyFourHourData(id,startTime,endTime);

            if(dataForAvailability.size()!=0)
            {
                for (PollingModel model:dataForAvailability)
                {
                    availability+=model.getAvailability();
                }

                avgAvailability = (availability*100)/dataForAvailability.size();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return avgAvailability;
    }

}
