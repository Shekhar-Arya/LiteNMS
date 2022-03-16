package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.commonutils.CacheStore;
import litenms.models.MonitorModel;
import litenms.service.MonitorService;
import java.util.HashMap;
import java.util.List;

public class MonitorAction extends ActionSupport implements ModelDriven<MonitorModel> {

    private MonitorModel monitorModel = new MonitorModel();

    public String getMonitorDevices()
    {
        try
        {
            HashMap<String,Object> result = new HashMap<>();

            if(CacheStore.getCacheList()==null || CacheStore.getCacheList().get("monitorList")==null)
            {
                List<MonitorModel> monitorModels =  MonitorService.getMonitorDevices();

                result.put("result",monitorModels);

                CacheStore.setCacheList("monitorList",monitorModels);
            }

            else {

                result.put("result",CacheStore.getCacheList().get("monitorList"));
            }

            monitorModel.setResult(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "success";
    }


    @Override
    public MonitorModel getModel() {
        return monitorModel;
    }
}
