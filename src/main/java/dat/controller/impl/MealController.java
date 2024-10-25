package dat.controller.impl;

import dat.config.HibernateConfig;
import dat.controller.IController;
import dat.daos.impl.MealDAO;
import dat.dtos.MealDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MealController implements IController<MealDTO, Integer> {

    private final MealDAO dao;

    public MealController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = MealDAO.getInstance(emf);
    }


    @Override
    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        MealDTO mealDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(mealDTO, MealDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<MealDTO> mealDTOS = dao.readAll();
        //response
        ctx.res().setStatus(200);
        ctx.json(mealDTOS, MealDTO.class);
    }

    @Override
    public void create(Context ctx) {
        //request
        MealDTO jsonRequest = ctx.bodyAsClass(MealDTO.class);
        // DTO
        MealDTO mealDTO = dao.create(jsonRequest);
        //response
        ctx.res().setStatus(201);
        ctx.json(mealDTO, MealDTO.class);
    }

    @Override
    public void update(Context ctx) {
        //request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        MealDTO jsonRequest = ctx.bodyAsClass(MealDTO.class);
        MealDTO mealDTO = dao.update(id, jsonRequest);
        //response
        ctx.res().setStatus(201);
        ctx.json(mealDTO, MealDTO.class);
    }

    @Override
    public void delete(Context ctx) {
        //reponse
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        // status
        ctx.res().setStatus(204);
    }

    public void maxPrepTime(Context ctx) {
        //request
        int maxPrepTime = ctx.pathParamAsClass("time", Integer.class).check(m -> m > 0, "Max prep time must be greater than 0").get();
        // DTO
        List<MealDTO> mealDTOS = dao.maxPrepTime(maxPrepTime);
        //response
        ctx.res().setStatus(200);
        ctx.json(mealDTOS, MealDTO.class);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public MealDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(MealDTO.class)
                .check(m -> m.getMealName() != null && !m.getMealName().isEmpty(), "Meal name must be set")
                .check(m -> m.getMealDescription() != null && !m.getMealDescription().isEmpty(), "Meal description must be set")
                .check(m -> m.getMealInstructions() != null && !m.getMealInstructions().isEmpty(), "Meal instructions must be set")
                .check(m -> m.getMealPrepTime() > 0, "Meal prep time must be greater than 0")
                .check(m -> m.getMealRating() >= 0 && m.getMealRating() <= 5, "Meal rating must be between 0 and 5")
                .get();
    }
}