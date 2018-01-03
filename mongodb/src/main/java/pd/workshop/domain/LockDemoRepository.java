package pd.workshop.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import pd.workshop.domain.model.LockDemo;

public interface LockDemoRepository extends MongoRepository<LockDemo, Long>, LockDemoRepositoryCustom {
}
