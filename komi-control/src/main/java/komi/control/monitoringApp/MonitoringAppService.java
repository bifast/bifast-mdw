package komi.control.monitoringApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.SocketAddress;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.GsonBuilder;
import komi.control.model.Parameter;
import komi.control.dto.ModuleResponse;
import komi.control.service.ParameterService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

@Service
public class MonitoringAppService {
	
	static final Logger log= LoggerFactory.getLogger(MonitoringAppService.class);
	@Autowired
	ParameterService parameterService;
	 
	public MonitoringApp getMonitoringUsed () {
		  
		MonitoringApp monitoringUsed = new MonitoringApp();
		KomiStatus komistatus = new KomiStatus();
		
		
		
		Boolean outbound = false;
		Boolean inbound = false;
		Boolean isoAdapter = false;
		Boolean database = false;
		Boolean ciConnector = false;
		Boolean coreBankSystem = false;
		
		Optional<Parameter> urlP = parameterService.findByParamname("URL.OUTBOUND");
		List<String> portOutbound = this.urlSplit(new String(urlP.get().getParamvalua()));
		
		Optional<Parameter> urlo = parameterService.findByParamname("URL.INBOUND");
		List<String> portinbound = this.urlSplit(new String(urlo.get().getParamvalua()));
		
		Optional<Parameter> urli = parameterService.findByParamname("URL.ISOADAPTER");
		List<String> portIsoAdapter = this.urlSplit(new String(urli.get().getParamvalua()));
		
		Optional<Parameter> urld = parameterService.findByParamname("URL.DATABASE");
		List<String> portDatabase = this.urlSplit(new String(urld.get().getParamvalua()));
		
		Optional<Parameter> urlCi = parameterService.findByParamname("URL.CICONNECTOR");
		List<String> portCiConnector = this.urlSplit(new String(urlCi.get().getParamvalua()));
		
		Optional<Parameter> urlCore = parameterService.findByParamname("URL.COREBANKSYSTEM");
		String portCoreBankSystem = new String(urlCore.get().getParamvalua());
		
		outbound = isSocketAliveUitlitybyCrunchify(portOutbound.get(0), Integer.valueOf(portOutbound.get(1)));
		inbound = isSocketAliveUitlitybyCrunchify(portinbound.get(0), Integer.valueOf(portinbound.get(1)));
		isoAdapter = isSocketAliveUitlitybyCrunchify(portIsoAdapter.get(0), Integer.valueOf(portIsoAdapter.get(1)));
		database = isSocketAliveUitlitybyCrunchify(portDatabase.get(0), Integer.valueOf(portDatabase.get(1)));
		ciConnector = isSocketAliveUitlitybyCrunchify(portCiConnector.get(0), Integer.valueOf(portCiConnector.get(1)));
		coreBankSystem = coreBankCheck(portCoreBankSystem);
		
		if(outbound) {
			komistatus.setOutboundService("Up");
		}else {
			komistatus.setOutboundService("Down");
		}
		
		if(inbound) {
			komistatus.setInboundService("Up");
		}else {
			komistatus.setInboundService("Down");
		}
		
		
		if(database) {
			komistatus.setDatabase("Up");
		}else {
			komistatus.setDatabase("Down");
		}
		
		if(isoAdapter) {
			komistatus.setIsoAdapter("Up");
		}else {
			komistatus.setIsoAdapter("Down");
		}
		
		if(ciConnector) {
			komistatus.setCiConnector("Connected");
		}else {
			komistatus.setIsoAdapter("NotConnected");
		}
		
		if(coreBankSystem) {
			komistatus.setCoreBankSystem("Connected");
		}else {
			komistatus.setCoreBankSystem("NotConnected");
		}
		
		monitoringUsed.setKomiStatus(komistatus);
		return monitoringUsed;
	}
	
