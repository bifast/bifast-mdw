package komi.control.scheduller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import komi.control.model.EventSubcriber;
import komi.control.model.QueueDistributed;
import komi.control.repository.EventCategoryRepository;
import komi.control.repository.QueueDistributedRepository;
import komi.control.service.EventService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

@Component
public class SendEmailScheduller {

    static final Logger log= LoggerFactory.getLogger(SendEmailScheduller.class);

    @Autowired
    private JavaMailSender javaMailSender;
;
    
    @Autowired
    QueueDistributedRepository etdRepository;

    //@Scheduled(cron = "0 */5 * * * ?")
    public void cronJobSch() throws IOException, MessagingException {

        log.info("================= SENDING EMAIL START "+ new Date().toString()+"======================");
        this.sendEmail();
    }

    public void sendEmail() throws IOException, MessagingException {

        List<QueueDistributed> emailSendList = etdRepository.getListByTypeNotifAndStatus("EMAIL");
        if(emailSendList.size() != 0){
        	if(emailSendList.size() != 0){

                for(QueueDistributed data:emailSendList){
                    new SimpleThreadForEmailTask(data.getSendTo(),null, data.getSubject(), data.getContent(),data).start();
                }
            }
        }

    }

    class SimpleThreadForEmailTask extends Thread {
        String emailTo,emailCc, emailSubject, emailContent;
        QueueDistributed mailTask;

        public SimpleThreadForEmailTask(String to,String cc, String sb, String cntn,QueueDistributed task) {
            emailTo = to;
            emailCc = cc;
            emailSubject = sb;
            emailContent = cntn;
            mailTask = task;
        }

        @Override
        public void run() {

            try {
                MimeMessage msg = javaMailSender.createMimeMessage();

                // true = multipart message
                MimeMessageHelper helper = new MimeMessageHelper(msg, true);
                
                helper.setTo(emailTo);
                if(emailCc != null) {
                	helper.addCc(emailCc);
                }
                helper.setSubject(emailSubject);
                helper.setText(emailContent, true);  
                
                javaMailSender.send(msg);
                log.info("================= SENDING SUCCESS ======================");

                //mailTask.setSentOn(new Date());
                mailTask.setErrorMsg("SUCCESS");
                mailTask.setStatus(1);
                mailTask.setNumError(0);
                etdRepository.save(mailTask);

                log.info("================= UPDATE TASK SUCCESS ======================");


            } catch (MessagingException mex) {

                //mailTask.setSentOn(new Date());
                mailTask.setErrorMsg(mex.getMessage());
                mailTask.setStatus(-1);
                mailTask.setNumError(mailTask.getNumError()==null?1:(mailTask.getNumError()+1));
                etdRepository.save(mailTask);

                log.info("================= SENDING ERROR ========================");
                mex.printStackTrace();

            }
        }
    }
}


