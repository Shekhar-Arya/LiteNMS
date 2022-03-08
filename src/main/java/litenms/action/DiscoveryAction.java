package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.models.DiscoveryModel;
import litenms.service.DiscoveryService;

public class DiscoveryAction extends ActionSupport implements ModelDriven<DiscoveryModel> {
    private DiscoveryModel discoveryModel = new DiscoveryModel();

    public String addDeviceForDiscovery()
    {
        System.out.println(discoveryModel.getIp());
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

    @Override
    public DiscoveryModel getModel() {
        return discoveryModel;
    }
}
