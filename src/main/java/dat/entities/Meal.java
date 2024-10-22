package dat.entities;

import dat.dtos.MealDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meals")
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
        this.mealId = mealDTO.getId();
        this.mealName = mealDTO.getMealName();
        this.mealDescription = mealDTO.getMealDescription();
        this.mealInstructions = mealDTO.getMealInstructions();
        this.mealPrepTime = mealDTO.getMealPrepTime();
        this.mealRating = mealDTO.getMealRating();

    }

    //Add equals and hashCode methods
}
