package in.ashokit.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.PlanSelectionEntity;

public interface DataCollectionPlanSelectRepo extends JpaRepository<PlanSelectionEntity, Serializable> {

}
