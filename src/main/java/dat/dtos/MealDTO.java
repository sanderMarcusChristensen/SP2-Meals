package dat.dtos;

import dat.entities.Meal;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MealDTO {
    private Integer id;
    private String mealName;
    private String mealDescription;
    private String mealInstructions;
    private double mealPrepTime;
    private double mealRating;

    private List<IngredientsDTO> ingredients;

    public MealDTO(Meal meal) {
        this.id = meal.getMealId();
        this.mealName = meal.getMealName();
        this.mealDescription = meal.getMealDescription();
        this.mealInstructions = meal.getMealInstructions();
        this.mealPrepTime = meal.getMealPrepTime();
        this.mealRating = meal.getMealRating();

        if(meal.getIngredients() != null) {
            this.ingredients = meal.getIngredients().stream()
                    .map(IngredientsDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public MealDTO(String mealName, String mealDescription, String mealInstructions, double mealPrepTime, double mealRating, List<IngredientsDTO> ingredients) {
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.mealInstructions = mealInstructions;
        this.mealPrepTime = mealPrepTime;
        this.mealRating = mealRating;
        this.ingredients = ingredients;
    }

    public static List<MealDTO> toMealDTOList(List<Meal> meals) {
        return meals.stream().map(MealDTO::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealDTO mealDTO = (MealDTO) o;
        return Double.compare(mealPrepTime, mealDTO.mealPrepTime) == 0 && Double.compare(mealRating, mealDTO.mealRating) == 0 && Objects.equals(id, mealDTO.id) && Objects.equals(mealName, mealDTO.mealName) && Objects.equals(mealDescription, mealDTO.mealDescription) && Objects.equals(mealInstructions, mealDTO.mealInstructions) && Objects.equals(ingredients, mealDTO.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mealName, mealDescription, mealInstructions, mealPrepTime, mealRating, ingredients);
    }
}
