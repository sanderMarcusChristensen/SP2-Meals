package dat.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dtos.MealDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

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

    }

    public static List<Meal> toMealList(List<MealDTO> mealDTOS) {
        return mealDTOS.stream().map(Meal::new).collect(Collectors.toList());
    }

    //Add equals and hashCode methods
}
