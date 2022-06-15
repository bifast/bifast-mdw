package komi.control.monitoringApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("api/monitoringApp")
public class MonitoringAppController {

    static final Logger log= LoggerFactory.getLogger(MonitoringAppController.class);
    
    @Autowired
    MonitoringAppService monitoringService;
    
    @CrossOrigin
    @GetMapping(value = "/checkService")
    public MonitoringApp getStorage(){

        
    	MonitoringApp monitoring=monitoringService.getMonitoringUsed();
		
        return  monitoring;

    }
}
