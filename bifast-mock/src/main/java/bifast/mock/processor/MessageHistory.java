package bifast.mock.processor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import bifast.library.iso20022.custom.BusinessMessage;

@Service
public class MessageHistory{

    static Map<String,BusinessMessage> myResponse = new HashMap<String,BusinessMessage>();

    public void save (String key, BusinessMessage value) {

        myResponse.put(key, value);
    }

    public BusinessMessage get (String key) {
        listKey();
        System.out.println("Mau cari no " + key);
        BusinessMessage msg = new BusinessMessage();
        if (myResponse.containsKey(key)) 
            msg = myResponse.get(key);
        
        else
            System.out.println("Kkok ga nemu ya");

        return msg;
    }   
    
    public int size () {
        return myResponse.size();
    }

    public void listKey () {
        System.out.println("Daftar key");
        for (Map.Entry<String,BusinessMessage> entry : myResponse.entrySet())
            System.out.println(entry.getKey() );
    }

    public BusinessMessage getAny () {
        BusinessMessage msg = (BusinessMessage) myResponse.values().toArray()[0];
        System.out.println("ini aja : " + msg.getAppHdr().getBizMsgIdr());
        return msg;
    }

}
