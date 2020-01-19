package si.recek.quarkus.fruit;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public List<Fruit> getAllFruits(){
        return fruitRepository.findAll();
    }

    public Optional<Fruit> getByName(String name) {
        return fruitRepository.findByNameIgnoreCase(name);
    }

    public Long create(Fruit fruit) throws FruitCreationException {
        try {
            return fruitRepository.save(fruit).getId();
        } catch (Exception e) {
            throw new FruitCreationException(e.getMessage());
        }
    }
}
