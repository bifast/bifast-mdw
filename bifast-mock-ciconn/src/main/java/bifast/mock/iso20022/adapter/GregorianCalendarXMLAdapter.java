package bifast.mock.iso20022.adapter;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class GregorianCalendarXMLAdapter extends XmlAdapter<String, XMLGregorianCalendar> {

	@Override
	public XMLGregorianCalendar unmarshal(String v) throws Exception {
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(v));
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar( cal);
		return calendar;
	}

	@Override
	public String marshal(XMLGregorianCalendar v) throws Exception {
		String strTime = String.format("%04d-%02d-%02dT%02d:%02d:%02d.%03d", 
							v.getYear(),v.getMonth(), v.getDay(),
							v.getHour(), v.getMinute(), v.getSecond(), v.getMillisecond());
		
		return strTime;

	}

}
