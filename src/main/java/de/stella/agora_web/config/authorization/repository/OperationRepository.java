package de.stella.agora_web.config.authorization.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.stella.agora_web.config.authorization.model.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long>{

    @Query("SELECT o FROM Operation o WHERE o.permitAll = true")
    static
    List<Operation> findByPublicAccess() {

        return null;
    }

    Optional<Operation> findByName(String operation);
}