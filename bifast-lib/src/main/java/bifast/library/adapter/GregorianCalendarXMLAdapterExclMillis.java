package bifast.library.adapter;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class GregorianCalendarXMLAdapterExclMillis extends XmlAdapter<String, XMLGregorianCalendar> {

	@Override
	public XMLGregorianCalendar unmarshal(String v) throws Exception {
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(v));
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar( cal);
		return calendar;
	}

	@Override
	public String marshal(XMLGregorianCalendar v) throws Exception {
		return v.getYear()+"-"+v.getMonth()+"-"+v.getDay()+ 
				"T" + v.getHour() + ":" + v.getMinute() + ":" + v.getSecond() ;
	}

}
