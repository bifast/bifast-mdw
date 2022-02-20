package komi.control.komilog.filefilter;

import java.io.File;
import java.io.FileFilter;

import org.springframework.stereotype.Service;

@Service
public class InboundCurentLogFileFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		if (pathname.getName().endsWith("komi-inbound.log"))
			return true;
		return false;
	}

}
