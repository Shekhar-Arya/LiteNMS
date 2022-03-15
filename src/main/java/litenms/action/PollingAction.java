package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.models.PollingModel;
import litenms.service.PollingService;

import java.util.HashMap;
import java.util.List;

public class PollingAction extends ActionSupport implements ModelDriven<PollingModel> {

    private PollingModel model = new PollingModel();

    public String getMonitorData()
    {
        PollingModel dataModel = PollingService.getPollingLatestData(model.getMonitor_id());
        List<PollingModel> dataModelList = PollingService.getPollingLastTwentyFourHourData(model.getMonitor_id());
        List<PollingModel> dataForAvailability = PollingService.getListForAvailabaility(model.getMonitor_id());
        int availability = 0;
        HashMap<String,Object> result = new HashMap<>();
        result.put("dataModel",dataModel);
        result.put("dataModelList",dataModelList);
        if(dataModel.getType()==null)
        {
            model.setMessage("Unknown");
        }
        else
        {
            for (PollingModel model:dataForAvailability) {
                availability+=model.getAvailability();
            }
            dataModel.setAvailability((availability*100)/dataForAvailability.size());
            model.setMessage("Known");
            model.setResult(result);
        }
        return "success";
    }

    @Override
    public PollingModel getModel() {
        return model;
    }
}
