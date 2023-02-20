package in.ashokit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name ="PLAN_SELECTION")
@Data
public class PlanSelectionEntity {
	@Id
	@GeneratedValue
	private Integer planId;
	private String planName;
	private Long caseNum;
	

}
