package in.ashokit.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.CreateCaseEntity;

public interface DataCollectionCreateCase extends JpaRepository<CreateCaseEntity, Serializable> {

}
