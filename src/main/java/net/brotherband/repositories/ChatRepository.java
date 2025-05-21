package net.brotherband.repositories;

import net.brotherband.models.Chat;
import net.brotherband.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends MongoRepository<Chat, UUID> {

    List<Chat> findByParticipantsContaining(User user);
    
    List<Chat> findByIsGroupChat(boolean isGroupChat);
    
    Optional<Chat> findByIdAndParticipantsContaining(UUID chatId, User user);
    
    List<Chat> findByParticipantsContainingAndIsGroupChat(User user, boolean isGroupChat);
    
    boolean existsByParticipantsContainsAndIsGroupChat(User user, boolean isGroupChat);
    
    long countByParticipantsContaining(User user);
    
    List<Chat> findByChatNameIgnoreCaseContaining(String chatName);
}
