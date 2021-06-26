package library.iso20022.adapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BigDecimalXMLAdapter extends XmlAdapter<String, BigDecimal> {

	@Override
	public BigDecimal unmarshal(String v) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String marshal(BigDecimal v) throws Exception {
		DecimalFormat df = new DecimalFormat("#############.00");
		return df.format(v);

	}

}
