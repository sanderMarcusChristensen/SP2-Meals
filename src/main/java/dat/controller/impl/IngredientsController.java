package dat.controller.impl;

import dat.controller.IController;
import dat.dtos.IngredientsDTO;
import io.javalin.http.Context;

public class IngredientsController implements IController<IngredientsDTO,Integer> {
    @Override
    public void read(Context ctx) {

    }

    @Override
    public void readAll(Context ctx) {

    }

    @Override
    public void create(Context ctx) {

    }

    @Override
    public void update(Context ctx) {

    }

    @Override
    public void delete(Context ctx) {

    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return false;
    }

    @Override
    public IngredientsDTO validateEntity(Context ctx) {
        return null;
    }
}
