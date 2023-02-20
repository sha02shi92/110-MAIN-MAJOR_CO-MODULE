package in.ashokit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="EDUCATION_DETAILS")
@Data
public class EducationDetailsEntity {
	@Id
	@GeneratedValue
	private Integer educationId;
	private String hDegree;
	private Integer gYear;
	private String uName;
	private Long caseNum;

}
