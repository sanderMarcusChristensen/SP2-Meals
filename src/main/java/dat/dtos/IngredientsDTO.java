package dat.dtos;

import dat.entities.Ingredients;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class IngredientsDTO {

    private Integer id;
    private String name;
    private String quantity;

    //Entity to DTO
    public IngredientsDTO(Ingredients ingredients) {
        this.id = ingredients.getId();
        this.name = ingredients.getName();
        this.quantity = ingredients.getQuantity();
    }

    public IngredientsDTO(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientsDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity);
    }
}
