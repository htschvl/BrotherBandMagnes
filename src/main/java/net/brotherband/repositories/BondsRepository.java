package net.brotherband.repositories;

import net.brotherband.models.Bond;
import net.brotherband.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BondsRepository extends JpaRepository<Bond, UUID> {

    // Todos os bonds onde o usuário está como user
    List<Bond> findByUser(User user);

    // Todos os bonds onde o usuário está como brother
    List<Bond> findByBrother(User brother);

    // Bonds onde o par user-brother está diretamente relacionado
    Optional<Bond> findByUserAndBrother(User user, User brother);

    boolean existsByUserAndBrother(User user, User brother);

    // Casos reversos, caso queira checar se a relação existe em qualquer direção (requer lógica extra no service)
    Optional<Bond> findByUserIdAndBrotherId(UUID userId, UUID brotherId);
    
    boolean existsByUserIdAndBrotherId(UUID userId, UUID brotherId);

    // Buscar todos os bonds em que o usuário está envolvido (em qualquer lado)
    List<Bond> findByUserIdOrBrotherId(UUID userId1, UUID userId2);

    long countByUser(User user);
    long countByBrother(User brother);
}
