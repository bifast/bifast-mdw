package komi.control.monitorlog;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class MinitorLogRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {


		from("stream:file?fileName=d:\\workspace\\temp\\logkomi\\a.txt"
				+ "&scanStream=true"
				+ "&scanStreamDelay=2000"
				+ "&fileWatcher=true"
				)
		
			.filter().simple("${body} contains 'ERROR'")
				.log("ada error  ${body}")
//				.setHeader("From", constant("frans.mazhar@gmail.com"))
//				.setHeader("To", constant("frans.mazhar@mii.co.id"))
//				.setHeader("Subject", constant("Message dari KOMI Mantap"))
//				.to("smtp://smtp.gmail.com:465?username=fransmzh13@gmail.com&password=januari68")
			.end()
		;

	}
	

}
