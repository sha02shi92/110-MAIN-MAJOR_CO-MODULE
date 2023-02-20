package in.ashokit.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.COTriggerEntity;

public interface COTriggerRepo extends JpaRepository<COTriggerEntity, Serializable> {
	
	public List<COTriggerEntity> findByTriggerStatus(String status);
	
	public COTriggerEntity findByCaseNum(Long caseNum);

}
