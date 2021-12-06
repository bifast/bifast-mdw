package komi.control.monitoringApp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/monitoringApp")
public class MonitoringAppController {

    static final Logger log= LoggerFactory.getLogger(MonitoringAppController.class);
    
    @Autowired
    MonitoringAppService monitoringService;
    
    @GetMapping(value = "/checkService")
    public MonitoringApp getStorage(){

        
    	MonitoringApp monitoring=monitoringService.getMonitoringUsed();
		
        return  monitoring;

    }
}
