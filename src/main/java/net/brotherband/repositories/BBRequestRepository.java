package net.brotherband.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brotherband.models.BBRequest;
import net.brotherband.models.Users;
import net.brotherband.models.BBRequest.RequestStatus;

@Repository
public interface BBRequestRepository extends JpaRepository<BBRequest, UUID> {
    
    List<BBRequest> findBySenderAndStatus(Users sender, RequestStatus status);
    
    List<BBRequest> findByReceiverAndStatus(Users receiver, RequestStatus status);
    
    boolean existsBySenderAndReceiverAndStatus(Users sender, Users receiver, RequestStatus status);
}