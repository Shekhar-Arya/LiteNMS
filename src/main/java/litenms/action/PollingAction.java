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
        List<PollingModel> dataModelList = PollingService.getPollingLastTenData(model.getMonitor_id());
        int availability = 0;
        for (PollingModel model:dataModelList) {
            availability+=model.getAvailability();
        }
        dataModel.setAvailability((availability*100)/dataModelList.size());
        HashMap<String,Object> result = new HashMap<>();
        result.put("dataModel",dataModel);
        result.put("dataModelList",dataModelList);
        if(dataModel.getType()==null)
        {
            model.setMessage("Unknown");
        }
        else
        {
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
