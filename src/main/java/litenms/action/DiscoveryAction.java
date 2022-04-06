package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.commonutils.CommonUtil;
import litenms.models.DiscoveryModel;
import litenms.service.DiscoveryService;
import litenms.service.MonitorService;
import java.util.HashMap;
import java.util.List;

public class DiscoveryAction extends ActionSupport implements ModelDriven<DiscoveryModel>
{
    private DiscoveryModel discoveryModel = new DiscoveryModel();

    public String addDeviceForDiscovery()
    {
        try
        {
            if(DiscoveryService.addDeviceToDiscovery(discoveryModel))
            {
                discoveryModel.setMessage("Device Added Succesfully");
            }
            else
            {
                discoveryModel.setMessage("Device not Added");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "success";
    }

    public String getDiscoveryDevices()
    {
        try
        {
            HashMap<String,Object> result = new HashMap<>();

            List<DiscoveryModel> models = DiscoveryService.getDiscoveryDevices();

            result.put("result",models);

            discoveryModel.setResult(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "success";
    }

    public String deleteDiscoveryRow()
    {
        try
        {
            DiscoveryModel model = DiscoveryService.getDiscoveryRow(discoveryModel.getId());

            if(DiscoveryService.deleteDiscoveryRow(discoveryModel.getId()))
            {
                discoveryModel.setMessage(model.getIp()+" Device Deleted Successfully");
            }
            else
            {
                discoveryModel.setMessage(model.getIp()+" Unsuccessful Device Deletion");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "success";
    }

    public String getDiscoveryRow()
    {
        try
        {
            HashMap<String,Object> result = new HashMap<>();

            result.put("result", DiscoveryService.getDiscoveryRow(discoveryModel.getId()));

            discoveryModel.setResult(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "success";
    }

    public String updateDiscoveryRow()
    {
        try
        {
            if(DiscoveryService.updateDiscoveryRow(discoveryModel))
            {
                discoveryModel.setMessage("Device Updated Successfully");
            }
            else
            {
                discoveryModel.setMessage("Unsuccessful Device Update");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "success";
    }

    public String runDiscoveryDevice()
    {
        try
        {
            int id = discoveryModel.getId();

            DiscoveryModel model = DiscoveryService.getDiscoveryRow(id);

            CommonUtil.addDiscoveryId(id);

            discoveryModel.setMessage(model.getIp()+" Device Added to Queue for Discovery");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "success";
    }

    public String addDeviceToMonitor()
    {
        try
        {
            DiscoveryModel model = DiscoveryService.getDiscoveryRow(discoveryModel.getId());

            if(MonitorService.addDeviceToMonitor(discoveryModel.getId()))
            {
                discoveryModel.setMessage(model.getIp()+" Device Provision Successfully");
            }
            else
            {
                discoveryModel.setMessage(model.getIp()+" Device Already Provisioned");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "success";
    }

    @Override
    public DiscoveryModel getModel() {
        return discoveryModel;
    }
}
