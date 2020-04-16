package edu.upc.eetac.dsa;

//Swagger Imports
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//Log4j imports
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Api(value = "/users", description = "Endpoint to Game Manager Service")
@Path("/users")
public class GameManagerService {

        static final Logger logger = Logger.getLogger(GameManagerService.class);
        private GameManager manager;
        public GameManagerService(){
            //Configuring Log4j, location of the log4j.properties file and must always be inside the src folder
            PropertyConfigurator.configure("src/main/resources/log4j.properties");
            this.manager = GameManagerImpl.getInstance();
            if (this.manager.getNumUsers() == 0) {

                //Adding Users
                String userId1 = this.manager.addUser("Maite","Fernandez");
                String userId2 = this.manager.addUser("Toni","Oller");
                String userId3 = this.manager.addUser("Juan","Lopez");

                //Adding GameObjects
                String swordId = this.manager.addGameObject(new GameObject("Sword"));
                String shieldId = this.manager.addGameObject(new GameObject("Shield"));
                String coinId= this.manager.addGameObject(new GameObject("Coin"));

                //Adding objects to users
                //Sword and Shield to Maite
                this.manager.addUserGameObject(userId1,swordId);
                this.manager.addUserGameObject(userId1,shieldId);
                //Only Coin for Toni
                this.manager.addUserGameObject(userId2,coinId);
                //Only sword for Juan
                this.manager.addUserGameObject(userId3,swordId);
            }
        }
        //When multiple GET, PUT, POSTS & DELETE EXIST on the same SERVICE, path must be aggregated
        //Users List
        @GET
        @ApiOperation(value = "Get all Users", notes = "Retrieves the list of Users")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "Successful", response = GameUser.class, responseContainer="List"),
        })
        @Path("/listUsers")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getUsers() {

            List<GameUser> users = this.manager.getSortedUsers();

            GenericEntity<List<GameUser>> entity = new GenericEntity<List<GameUser>>(users) {};
            return Response.status(201).entity(entity).build()  ;
        }

        //Add a user
        @POST
        @ApiOperation(value = "create a new User", notes = "Adds a new user given name and surname")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "Successful", response=GameUser.class),
                @ApiResponse(code = 500, message = "Validation Error")
        })
        @Path("/addUser/{name}/{surname}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response newUser( @PathParam("name") String name, @PathParam("surname") String surname ) {
            if (name.isEmpty() || surname.isEmpty() )  return Response.status(500).entity(new GameUser()).build();
            String userId =this.manager.addUser(name,surname);
            return Response.status(201).entity(manager.getUser(userId)).build();
        }

        //Update a user
        @PUT
        @ApiOperation(value = "Update a User", notes = "Edits an existing User")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "Successful"),
                @ApiResponse(code = 404, message = "User not found"),
        })
        @Path("/updateUser/{id}/{name}/{surname}")
        public Response updateUser(@PathParam("id") String id,@PathParam("name") String name,@PathParam("surname") String surname ) {

            GameUser user = this.manager.updateUser(id,name,surname);
            if (user == null) return Response.status(404).build();
            return Response.status(201).entity(manager.getUser(id)).build();
        }

        //Get user information
        @GET
        @ApiOperation(value = "Get a User", notes = "Retrieve User")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "Successful", response = GameUser.class),
                @ApiResponse(code = 404, message = "User not found")
        })
        @Path("/getUser/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getUser(@PathParam("id") String id) {
            GameUser user = this.manager.getUser(id);
            if (user == null) return Response.status(404).build();
            else  return Response.status(201).entity(user).build();
        }

        //Get objects of a user
        @GET
        @ApiOperation(value = "Get a User GameObjects", notes = "Retrieve User Game Objects")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "Successful", response = GameObject.class,responseContainer="List"),
                @ApiResponse(code = 404, message = "User not found"),
                @ApiResponse(code = 204, message = "No Game Object found")
        })
        @Path("/getGameObjectsUser/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getUserObject(@PathParam("id") String id) {
            GameUser user = this.manager.getUser(id);
            List<GameObject> listGameObject;
            if (user == null) return Response.status(404).build();
            else {
                if(user.getSizeGameObjectsList()==0){
                    Response.status(204).build();
                }
            }
            listGameObject = manager.getUserGameObjects(id);
            GenericEntity<List<GameObject>> entity = new GenericEntity<List<GameObject>>(listGameObject) {};
            return Response.status(201).entity(entity).build();
        }

        //Adds an object to a user
        @PUT
        @ApiOperation(value = "Adds a Game object to user", notes = "Adds an existing Game Object to user")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "Successful", response=GameUser.class),
                @ApiResponse(code = 500, message = "Validation Error"),
                @ApiResponse(code = 404, message = "User of Object Not found Error")
        })
        @Path("/addGameObjectUser/{userId}/{objectId}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response addObject(@PathParam("userId") String userId,@PathParam("objectId") String gameObjectId ) {
            if (userId.isEmpty() || gameObjectId.isEmpty()) return Response.status(500).entity(new GameUser()).build();
            else {
                GameUser user = manager.getUser(userId);
                GameObject gameObject = manager.getGameObject(gameObjectId);
                if (user == null || gameObject == null) return Response.status(404).entity(new GameUser()).build();
            }
            manager.addUserGameObject(userId, gameObjectId);
            return Response.status(201).entity(manager.getUser(userId)).build();
        }

    }
