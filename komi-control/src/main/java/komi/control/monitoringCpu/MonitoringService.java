package komi.control.monitoringCpu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.GsonBuilder;
import komi.control.model.Parameter;
import komi.control.service.ParameterService;

@Service
public class MonitoringService {
	
	static final Logger log= LoggerFactory.getLogger(MonitoringService.class);
	@Autowired
	ParameterService parameterService;
	 
	@Value("${server.port}")
	private int serverPort;

	public Monitoring getMonitoringUsed () {
		  
		Monitoring monitoringUsed = new Monitoring();
		monitoringUsed = this.getMemory(monitoringUsed);
		monitoringUsed = this.getDisk(monitoringUsed);
		monitoringUsed = this.getProcessor(monitoringUsed);
		
		return monitoringUsed;
	}
	
	public Monitoring getMemory (Monitoring monitoring) {  
       
        try
        { 

        Process process = Runtime.getRuntime().exec("cat /proc/meminfo");
        	
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        ArrayList<String> list=new ArrayList<String>();
        
           while ((line=reader.readLine())!=null)
           	{
        	   System.out.println(line); 
        	   list.add(line);
            }
           
           String [] totalMemory = list.get(0).replaceAll("\\s+", " ").split(" ");
           String [] memoryAvialable = list.get(2).replaceAll("\\s+", " ").split(" ");
           
           System.out.println(totalMemory[1]); 
           System.out.println(memoryAvialable[1]); 
           Double persentaseAviable = (Double.valueOf(memoryAvialable[1])/Double.valueOf(totalMemory[1]))*100;
           Double persentaseUsed = 100-persentaseAviable;
           
           DecimalFormat decimalFormat = new DecimalFormat("0.00");
           System.out.println(decimalFormat.format(persentaseAviable)+"%"); 
           System.out.println(decimalFormat.format(persentaseUsed)+"%"); 
           
           Double totaMemoryGb = Double.valueOf(totalMemory[1])/1048576.0;
           Memory memory = new Memory();
           memory.setAvailableMemory(decimalFormat.format(persentaseAviable)+"%");
           memory.setUsedMemory(decimalFormat.format(persentaseUsed)+"%");
           memory.setTotalMemory(decimalFormat.format(totaMemoryGb)+"Gb");
           monitoring.setMemory(memory);
           
         }       
            catch(Exception e)
         { 
             System.out.println(e); 
         }
         finally
         {
           //process.destroy();
         }  
		
		return monitoring;
	}
	
	public Monitoring getDisk (Monitoring monitoring) {  

		String totalDisk = "a";
        
        ///DISK 
        Disk disk = new Disk();
        
        Optional<Parameter> urlP = parameterService.findByParamname("MONITORING.DISKNAME");
		String diskName = new String(urlP.get().getParamvalua());
        
        try
        { 
        Process process = Runtime.getRuntime().exec("df -h "+diskName); //for Windows
        
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
           while ((line=reader.readLine())!=null)
           	{
        	   System.out.println(line); 
        	   totalDisk =line;
            }
           
           System.out.println(totalDisk); 
           
           String [] strarr = totalDisk.replaceAll("\\s+", " ").split(" ");
           disk.setName(strarr[0]);
           disk.setSize(strarr[1]);
           disk.setUsed(strarr[2]);
           disk.setAvailable(strarr[3]);
           disk.setPercent(strarr[4]);
           monitoring.setDisk(disk);
         }       
            catch(Exception e)
         { 
             System.out.println(e); 
         }
         finally
         {
           //process.destroy();
         }  
		
		return monitoring;
	}
	
	public Monitoring getProcessor (Monitoring monitoringUsed) {
	    
//		Optional<Parameter> urlP = parameterService.findByParamname("URL.ACTUATOR.CPU.USAGE");
//		String urlActuatorCpuUsage = new String(urlP.get().getParamvalua());
		String urlActuatorCpuUsage = "http://localhost:" + serverPort + "/actuator/metrics/system.cpu.usage";
		
//		Optional<Parameter> usernameP = parameterService.findByParamname("KOMI_CORE_OUTBOUND_USERNAME");
//		String username = new String(usernameP.get().getParamvalua());
//		
//		Optional<Parameter> passwordP = parameterService.findByParamname("KOMI_CORE_OUTBOUND_PASSWORD");
//		String password = new String(passwordP.get().getParamvalua());
		
		
		CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet(urlActuatorCpuUsage);

        String jsonRspn=null;
        	
//           String auth = username + ":" + password;
//           byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
//           String authHeader = "Basic " + new String( encodedAuth );
        	
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
//            httpPost.setHeader("Authorization", authHeader);
            
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse rspn = httpClient.execute(httpPost)) {

                HttpEntity entity = rspn.getEntity();
                if (entity != null) {
                    String json = EntityUtils.toString(rspn.getEntity());
                    jsonRspn = json;
                    System.out.println(json);
                    if (json.equals("ERROR")) {
                    	System.out.println("json = ERROR");
                    } else {
                        GsonBuilder gson = new GsonBuilder();

                        ProccesorUseg a = gson.create().fromJson(json, ProccesorUseg.class);
 
                        Processor proc = new Processor();
                        
                        Double total =  a.getMeasurements().get(0).getValue() * 100;
                        //System.out.println(total);
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                       // System.out.println(decimalFormat.format(total));
                        proc.setUsed(Double.valueOf(decimalFormat.format(total)));
                        
                        monitoringUsed.setProcessor(proc);
                    }
                }
                client.close();
            }catch (Exception e) {
            	System.out.println(e.toString() +" msg:"+e.getMessage());
            	System.out.println("jsonRspn:"+jsonRspn);
            }
		return monitoringUsed;
	}
	
	
}
