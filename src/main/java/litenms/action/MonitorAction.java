package litenms.action;

import com.mysql.cj.xdevapi.DeleteStatement;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.deploy.security.SelectableSecurityManager;
import litenms.commonutils.CacheStore;
import litenms.models.MonitorModel;
import litenms.service.MonitorService;
import litenms.service.PollingService;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MonitorAction extends ActionSupport implements ModelDriven<MonitorModel> {

    private MonitorModel monitorModel = new MonitorModel();

    public String getMonitorDevices()
    {
        try
        {
            HashMap<String,Object> result = new HashMap<>();

            ConcurrentHashMap<String,Object> temp = CacheStore.getCacheList();

            if(temp==null || temp.get("monitorList")==null)
            {
                List<MonitorModel> monitorModels =  MonitorService.getMonitorDevices();

                result.put("result",monitorModels);

                CacheStore.setCacheList("monitorList",monitorModels);
            }

            else
            {

                result.put("result",temp.get("monitorList"));
            }

            monitorModel.setResult(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "success";
    }

    public String deleteMonitorData()
    {
        if (MonitorService.deleteMonitorData(monitorModel.getId()))
        {
            monitorModel.setMessage("Successfully Deleted");
        }
        else
        {
            monitorModel.setMessage("Delete Unsuccessful");
        }
        return "success";
    }

    @Override
    public MonitorModel getModel() {
        return monitorModel;
    }
}
