package bifast.inbound.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="KC_INBOUND_COUNTER")
public class InboundCounter {

	@Id
	private Integer tanggal;
	private Integer lastNumber;
	
	public InboundCounter() {
	}
	
	public InboundCounter(Integer tanggal, Integer lastNumber) {
		this.tanggal = tanggal;
		this.lastNumber = lastNumber;
	}
	
	public Integer getTanggal() {
		return tanggal;
	}
	public void setTanggal(Integer tanggal) {
		this.tanggal = tanggal;
	}
	public Integer getLastNumber() {
		return lastNumber;
	}
	public void setLastNumber(Integer lastNumber) {
		this.lastNumber = lastNumber;
	}
	
	
}
