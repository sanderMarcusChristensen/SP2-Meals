package dat.dtos;

import dat.entities.Meal;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MealDTO {
    private Integer mealId;
    private String mealName;
    private String mealDescription;
    private String mealInstructions;
    private double mealPrepTime;
    private double mealRating;

    @Setter
    private List<IngredientsDTO> ingredients;

    public MealDTO(Meal meal) {
        this.mealId = meal.getMealId();
        this.mealName = meal.getMealName();
        this.mealDescription = meal.getMealDescription();
        this.mealInstructions = meal.getMealInstructions();
        this.mealPrepTime = meal.getMealPrepTime();
        this.mealRating = meal.getMealRating();

        if (meal.getIngredients() != null) {
            this.ingredients = meal.getIngredients().stream()
                    .map(IngredientsDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public MealDTO(String mealName, String mealDescription, String mealInstructions, double mealPrepTime, double mealRating) {
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.mealInstructions = mealInstructions;
        this.mealPrepTime = mealPrepTime;
        this.mealRating = mealRating;
    }

    public MealDTO(Integer mealId, String mealName, String mealDescription, String mealInstructions, double mealPrepTime, double mealRating, List<IngredientsDTO> ingredients) {
        this.mealId = mealId;
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
        return Double.compare(mealPrepTime, mealDTO.mealPrepTime) == 0 && Double.compare(mealRating, mealDTO.mealRating) == 0 && Objects.equals(mealId, mealDTO.mealId) && Objects.equals(mealName, mealDTO.mealName) && Objects.equals(mealDescription, mealDTO.mealDescription) && Objects.equals(mealInstructions, mealDTO.mealInstructions) && Objects.equals(ingredients, mealDTO.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, mealName, mealDescription, mealInstructions, mealPrepTime, mealRating, ingredients);
    }
}
