package in.ashokit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="KIDS_DETAILS")
@Data
public class KidsDetailsEntity {
	
	@Id
	@GeneratedValue
	private Integer kidId;
	private String kidsName;
	private LocalDate kidsAge;
	private Long kidsSsn;
	private String gender;
	private Long caseNum;

}
