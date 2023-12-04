package io.github.quarkustreinamento.quarkussocial.rest;

import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.github.quarkustreinamento.quarkussocial.rest.dto.CreateUserRequest;
import io.github.quarkustreinamento.quarkussocial.service.UserService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class UserResouce {

    @Inject
    private UserService userService;

    @POST
    public Response createUser(@Valid CreateUserRequest userRequest){

        User user = userService.save(
                new User(userRequest.getName(), userRequest.getAge()));

        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @GET
    public Response listAllUser(){
        return Response.ok(userService.findAll()).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id){
        userService.deleteUser(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userRequest){
        userService.atualizar(new User(id,
                userRequest.getName(),
                userRequest.getAge()));

        return Response.noContent().build();
    }
}
