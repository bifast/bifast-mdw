package bifast.outbound.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.CorebankTransactionRepository;

@Service
public class CorebankService {
	
	@Autowired
	private CorebankTransactionRepository cbRepo;

    DateTimeFormatter fmtMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    DateTimeFormatter fmtDateOnly = DateTimeFormatter.ofPattern("yyyyMMdd");

	public String getKomiNoref (RequestMessageWrapper rmw) {
	    LocalDateTime ldtKomiStart = LocalDateTime.ofInstant(rmw.getKomiStart(), ZoneId.systemDefault());
		Long cbId = cbRepo.getCorebankSequence();

		String cbNoRef = "KOM" + fmt1.format(ldtKomiStart) + String.format("%05d", cbId);	
		return cbNoRef;
	}
	
	public String getOriginalNoref (RequestMessageWrapper rmw) {
		return rmw.getRequestId();		
	}
	
	public String getOriginalDatetime (RequestMessageWrapper rmw) {
	    LocalDateTime ldtKomiStart = LocalDateTime.ofInstant(rmw.getKomiStart(), ZoneId.systemDefault());
		return fmtMillis.format(ldtKomiStart);
	}
	
	public String getCurrentDatetime () {
		return fmtMillis.format(LocalDateTime.now());
	}
	
	public String getCurrentDateOnly ( ) {
		return fmtDateOnly.format(LocalDateTime.now());

	}
}
