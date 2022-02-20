package komi.control.komilog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;

import org.springframework.stereotype.Service;

@Service
public class ReadZipFile {

	StringBuffer read (File file) throws Exception {
        GZIPInputStream gis = new GZIPInputStream(new FileInputStream(file));
        
        StringBuffer sb = new StringBuffer();
        
        Reader decoder = new InputStreamReader(gis, "UTF-8");
        BufferedReader buffered = new BufferedReader(decoder);
        
        while(buffered.ready()) {
        	sb.append(buffered.readLine());
        	sb.append(System.getProperty("line.separator"));
        }
        gis.close();
        
        return sb;
	}

}
