package bifast.mock.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="MESSAGE_COUNTER")
public class MessageCounter {

	@Id
	private Integer tanggal;
	private Integer lastNumber;
	
	public MessageCounter() {
	}
	
	public MessageCounter(Integer tanggal, Integer lastNumber) {
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
