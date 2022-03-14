package litenms.service;

import litenms.dao.PollingDao;
import litenms.models.PollingModel;

import java.util.List;

public class PollingService {
    public static void addPollingData(PollingModel model)
    {
        PollingDao.addDataOfPolling(model);
    }

    public static PollingModel getPollingLatestData(int id)
    {
      return PollingDao.getPollingLatestData(id);
    }

    public static List<PollingModel> getPollingLastTenData(int id)
    {
        return PollingDao.getPollingLastTenData(id);
    }
}