	public List<String> urlSplit(String url) {
		List<String> strList = new ArrayList<String>();
		
		url = url.replace("http://", "");
		url = url.replace(":", "#");
		
		String [] a = url.split("#");
		strList.add(a[0]);
		strList.add(a[1]);
		
		return strList;
	}
	
	public static boolean isSocketAliveUitlitybyCrunchify(String hostName, int port) {
		boolean isAlive = false;
 
		// Creates a socket address from a hostname and a port number
		SocketAddress socketAddress = new InetSocketAddress(hostName, port);
		Socket socket = new Socket();
 
		// Timeout required - it's in milliseconds
		int timeout = 2000;
 
		log("hostName: " + hostName + ", port: " + port);
		try {
			socket.connect(socketAddress, timeout);
			socket.close();
			isAlive = true;
 
		} catch (SocketTimeoutException exception) {
			System.out.println("SocketTimeoutException " + hostName + ":" + port + ". " + exception.getMessage());
		} catch (IOException exception) {
			System.out.println(
					"IOException - Unable to connect to " + hostName + ":" + port + ". " + exception.getMessage());
		}
		return isAlive;
	}
	
	// Simple log utility
	private static void log(String string) {
		System.out.println(string);
	}
 
	// Simple log utility returns boolean result
	private static void log(boolean isAlive) {
		System.out.println("isAlive result: " + isAlive + "\n");
	}
 
	
	public static boolean coreBankCheck(String link) {
		boolean isAlive = false;
		
		CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet(link);

        String jsonRspn=null;
    
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            
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

                        ModuleResponse moduleResponse = gson.create().fromJson(json, ModuleResponse.class);
 
                        if(moduleResponse.getModuleDetail().getActiveConnections() > 0) {
                        	isAlive = true;
                        }else {
                        	isAlive = false;
                        }
                       
                    }
                }
                client.close();
            }catch (Exception e) {
            	
            	isAlive = false;
            }
		return isAlive;
	}

	/*
	public MonitoringApp checkOutboundService (MonitoringApp monitoringUsed) {
	    
		
		Optional<Parameter> urlP = parameterService.findByParamname("URL.ACTUATOR.CPU.USAGE");
		String urlActuatorCpuUsage = new String(urlP.get().getParamvalua());
		
		CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet("http://10.11.100.116:9002/actuator/health");

        String jsonRspn=null;
    
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", "Basic Q01TOnBhc3N3b3Jk");
            
            KomiStatus komiStatus = monitoringUsed.getKomiStatus();
            
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

                        ActuatorResponse a = gson.create().fromJson(json, ActuatorResponse.class);
 
                        komiStatus.setOutboundService(a.getComponents().getPing().getStatus());
                        komiStatus.setDatabase(a.getComponents().getDb().getStatus());
                        
                        monitoringUsed.setKomiStatus(komiStatus);
                    }
                }
                client.close();
            }catch (Exception e) {
            	
            	komiStatus.setOutboundService("Down");
                monitoringUsed.setKomiStatus(komiStatus);
            }
		return monitoringUsed;
	}
	
	public MonitoringApp checkInboundService (MonitoringApp monitoringUsed) {
	    
		
		Optional<Parameter> urlP = parameterService.findByParamname("URL.ACTUATOR.CPU.USAGE");
		String urlActuatorCpuUsage = new String(urlP.get().getParamvalua());
		
		CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet("http://10.11.100.116:9001/actuator/health");

        String jsonRspn=null;
    
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            
            KomiStatus komiStatus = monitoringUsed.getKomiStatus();
            
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

                        ActuatorResponse a = gson.create().fromJson(json, ActuatorResponse.class);
 
                        komiStatus.setInboundService(a.getStatus());
                       
                        monitoringUsed.setKomiStatus(komiStatus);
                    }
                }
                client.close();
            }catch (Exception e) {
            	
            	komiStatus.setInboundService("Down");
                monitoringUsed.setKomiStatus(komiStatus);
            }
		return monitoringUsed;
	}
	
	*/
}
