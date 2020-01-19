package si.recek.quarkus.fruit;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/fruits",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<Fruit> getFruit(@PathVariable String name) {
        Optional<Fruit> fruitOptional = fruitService.getByName(name);
        if (fruitOptional.isPresent()) {
            return ResponseEntity.ok(fruitOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping()
    public List<Fruit> getFruitAll() {
        return fruitService.getAllFruits();
    }

    @PostMapping()
    public ResponseEntity createFruit(@RequestBody Fruit fruit) {
        Long id;
        try {
            id = fruitService.create(fruit);
        } catch (FruitCreationException fce) {
            return ResponseEntity.badRequest().body(fce.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}