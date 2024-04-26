package com.goodvice.pumbtest.repository;

import com.goodvice.pumbtest.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing {@link Animal} entities in the database.
 *
 * @author goodvice
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
