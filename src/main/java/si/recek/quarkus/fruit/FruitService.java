package si.recek.quarkus.fruit;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Optional;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    private final Jedis jedis;

    public FruitService(FruitRepository fruitRepository, Jedis jedis) {
        this.fruitRepository = fruitRepository;
        this.jedis = jedis;
    }

    public List<Fruit> getAllFruits(){
        List<Fruit> fruits = fruitRepository.findAll();
        for (Fruit fruit : fruits) {
            String redisName = jedis.get(fruit.getId().toString());
            System.out.println("Redis name for id: " + fruit.getId() + " is " + redisName );
        }
        return fruits;
    }

    public Optional<Fruit> getByName(String name) {
        return fruitRepository.findByNameIgnoreCase(name);
    }

    public Long create(Fruit fruit) throws FruitCreationException {
        try {
            Fruit storedFruit = fruitRepository.save(fruit);
            jedis.append(storedFruit.getId().toString(), storedFruit.getName());
            return storedFruit.getId();
        } catch (Exception e) {
            throw new FruitCreationException(e.getMessage());
        }
    }
}
