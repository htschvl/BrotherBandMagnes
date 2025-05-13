package net.brotherband.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brotherband.models.Bonds;


@Repository
public interface BondsRepository extends JpaRepository<Bonds, UUID> {
    
}