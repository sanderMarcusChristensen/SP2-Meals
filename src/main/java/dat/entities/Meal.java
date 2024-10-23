package dat.entities;

import dat.dtos.IngredientsDTO;
import dat.dtos.MealDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "meal")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id", nullable = false, unique = true)
    private Integer mealId;

    @Setter
    @Column(name = "meal_name", nullable = false)
    private String mealName;

    @Setter
    @Column(name = "meal_description", nullable = false)
    private String mealDescription;

    @Setter
    @Column(name = "meal_instructions", nullable = false)
    private String mealInstructions;

    @Setter
    @Column(name = "meal_prep_time", nullable = false)
    private double mealPrepTime;

    @Setter
    @Column(name = "meal_rating", nullable = false)
    private double mealRating;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "meal_ingredients",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredients> ingredients;

    public Meal(String mealName, String mealDescription, String mealInstructions, double mealPrepTime, double mealRating) {
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.mealInstructions = mealInstructions;
        this.mealPrepTime = mealPrepTime;
        this.mealRating = mealRating;
    }

    public Meal(MealDTO mealDTO) {
        this.mealId = mealDTO.getMealId();
        this.mealName = mealDTO.getMealName();
        this.mealDescription = mealDTO.getMealDescription();
        this.mealInstructions = mealDTO.getMealInstructions();
        this.mealPrepTime = mealDTO.getMealPrepTime();
        this.mealRating = mealDTO.getMealRating();

        if(mealDTO.getIngredients() != null) {
            this.ingredients = mealDTO.getIngredients().stream()
                    .map(Ingredients::new)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Double.compare(mealPrepTime, meal.mealPrepTime) == 0 && Double.compare(mealRating, meal.mealRating) == 0 && Objects.equals(mealId, meal.mealId) && Objects.equals(mealName, meal.mealName) && Objects.equals(mealDescription, meal.mealDescription) && Objects.equals(mealInstructions, meal.mealInstructions) && Objects.equals(ingredients, meal.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, mealName, mealDescription, mealInstructions, mealPrepTime, mealRating, ingredients);
    }
    public static List<Meal> toMealList(List<MealDTO> mealDTOS) {
        return mealDTOS.stream().map(Meal::new).collect(Collectors.toList());
    }
}