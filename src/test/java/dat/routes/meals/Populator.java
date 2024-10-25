package dat.routes.meals;

import dat.daos.impl.MealDAO;
import dat.dtos.MealDTO;
import dat.entities.Ingredients;
import dat.entities.Meal;
import dat.security.daos.SecurityDAO;
import dat.security.entities.Role;
import dat.security.entities.User;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Set;

public class Populator {

    private static EntityManagerFactory emf;
    private static MealDAO mealDAO;
    private static SecurityDAO securityDAO;

    public Populator(EntityManagerFactory emf, MealDAO mealDAO) {
        this.emf = emf;
        this.mealDAO = mealDAO;
    }

    public void populateDatabase() {
        // Burger
        Meal m1 = Meal.builder()
                .mealName("Burger")
                .mealDescription("A delicious burger")
                .mealInstructions("Grill beef, assemble burger")
                .mealPrepTime(10)
                .mealRating(3.1)
                .build();
        m1.setIngredients(List.of(
                new Ingredients("Bun", "1"),
                new Ingredients("Beef patty", "150g"),
                new Ingredients("Cheddar cheese", "1 slice")
        ));

        // Pizza
        Meal m2 = Meal.builder()
                .mealName("Pizza")
                .mealDescription("A delicious pizza")
                .mealInstructions("Make dough, bake pizza")
                .mealPrepTime(15)
                .mealRating(4.1)
                .build();
        m2.setIngredients(List.of(
                new Ingredients("Pizza dough", "1 ball"),
                new Ingredients("Tomato sauce", "100ml"),
                new Ingredients("Mozzarella cheese", "100g")
        ));

        Meal m3 = Meal.builder()
                .mealName("Pasta")
                .mealDescription("A delicious pasta")
                .mealInstructions("Cook pasta, mix with sauce")
                .mealPrepTime(20)
                .mealRating(5.1)
                .build();
        m3.setIngredients(List.of(
                new Ingredients("Spaghetti", "200g"),
                new Ingredients("Tomato sauce", "100ml"),
                new Ingredients("Parmesan cheese", "50g")
        ));

        MealDTO mDTO1 = new MealDTO(m1);
        MealDTO mDTO2 = new MealDTO(m2);
        MealDTO mDTO3 = new MealDTO(m3);

        mealDAO.create(mDTO1);
        mealDAO.create(mDTO2);
        mealDAO.create(mDTO3);
    }

        //TODO Fix all security related methods, so the tests in securityRouteTest can be run

    //security
    public List<User> createUsers(List<Role> roles) {
        return List.of(
                new User(
                        "User1",
                        "1234",
                        Set.of(roles.get(0))
                ),
                new User(
                        "User2",
                        "1234",
                        Set.of(roles.get(0))
                ),
                new User(
                        "Admin1",
                        "1234",
                        Set.of(roles.get(1))
                )
        );
    }

    //security
    public List<Role> createRoles() {
        return List.of(
                new Role("s"),
                new Role("admin")
        );
    }

    //security
    public void persist(List<?> entities) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            entities.forEach(em::persist);
            em.getTransaction().commit();
        }
    }

    //security
    public void persistRoleIfNotExists(Role role) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Role existingRole = em.find(Role.class, role.getRoleName());
            if (existingRole == null) {
                em.persist(role);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void clearDatabase() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.createQuery("DELETE FROM Meal").executeUpdate();
            em.createQuery("DELETE FROM Ingredients").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();

            em.getTransaction().commit();
        }
    }
}