package bifast.library.adapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BigDecimalXMLAdapter extends XmlAdapter<String, BigDecimal> {

	@Override
	public BigDecimal unmarshal(String v) throws Exception {
		BigDecimal bd = new BigDecimal(v);
		
		return bd;
	}

	@Override
	public String marshal(BigDecimal v) throws Exception {
		DecimalFormat df = new DecimalFormat("#############.00");
		return df.format(v);

	}

}
