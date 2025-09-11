package in.armando.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.WailtlistEntity;

public interface WaitlistRepository extends JpaRepository<WailtlistEntity, Long> {
    
}
