package litenms.commonutils;

import litenms.dao.DiscoveryDao;
import litenms.dao.MonitorDao;
import litenms.models.MonitorModel;
import litenms.models.SSHCredentialModel;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SchedulePollingJob implements Job
{

    DiscoveryDao discoveryDao = new DiscoveryDao();

    MonitorDao monitorDao = new MonitorDao();

    @Override
    public void execute(JobExecutionContext jobExecutionContext)
    {
        List<MonitorModel> monitorModels = null;

        HashMap<Integer,SSHCredentialModel> sshCredList = null;

        try
        {
            ConcurrentHashMap<String,Object> temp = CacheStore.getCacheList();

            if(temp!=null && temp.get("sshCredList")!=null && temp.get("monitorList")!=null)
            {
                monitorModels = (List<MonitorModel>) temp.get("monitorList");

                sshCredList = (HashMap<Integer, SSHCredentialModel>) temp.get("sshCredList");
            }
            else
            {
                sshCredList = discoveryDao.getAllSSHCred();

                CacheStore.setCacheList("sshCredList",sshCredList);

                monitorModels = monitorDao.getMonitorDevices();

                CacheStore.setCacheList("monitorList",monitorModels);
            }

            for (MonitorModel model:monitorModels)
            {
                if(model.getType().equals("SSH"))
                {
                    model.setUsername(sshCredList.get(model.getSshId()).getUsername());

                    model.setPassword(sshCredList.get(model.getSshId()).getPassword());
                }

                CommonUtil.addModel(model);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
     }
}
