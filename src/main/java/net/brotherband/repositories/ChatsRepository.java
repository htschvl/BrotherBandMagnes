package net.brotherband.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brotherband.models.Chats;

@Repository
public interface ChatsRepository extends JpaRepository<Chats, UUID> {
    
}

   