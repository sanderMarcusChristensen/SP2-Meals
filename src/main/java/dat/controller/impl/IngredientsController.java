package dat.controller.impl;

import dat.controller.IController;
import dat.daos.impl.IngredientsDAO;
import dat.dtos.IngredientsDTO;
import dat.exceptions.ApiException;
import io.javalin.http.Context;

import java.util.List;

public class IngredientsController implements IController<IngredientsDTO,Integer> {

    private IngredientsDAO dao;

    public IngredientsController(IngredientsDAO dao) {
        this.dao = dao;
    }


    @Override
    public void read(Context ctx) {

        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            IngredientsDTO dto = dao.read(id);

            if(dto != null) {
                ctx.json(dto);
                ctx.status(200);
            } else {
                ctx.json(new ApiException(404, "Ingredient not found"));
            }
        } catch (Exception e) {
            ctx.json(new ApiException(500, "Something went wrong trying to find Ingredient with that id "));
        }
    }

    @Override
    public void readAll(Context ctx) {


    }

    @Override
    public void create(Context ctx) {

        try{
            IngredientsDTO dto = ctx.bodyAsClass(IngredientsDTO.class);
            IngredientsDTO saved = dao.create(dto);

            if(saved != null){
                ctx.json(saved);
                ctx.status(201);
            } else {                // sæt de rigtig beskeder på
                throw new ApiException(422,"Unprocessable Content, something went wrong trying to create a ingredient, please try again");
            }

        } catch (Exception e) {
            throw new ApiException(500,e.getMessage());
        }

    }

    @Override
    public void update(Context ctx) {

        try{
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            IngredientsDTO dto = ctx.bodyAsClass(IngredientsDTO.class);
            dto.setId(id);

            IngredientsDTO updateDTO = dao.update(id,dto);
            if(updateDTO != null){
                ctx.json(updateDTO);
                ctx.status(201);
            } else {
                throw new ApiException(422,"Unprocessable Content something went wrong trying to update a ingredient, please try again please try again");
            }

        } catch (Exception e) {
            throw new ApiException(500,e.getMessage());
        }


    }

    @Override
    public void delete(Context ctx) {
        try{
            Integer id = Integer.parseInt(ctx.pathParam("id"));

            IngredientsDTO dto = new IngredientsDTO();
            dto.setId(id);

            dao.delete(id);
            ctx.status(200);
        } catch (Exception e) {
            throw new ApiException(500,"Something went wrong trying to delete a ingredient, please try again");
        }
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
