package edu.upc.eetac.dsa;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.filter.HttpMethodOverrideFilter;

import java.util.*;

public class GameManagerImpl implements GameManager {

    //Basic GameManager values
    private static GameManager instance;
    private HashMap<String , GameUser> mapUser;
    private List<GameObject> listGameObjects;
    private static Logger log = Logger.getLogger(GameManagerImpl.class);

    //Private Constructor for Singleton
    private GameManagerImpl(){
        this.mapUser = new HashMap<String, GameUser>();
        this.listGameObjects = new LinkedList<GameObject>();
    }
    //Singleton implementation for the instance of the GameManager
    public static GameManager getInstance(){
        if(instance == null) {
            instance = new GameManagerImpl();
        }
        return instance;
    }

    //Get alphabetically sorted list of users
    @Override
    public List<GameUser> getSortedUsers() {
        //Map of Users is not empty
        log.info("Get Sorted Users");
        if(this.mapUser != null) {
            List<GameUser> result = new LinkedList<GameUser>(mapUser.values());

            Collections.sort(result, new Comparator<GameUser>() {
                @Override
                public int compare(GameUser u1, GameUser u2) {
                    //ToIgnoreCase: To not distinguish between Capital and LowerCase
                    return u1.getName().compareToIgnoreCase(u2.getName());
                }
            });
            log.info("List of users with alphabetical order: " + result.toString());
            return result; //200 OK PETITION
        }
        else {
            log.warn("Users list is empty");
            return null; //404 (Empty Table)

        }

    }

    //Get number of users
    @Override
    public int getNumUsers() { return this.mapUser.size(); }

    //Add a user
    @Override
    public String addUser(GameUser u) {
        log.info("Add user " + u);
        this.mapUser.put(u.getId(),u);
        log.info("New user added");
        return u.getId();

    }
    @Override
    public String addUser(String name, String surname) {
        GameUser u = new GameUser(name,surname);
        this.addUser(u);
        return u.getId();
    }

    //Get information about a user
    @Override
    public GameUser getUser(String id) {
        GameUser u = this.mapUser.get(id);
        log.info("Get User("+id+")");
        if(u!=null) { log.info("Get User (" + id + "): " + u); }
        else{ log.error("Not found " + id);}
        return u;
    }


    //Add an object
    @Override
    public  String addGameObject(GameObject o) {
        log.info("Add Game Object " + o);
        this.listGameObjects.add(o);
        log.info("New Game Object added");
        return o.getId();
    }
    @Override
    public String addGameObject(String name) {
        GameObject o = new GameObject(name);
        this.addGameObject(o);
        return o.getId();

    }


    //Get information about a game object
    @Override
    public GameObject getGameObject(String id){
        log.info("Get Game Object ("+id+")");
        for (GameObject o: this.listGameObjects) {
            if (o.getId().equals(id)) {
                log.info("Get Game Object ("+id+"): "+o);
                return o;
            }
        }
        log.error("Not found");
        return null;
    }

    //Add an object to a user
    @Override
    public String addUserGameObject(GameUser u, GameObject o) {
        log.info("Add object to user " + o);
        u.setGameObject(o);
        log.info("New object added");
        return u.getId();
    }
    @Override
    public String addUserGameObject(String userId, String gameObjectId){
        GameUser u = this.mapUser.get(userId);
        if(u!=null) {
            for(GameObject o : listGameObjects){
                if (o.getId().equals(gameObjectId)) {
                    log.info("Add User Object (" + gameObjectId + "): " + o);
                    this.addUserGameObject(u, o);
                    return u.getId();
                }
            }
        }
        log.error("Object or user not found.");
        return null;
    }

    //Get object list of a user in insertion order
    @Override
    public List<GameObject> getUserGameObjects(String id) {
        log.info("Get User Game Objects ("+id+")");
        GameUser u = this.mapUser.get(id);
        if(u!=null) {
            List<GameObject> o = u.getGameObjects();
            if (o != null) {
                log.info("Get User Game Object(" + id + "): " + o);
                return o;
            } else {
                log.warn("Is empty");
            }
        }
        log.error("User not found");
        return null;
    }

    //Get number of objects of a user
    @Override
    public int getNumGameObjectsUser(String id) {
        GameUser u = this.mapUser.get(id);
        return u.getSizeGameObjectsList();
    }

    //Update a user
    @Override
    public GameUser updateUser(String id, String name, String surname) {
        GameUser u = this.mapUser.get(id);

        if (u!=null) {
            log.info(u+"update");
            u.setName(name);
            u.setSurname(surname);
            log.info(u+" updated ");
        }
        else {
            log.error("Not found");
        }

        return u;
    }
    //Clear resources
    @Override
    public void clearResources() {
        this.listGameObjects.clear();
        this.mapUser.clear();
    }
}
