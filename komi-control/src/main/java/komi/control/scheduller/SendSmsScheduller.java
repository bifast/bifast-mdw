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
public class SendSmsScheduller {

    static final Logger log= LoggerFactory.getLogger(SendSmsScheduller.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    ParameterService parameterService;
    
    @Autowired
    QueueDistributedRepository etdRepository;

    //@Scheduled(cron = "0 */5 * * * ?")
    public void cronJobSmsSch() throws IOException, MessagingException {

        log.info("================= SENDING SMS TO API "+ new Date().toString()+"======================");
        this.sendSMS();
    }

    public void sendSMS() throws IOException, MessagingException {

        List<QueueDistributed> emailSendList = etdRepository.getListByTypeNotifAndStatus("SMS");

    	if(emailSendList.size() != 0){

            for(QueueDistributed data:emailSendList){
                new SimpleThreadForSMSTask(data.getSendTo(), data.getContent(),data).start();
            }
        }
        
    }

    class SimpleThreadForSMSTask extends Thread {
        String accountNumber,msg;
        QueueDistributed smsTask;

        public SimpleThreadForSMSTask(String accNumber,String msg,QueueDistributed task) {
        	accountNumber = accNumber;
        	msg = msg;

        	smsTask = task;
        }

        @Override
        public void run() {
        	
        	String UrlApiServerSms = "";
        	Optional<Parameter> hostP = parameterService.findByParamname("SMS.API.URL");
     		UrlApiServerSms = new String(hostP.get().getParamvalua());
     		
        	CloseableHttpClient client = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(UrlApiServerSms);
	        
	        String jsonRspn=null;
	        
		        JSONObject jsonStr = new JSONObject();
		        
		        jsonStr.put("accountNumber", accountNumber);
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

	                        smsTask.setErrorMsg("SUCCESS");
	                        smsTask.setStatus(1);
	                        smsTask.setNumError(0);
	                        etdRepository.save(smsTask);

	                        log.info("================= UPDATE TASK SUCCESS ======================");
	                         
	                    }
	                }
	                client.close();
	            }catch (Exception e) {
	                log.error(e.toString() +" msg:"+e.getMessage());
	                log.error("jsonRspn:"+jsonRspn);
	                
	                //mailTask.setSentOn(new Date());
	            	smsTask.setErrorMsg(e.getMessage());
	            	smsTask.setStatus(-1);
	            	smsTask.setNumError(smsTask.getNumError()==null?1:(smsTask.getNumError()+1));
	                etdRepository.save(smsTask);

	                log.info("================= SENDING SMS TO API ERROR ========================");

	            }
        	
        }
    }
}


