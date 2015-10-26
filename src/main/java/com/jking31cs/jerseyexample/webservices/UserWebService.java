package com.jking31cs.jerseyexample.webservices;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jking31cs.jerseyexample.objects.User;
import com.jking31cs.jerseyexample.stores.UserStore;
/**
 * This web service handles all the different http calls from a client to create, read, update, and delete Users.
 * This is done using Jersey/JAX-RS (Java Application Rest Service API).
 */

@Path("api/users")
public class UserWebService {

    private final UserStore store;

    @Inject
    public UserWebService(UserStore store) {
        this.store = store;
    }

    //Gets all users in the system.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        return store.getAll();
    }

    //Gets user with specific id.
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") Long id) {
        return store.get(id);
    }

    //Creates a new user.
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User saveUser(User user) {
        return store.save(user);
    }
    
    //Updates a user in the system.
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(@PathParam("id") Long id, User user) {
        User user1 = store.get(id);
        if (user1!=null){
        	user1.setEmail(user.getEmail());
        	user1.setName(user.getName());
        	return store.save(user1);
       }
           
       return store.save(user);
    }
    
    //Deletes a user from the system.
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User deleteUser(@PathParam("id") Long id) {
    	User user = store.get(id);
    	if (user==null){
    		throw new RuntimeException("Delete: User with " + id +  " not found");
    	}
        return store.delete(user);
    }

}
