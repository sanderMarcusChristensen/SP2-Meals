package dat.controller.impl;

import dat.config.HibernateConfig;
import dat.daos.impl.MealDAO;
import dat.daos.impl.UserDAO;
import dat.dtos.MealDTO;
import dat.security.dtos.RoleDTO;
import dat.security.dtos.UserDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserController {

    private final UserDAO dao;

    public UserController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = UserDAO.getInstance(emf);
    }


    public void readAll(Context ctx) {
        // List of DTOS
        List<UserDTO> dto = dao.readAll();
        //response
        ctx.res().setStatus(200);
        ctx.json(dto, UserDTO.class);
    }

}
