package komi.control.komilog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;

import org.springframework.stereotype.Service;

@Service
public class ReadLogFile {

	StringBuffer read (File file) throws Exception {

		FileInputStream fis = new FileInputStream(file);
		InputStream inputStream = null;
		
		if (file.getName().endsWith(".gz")) 
			inputStream = new GZIPInputStream (fis);
		else 
			inputStream = fis;
		
		
		Reader decoder = new InputStreamReader(inputStream, "UTF-8");		
	        
        
        StringBuffer sb = new StringBuffer();
        
        BufferedReader buffered = new BufferedReader(decoder);
        
        while(buffered.ready()) {
        	sb.append(buffered.readLine());
        	sb.append(System.getProperty("line.separator"));
        }
		
        fis.close();
        inputStream.close();
        
        return sb;
	}

}
