package komi.control.scheduller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import komi.control.model.EventSubcriber;
import komi.control.model.Parameter;
import komi.control.model.QueueDistributed;
import komi.control.repository.EventCategoryRepository;
import komi.control.repository.QueueDistributedRepository;
import komi.control.service.EventService;
import komi.control.service.ParameterService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;


import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class SendNotifPortalScheduller {

    static final Logger log= LoggerFactory.getLogger(SendNotifPortalScheduller.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    ParameterService parameterService;
    
    @Autowired
    QueueDistributedRepository etdRepository;

    //@Scheduled(cron = "0 */5 * * * ?")
    public void cronJobSmsSch() throws IOException, MessagingException {

        log.info("================= SENDING NOTIF PORTAL TO API "+ new Date().toString()+"======================");
        this.sendNotifPortal();
    }

    public void sendNotifPortal() throws IOException, MessagingException {

        List<QueueDistributed> emailSendList = etdRepository.getListByTypeNotifAndStatus("SMS");

    	if(emailSendList.size() != 0){

            for(QueueDistributed data:emailSendList){
                new SimpleThreadForNotifPortalTask(data.getSendTo(), data.getContent(),data).start();
            }
        }
        
    }

    class SimpleThreadForNotifPortalTask extends Thread {
        String user_id,noTelp,msg;
        QueueDistributed portalTask;

        public SimpleThreadForNotifPortalTask(String userId,String msg,QueueDistributed task) {
        	user_id = userId;
        	msg = msg;

        	portalTask = task;
        }

        @Override
        public void run() {
        	
        	String UrlApiPortal = "";
        	Optional<Parameter> hostP = parameterService.findByParamname("PORTAL.API.URL");
        	UrlApiPortal = new String(hostP.get().getParamvalua());
	        
        	CloseableHttpClient client = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(UrlApiPortal);
        	
	        String jsonRspn=null;
	        
		        JSONObject jsonStr = new JSONObject();
		        
		        jsonStr.put("user_id", user_id);
		        jsonStr.put("msg", msg);
		        String jsonInput = jsonStr.toString();
	       
	
	            httpPost.setEntity(new StringEntity(jsonInput, ContentType.APPLICATION_JSON));
	            httpPost.setHeader("Accept", "application/json");
	            httpPost.setHeader("Content-type", "application/json");
	          
	
	            try (CloseableHttpClient httpClient = HttpClients.createDefault();
	                 CloseableHttpResponse rspn = httpClient.execute(httpPost)) {
	
	                HttpEntity entity = rspn.getEntity();
	                if (entity != null) {
	                    String json = EntityUtils.toString(rspn.getEntity());
	                    jsonRspn = json;
	                    log.debug(json);
	                    if (json.equals("ERROR")) {
	                        log.debug("json = ERROR");
	                    } else {
	                        GsonBuilder gson = new GsonBuilder();
	
	                        log.info("================= SENDING SMS TO API SUCCESS ======================");

	                        portalTask.setErrorMsg("SUCCESS");
	                        portalTask.setStatus(1);
	                        portalTask.setNumError(0);
	                        etdRepository.save(portalTask);

	                        log.info("================= UPDATE TASK SUCCESS ======================");
	                         
	                    }
	                }
	                client.close();
	            }catch (Exception e) {
	                log.error(e.toString() +" msg:"+e.getMessage());
	                log.error("jsonRspn:"+jsonRspn);
	                
	                //mailTask.setSentOn(new Date());
	                portalTask.setErrorMsg(e.getMessage());
	                portalTask.setStatus(-1);
	                portalTask.setNumError(portalTask.getNumError()==null?1:(portalTask.getNumError()+1));
	                etdRepository.save(portalTask);

	                log.info("================= SENDING SMS TO API ERROR ========================");

	            }
        	
        }
    }
}


