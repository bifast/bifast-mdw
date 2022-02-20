package komi.control.komilog;

import java.io.File;
import java.io.FileFilter;

import org.springframework.stereotype.Service;

@Service
public class OutboundZipLogFileFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		if (pathname.getName().startsWith("komi-outbound"))
			if (pathname.getName().endsWith(".gz"))
				return true;
		return false;
	}

}
