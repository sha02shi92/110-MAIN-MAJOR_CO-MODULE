package in.ashokit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "CO_TRIGGERS")
@Entity
public class COTriggerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer coTriggerId;
	private Long caseNum;
	
	@Lob
	private byte[] coPdf;
	private String triggerStatus;

}
