package komi.control.komilog.filefilter;

import java.io.File;
import java.io.FileFilter;

import org.springframework.stereotype.Service;

@Service
public class InboundZipLogFileFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		if (pathname.getName().startsWith("komi-inbound"))
			if (pathname.getName().endsWith(".gz"))
				return true;
		return false;
	}

}
