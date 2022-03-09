package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.models.DiscoveryModel;
import litenms.service.DiscoveryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiscoveryAction extends ActionSupport implements ModelDriven<DiscoveryModel> {

    private DiscoveryModel discoveryModel = new DiscoveryModel();

    public String addDeviceForDiscovery()
    {
        if(DiscoveryService.addDeviceToDiscovery(discoveryModel))
        {
            discoveryModel.setMessage("Device Added Succesfully");
        }
        else
        {
            discoveryModel.setMessage("Device not Added");
        }

        return "success";
    }

    public String getDiscoveryDevices()
    {
        if(DiscoveryService.getDiscoveryDevices()!=null)
        {
            HashMap<String,Object> result = new HashMap<>();

            result.put("result",DiscoveryService.getDiscoveryDevices());

            discoveryModel.setResult(result);
        }
        return "success";
    }

    public String deleteDiscoveryRow()
    {
        if(DiscoveryService.deleteDiscoveryRow(discoveryModel.getId()))
        {
            return "success";
        }
        return "error";
    }

    public String getDiscoveryRow()
    {
        HashMap<String,Object> result = new HashMap<>();

        result.put("result",DiscoveryService.getDiscoveryRow(discoveryModel.getId()));

        discoveryModel.setResult(result);

        return "success";
    }

    public String updateDiscoveryRow()
    {
        if(DiscoveryService.updateDiscoveryRow(discoveryModel))
        {
            return "success";
        }
        return "error";
    }

    @Override
    public DiscoveryModel getModel() {
        return discoveryModel;
    }
}
