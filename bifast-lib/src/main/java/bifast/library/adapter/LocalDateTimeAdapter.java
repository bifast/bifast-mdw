package bifast.library.adapter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.format.annotation.DateTimeFormat;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

	@Override
	public LocalDateTime unmarshal(String v) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_INSTANT;
        return LocalDateTime.now();
	}

	@Override
	public String marshal(LocalDateTime v) throws Exception {
		
        DateTimeFormatter dtf = DateTimeFormatter.ISO_INSTANT;
        return v.format(dtf);

	}

}
