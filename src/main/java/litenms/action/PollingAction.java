package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.models.PollingModel;
import litenms.service.PollingService;

import java.util.HashMap;
import java.util.List;

public class PollingAction extends ActionSupport implements ModelDriven<PollingModel> {

    private PollingModel model = new PollingModel();

    private PollingService pollingService = new PollingService();

    public String getMonitorData()
    {
        try
        {
            PollingModel dataModel = pollingService.getPollingLatestData(model.getMonitor_id());

            List<PollingModel> dataModelList = pollingService.getPollingLastTwentyFourHourData(model.getMonitor_id());

            if(dataModel.getType()==null)
            {
                model.setMessage("Unknown");
            }
            else
            {
                dataModel.setAvailability(pollingService.getListForAvailabaility(model.getMonitor_id()));

                HashMap<String,Object> result = new HashMap<>();

                result.put("dataModel",dataModel);

                result.put("dataModelList",dataModelList);

                model.setMessage("Known");

                model.setResult(result);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "success";
    }


    @Override
    public PollingModel getModel() {
        return model;
    }
}
