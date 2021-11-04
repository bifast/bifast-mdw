package bifast.library.adapter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class GregorianIsoUTCAdapter extends XmlAdapter<String, XMLGregorianCalendar> {

	@Override
	public XMLGregorianCalendar unmarshal(String v) throws Exception {
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(v));
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar( cal);
		return calendar;
	}

	@Override
	public String marshal(XMLGregorianCalendar v) throws Exception {
//		String strTime = String.format("%04d-%02d-%02dT%02d:%02d:%02d.%03d", 
//							v.getYear(),v.getMonth(), v.getDay(),
//							v.getHour(), v.getMinute(), v.getSecond(), v.getMillisecond());
		
		GregorianCalendar gc = v.toGregorianCalendar();
		ZonedDateTime zdt = gc.toZonedDateTime();
		Instant instant = zdt.toInstant();
		
	    DateTimeFormatter fmtMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
//	    fmtMillis.
	    
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
        String dd = dateFormat.format(gc.getTime());
		return dd;

	}

}
