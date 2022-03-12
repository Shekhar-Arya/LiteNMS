package litenms.commonUtil;

import litenms.dao.DiscoveryDao;
import litenms.dao.MonitorDao;
import litenms.models.DiscoveryModel;
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
        }
        else if(CacheStore.getCacheList()==null || CacheStore.getCacheList().get("sshCredList")==null)
        {
            map = DiscoveryDao.getAllSSHCred();
        }
        else {

            monitorModels = (List<MonitorModel>) CacheStore.getCacheList().get("monitorList");
            map = (HashMap<Integer, SSHCredentialModel>) CacheStore.getCacheList().get("sshCredList");
        }

        for (MonitorModel m:monitorModels) {
            DiscoveryModel model = new DiscoveryModel();
            model.setIp(m.getIp());
            model.setUsername(map.get(m.getSshId()).getUsername());
            model.setPassword(map.get(m.getSshId()).getPassword());
            CommonUtil.addModel(model);
        }

     }
}
