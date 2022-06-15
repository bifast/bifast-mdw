package komi.control.monitoringCpu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/monitoring")
public class MonitoringController {

    static final Logger log= LoggerFactory.getLogger(MonitoringController.class);
    
    @Autowired
    MonitoringService monitoringService;
    
    @CrossOrigin
    @GetMapping(value = "/getMonitoringUsed")
    public Monitoring getStorage(){

        
    	Monitoring monitoring=monitoringService.getMonitoringUsed();
		
        return  monitoring;

    }
}
