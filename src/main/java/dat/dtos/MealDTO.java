package dat.dtos;

import dat.entities.Meal;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
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

    public MealDTO(Meal meal) {
        this.id = meal.getMealId();
        this.mealName = meal.getMealName();
        this.mealDescription = meal.getMealDescription();
        this.mealInstructions = meal.getMealInstructions();
        this.mealPrepTime = meal.getMealPrepTime();
        this.mealRating = meal.getMealRating();
    }

    public MealDTO(String mealName, String mealDescription, String mealInstructions, double mealPrepTime, double mealRating) {
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.mealInstructions = mealInstructions;
        this.mealPrepTime = mealPrepTime;
        this.mealRating = mealRating;
    }

    public static List<MealDTO> toMealDTOList(List<Meal> meals) {
        return meals.stream().map(MealDTO::new).collect(Collectors.toList());
    }

    //Add equals and hashCode methods
}
