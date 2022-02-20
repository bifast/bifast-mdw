package komi.control.komilog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.stereotype.Service;

@Service
public class ReadLogFile {

	StringBuffer read (File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
        
        StringBuffer sb = new StringBuffer();
        
        Reader decoder = new InputStreamReader(fis, "UTF-8");
        BufferedReader buffered = new BufferedReader(decoder);
        
        while(buffered.ready()) {
        	sb.append(buffered.readLine());
        	sb.append(System.getProperty("line.separator"));
        }
        fis.close();
        
        return sb;
	}

}
