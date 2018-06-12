package in.grasshoper.sttask.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<StTask, Long>, JpaSpecificationExecutor<StTask>{
	
}
