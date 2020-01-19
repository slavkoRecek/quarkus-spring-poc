package si.recek.quarkus.fruit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FruitRepository extends JpaRepository<Fruit, Long> {

    Optional<Fruit> findByNameIgnoreCase(String name);
}
