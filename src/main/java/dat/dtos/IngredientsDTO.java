package dat.dtos;

import dat.entities.Ingredients;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public IngredientsDTO(Integer id, String name, String quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
}
