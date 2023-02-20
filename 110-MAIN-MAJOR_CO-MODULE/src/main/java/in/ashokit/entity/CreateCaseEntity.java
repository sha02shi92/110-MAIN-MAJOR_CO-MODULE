package in.ashokit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="CREATE_CASE")
@Data
public class CreateCaseEntity {

	@Id
	@GeneratedValue
	private Integer appId;
	private Integer planId;
	private Long caseNum;
}
