package in.ashokit.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.KidsDetailsEntity;

public interface DataCollectionKidsRepo extends JpaRepository<KidsDetailsEntity, Serializable> {

	  public List<KidsDetailsEntity> findByCaseNum(Long caseNum);
}
