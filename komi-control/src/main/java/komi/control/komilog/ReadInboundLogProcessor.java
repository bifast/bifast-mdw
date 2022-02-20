package komi.control.komilog;

import java.io.File;
import java.io.FileFilter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
	@Autowired ReadLogFile readLogfile;
	
	@Value("${inbound.log.dir}")
	String inboundlogdir;
	
    private String sysdate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    private String tgl;

	private FileFilter logFilefilter = new FileFilter() {
		public boolean accept(File pathname) {
			if (pathname.getName().endsWith("komi-inbound.log"))
				return true;
			return false;
		}
	};

	FileFilter gzFilefilter = new FileFilter() {
		public boolean accept(File pathname) {
			if (pathname.getName().startsWith("komi-inbound.log." + tgl))
				if (pathname.getName().endsWith(".gz"))
					return true;
			return false;
		}
	};

	@Override
	public void process(Exchange exchange) throws Exception {

		try {
			this.tgl = exchange.getMessage().getHeader("tanggal", String.class);
        	LocalDate.parse(tgl);
        }
        catch (DateTimeParseException e) {
            exchange.getMessage().setBody("<--- Format tanggal salah --->");
            return;
        }
       
        File logDir = new File(inboundlogdir);
        
        if ((null != tgl) && (sysdate.equals(tgl)) ) {
        	tgl = null;
        }
        
        StringBuffer sb = new StringBuffer();
        List<File> files = new ArrayList<File>();
        List<File> sortedFiles = new ArrayList<File>();

        if (null == tgl) {
        	File[] fs = logDir.listFiles(logFilefilter);
        	if (fs.length>0) {
        		sortedFiles.add(fs[0]);
        	}
        }
        
        else {
            for (File f : logDir.listFiles(gzFilefilter)) 
            	files.add(f);
            sortedFiles = files.stream().sorted().collect(Collectors.toList());
        }
        
        if (sortedFiles.size()==0) 
        	sb.append("<--- Logfile tidak ditemukan --->");
        
        for (File f : sortedFiles) 
        	sb.append(readLogfile.read(f));

        exchange.getMessage().setBody(sb);
	}

}
