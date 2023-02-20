package in.ashokit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "INCOME_DETAILS")
@Data
public class IncomeDetailsEntity {
	
	@Id
	@GeneratedValue
	private Integer incomeId;
	private Double monthlyIncome;
	private Double rentIncome;
	private Double propertyIncome;
	private Long caseNum;

}
