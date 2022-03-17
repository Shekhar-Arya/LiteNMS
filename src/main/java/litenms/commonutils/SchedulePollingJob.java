package litenms.commonutils;

import litenms.dao.DiscoveryDao;
import litenms.dao.MonitorDao;
import litenms.models.MonitorModel;
import litenms.models.SSHCredentialModel;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SchedulePollingJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

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
                sshCredList = DiscoveryDao.getAllSSHCred();

                CacheStore.setCacheList("sshCredList",sshCredList);

                monitorModels = MonitorDao.getMonitorDevices();

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
