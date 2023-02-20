package in.ashokit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "Citizen_APPS")
@Data
public class CitizenAppEntity {
	@Id
	@GeneratedValue
	private String appId;
	private String name;
	private String email;
	private Long mobileNum;
	private String gender;
	private LocalDate dob;
	private Long ssn;
	private Long caseNum;

	@CreationTimestamp
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;

	private Integer createdBy;
	private Integer updatedBy;

}
