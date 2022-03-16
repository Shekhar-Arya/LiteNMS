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

public class SchedulePollingJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        List<MonitorModel> monitorModels = null;

        HashMap<Integer,SSHCredentialModel> map = null;

        if(CacheStore.getCacheList()==null || CacheStore.getCacheList().get("monitorList")==null)
        {
           monitorModels = MonitorDao.getMonitorDevices();

           CacheStore.setCacheList("monitorList",monitorModels);
        }

        if(CacheStore.getCacheList()==null || CacheStore.getCacheList().get("sshCredList")==null)
        {
            map = DiscoveryDao.getAllSSHCred();

            CacheStore.setCacheList("sshCredList",map);
        }

        if(CacheStore.getCacheList()!=null && CacheStore.getCacheList().get("sshCredList")!=null && CacheStore.getCacheList().get("monitorList")!=null)
        {
            monitorModels = (List<MonitorModel>) CacheStore.getCacheList().get("monitorList");

            map = (HashMap<Integer, SSHCredentialModel>) CacheStore.getCacheList().get("sshCredList");
        }

        for (MonitorModel model:monitorModels)
        {
            if(model.getType().equals("SSH"))
            {
                model.setUsername(map.get(model.getSshId()).getUsername());

                model.setPassword(map.get(model.getSshId()).getPassword());
            }

            CommonUtil.addModel(model);
        }

     }
}
