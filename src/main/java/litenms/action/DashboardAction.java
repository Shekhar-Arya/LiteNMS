package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.models.DashboardModel;
import litenms.service.DashboardService;

import java.util.HashMap;

public class DashboardAction extends ActionSupport implements ModelDriven<DashboardModel>
{

    private DashboardModel model = new DashboardModel();

    DashboardService dashboardService = new DashboardService();

    public String getDashboardData()
    {
        try
        {
            HashMap<String,Object> result = new HashMap<>();

            result.put("totalStatusData",dashboardService.getTotalDevicesByStatus());

            result.put("topFiveData",dashboardService.getTopDataForDashboard());

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
