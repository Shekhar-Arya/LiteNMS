package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.models.MonitorModel;
import litenms.service.MonitorService;

import java.util.HashMap;
import java.util.List;

public class MonitorAction extends ActionSupport implements ModelDriven<MonitorModel> {

    private MonitorModel monitorModel = new MonitorModel();

    public String getMonitorDevices()
    {
        HashMap<String,Object> result = new HashMap<>();
        result.put("result", MonitorService.getMonitorDevices());
        monitorModel.setResult(result);
        return "success";
    }


    @Override
    public MonitorModel getModel() {
        return monitorModel;
    }
}
