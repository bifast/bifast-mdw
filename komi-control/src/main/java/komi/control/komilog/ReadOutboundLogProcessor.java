package komi.control.komilog;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import komi.control.komilog.filefilter.InboundCurentLogFileFilter;
import komi.control.komilog.filefilter.InboundZipLogFileFilter;

@Component
public class ReadOutboundLogProcessor implements Processor{
	@Autowired InboundZipLogFileFilter inboundFilter;
	@Autowired InboundCurentLogFileFilter inboundCurrentLogFilter;
	@Autowired ReadLogFile readLogfile;
	
	@Value("${outbound.log.dir}")
	String outboundlogdir;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		FileFilter logFilefilter = new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.getName().endsWith("komi-outbound.log"))
					return true;
				return false;
			}
		};

		FileFilter gzFilefilter = new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.getName().startsWith("komi-outbound"))
					if (pathname.getName().endsWith(".gz"))
						return true;
				return false;
			}
		};

        File logDir = new File(outboundlogdir);
        
        String tgl = exchange.getMessage().getHeader("tanggal", String.class);

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
            for (File f : logDir.listFiles(gzFilefilter)) {
            	if (f.getName().contains(tgl))
            		files.add(f);
            }
            sortedFiles = files.stream().sorted().collect(Collectors.toList());
        }
        
        if (sortedFiles.size()==0) 
        	sb.append("<--- Logfile tidak ditemukan di " + outboundlogdir + " --->");
        
        for (File f : sortedFiles) {
        	System.out.println("File " + f.getName());
        	sb.append(readLogfile.read(f));
        }

        exchange.getMessage().setBody(sb);
	}

	
}
