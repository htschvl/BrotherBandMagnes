package net.brotherband.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import net.brotherband.models.Messages;

public interface MessagesRepository extends JpaRepository<Messages, UUID> {

    
}
