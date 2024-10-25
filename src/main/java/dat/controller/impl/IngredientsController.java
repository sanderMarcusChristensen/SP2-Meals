package dat.controller.impl;

import dat.config.HibernateConfig;
import dat.controller.IController;
import dat.daos.impl.IngredientsDAO;
import dat.dtos.IngredientsDTO;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class IngredientsController implements IController<IngredientsDTO, Integer> {
    private final IngredientsDAO dao;

    //private IngredientsDAO dao;

    public IngredientsController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = IngredientsDAO.getInstance(emf);
    }


    @Override
    public void read(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        IngredientsDTO dto = dao.read(id);
        ctx.res().setStatus(200);
        ctx.json(dto, IngredientsDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<IngredientsDTO> ingredientsDTOS = dao.readAll();
        // Response
        ctx.res().setStatus(200);
        ctx.json(ingredientsDTOS);
    }

    @Override
    public void create(Context ctx) {
        try {
            IngredientsDTO dto = ctx.bodyAsClass(IngredientsDTO.class);
            IngredientsDTO saved = dao.create(dto);

            if (saved != null) {
                ctx.json(saved);
                ctx.status(201);
            } else {                // sæt de rigtig beskeder på
                throw new ApiException(422, "Unprocessable Content, something went wrong trying to create a ingredient, please try again");
            }

        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }

    }

    @Override
    public void update(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
            IngredientsDTO dto = ctx.bodyAsClass(IngredientsDTO.class);
            dto.setId(id);

            IngredientsDTO updateDTO = dao.update(id, dto);
            if (updateDTO != null) {
                ctx.json(updateDTO);
                ctx.status(201);
            } else {
                throw new ApiException(422, "Unprocessable Content something went wrong trying to update a ingredient, please try again please try again");
            }

        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    @Override
    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class)
                .check(this::validatePrimaryKey, "Not a valid id")
                .get();
        dao.delete(id);
        ctx.res().setStatus(204); // No Content after successful deletion
    }


    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public IngredientsDTO validateEntity(Context ctx) {
        return null;
    }
}
