package komi.control.controller;

import komi.control.model.Event;
import komi.control.service.EventService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/event")
public class EventController {

    static final Logger log= LoggerFactory.getLogger(EventController.class);

    @Autowired
    EventService eventService;

    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
    public Event save(@RequestBody Event eventReq){

        String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();

        Event event = new Event();
        
        event.setAccountNUmber(eventReq.getAccountNUmber());
        event.setCategoryEvent(eventReq.getCategoryEvent());
        event.setDescription(eventReq.getDescription());
        event.setTransactionNumber(eventReq.getTransactionNumber());
        event.setAccountNumberRecipient(eventReq.getAccountNumberRecipient());
        event.setBankRecipient(eventReq.getBankRecipient());
        event.setSource(eventReq.getSource());
        event.setUserId(event.getUserId());
        event.setTypeSubcribe(eventReq.getTypeSubcribe());
        event.setCategoryEvent(eventReq.getCategoryEvent());
        eventService.save(event);

        return  event;

    }
    
    @GetMapping(value = "/getSource")
    public String getStorage(){

        
        String a = "a";
        

        try
        { 
        //Process process = Runtime.getRuntime().exec("pwd"); // for Linux
        Process process = Runtime.getRuntime().exec("df -h /dev/ploop24188p1"); //for Windows

        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
           while ((line=reader.readLine())!=null)
           {
            System.out.println(line);   
            }
         }       
            catch(Exception e)
         { 
             System.out.println(e); 
         }
         finally
         {
           //process.destroy();
         }  

        return  a;

    }
}
