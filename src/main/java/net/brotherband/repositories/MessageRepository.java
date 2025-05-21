package net.brotherband.repositories;

import net.brotherband.models.Chat;
import net.brotherband.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends MongoRepository<Message, UUID> {

    List<Message> findByChatId(String chatId);
    
    List<Message> findBySenderId(String senderId);
    
    List<Message> findByChatAndIsReadFalse(Chat chat);
    
    List<Message> findByChatIdOrderBySentTimeDesc(String chatId);
    
    Optional<Message> findFirstByChatIdOrderBySentTimeDesc(String chatId);
    
    long countByChatId(String chatId);
    
    long countByChatIdAndIsReadFalse(String chatId);
    
    boolean existsBySenderIdAndChatId(String senderId, String chatId);
}
