package net.brotherband.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import net.brotherband.models.BBRequest;
import net.brotherband.models.User;
import net.brotherband.models.BBRequest.RequestStatus;

@Repository
public interface BBRequestRepository extends MongoRepository<BBRequest, UUID> {
    
    List<BBRequest> findBySenderAndStatus(User sender, RequestStatus status);
    List<BBRequest> findBySenderId(String senderId);
    List<BBRequest> findByReceiverId(String receiverId);
    List<BBRequest> findByStatus(BBRequest.RequestStatus status);
    
    List<BBRequest> findByReceiverAndStatus(User receiver, RequestStatus status);
    List<BBRequest> findByReceiverIdAndStatus(String receiverId, RequestStatus status);
    List<BBRequest> findBySenderAndReceiverAndStatus(User sender, User receiver, RequestStatus status);
    List<BBRequest> findBySenderIdAndReceiverIdAndStatus(String senderId, String receiverId, RequestStatus status);
    
    boolean existsBySenderAndReceiverAndStatus(User sender, User receiver, RequestStatus status);
    boolean existsBySenderIdAndReceiverIdAndStatus(String senderId, String receiverId, RequestStatus status);
}

