package in.ashokit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="ED_ELIG_DTLS")
@Entity
public class EDEntity {
	
	@Id
	@GeneratedValue
	private Integer traceId;
	private Long caseNum;
	private String holderName;
	private Long holderSsn;
	private String planName;
	private String planStatus;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double benefitAmount;
	private String denialReason;

}
