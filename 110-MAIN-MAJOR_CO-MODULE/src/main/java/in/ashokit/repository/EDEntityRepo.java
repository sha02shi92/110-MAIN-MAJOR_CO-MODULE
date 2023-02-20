package in.ashokit.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.EDEntity;

public interface EDEntityRepo extends JpaRepository<EDEntity, Serializable> {
	
	public EDEntity findByCaseNum(Long caseNum);

}
