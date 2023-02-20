package in.ashokit.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.IncomeDetailsEntity;

public interface DataCollectionIncomeRepo extends JpaRepository<IncomeDetailsEntity, Serializable> {

	   public IncomeDetailsEntity findByCaseNum(Long caseNum);
	
}
