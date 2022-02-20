package komi.control.komilog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReadInboundLogProcessor implements Processor{
	@Autowired InboundZipLogFileFilter inboundFilter;
	@Autowired ReadLogFile readLogfile;
	
	@Value("${inbound.log.dir}")
	String inboundlogdir;
	
	@Override
	public void process(Exchange exchange) throws Exception {

        File logDir = new File(inboundlogdir);
        
        List<File> files = new ArrayList<File>();
        for (File f : logDir.listFiles(inboundFilter)) 
        	files.add(f);
       
        List<File> sortedFiles = files.stream().sorted().collect(Collectors.toList());
        
        StringBuffer sb = new StringBuffer();
        
        for (File f : files) {
        	System.out.println(f.getName());
        	sb.append(readLogfile.read(f));
        }

        exchange.getMessage().setBody(sb);
	}

	
}
