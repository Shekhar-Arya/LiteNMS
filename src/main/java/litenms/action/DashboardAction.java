package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.models.DashboardModel;
import litenms.service.DashboardService;

import java.util.HashMap;

public class DashboardAction extends ActionSupport implements ModelDriven<DashboardModel>
{

    private DashboardModel model = new DashboardModel();

    public String getDashboardData()
    {
        try
        {
            HashMap<String,Object> result = new HashMap<>();

            result.put("totalStatusData",DashboardService.getTotalDevicesByStatus());

            result.put("topFiveData",DashboardService.getTopDataForDashboard());

            model.setResult(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "success";
    }


    @Override
    public DashboardModel getModel()
    {
        return model;
    }
}
