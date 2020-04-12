package edu.upc.eetac.dsa;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.filter.HttpMethodOverrideFilter;

import java.util.*;

public class GameManagerImpl implements GameManager {

    //Basic GameManager values
    private static GameManager instance;
    private HashMap<String , GameUser> mapUser;
    private List<GameObject> listGameObjects;
    private static Logger logger = Logger.getLogger(GameManagerImpl.class);

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
        if(this.mapUser != null) {
            List<GameUser> result = new LinkedList<GameUser>(mapUser.values());

            Collections.sort(result, new Comparator<GameUser>() {
                @Override
                public int compare(GameUser u1, GameUser u2) {
                    //ToIgnoreCase: To not distinguish between Capital and LowerCase
                    return u1.getName().compareToIgnoreCase(u2.getName());
                }
            });
            //Logger.info("List of users with alphabetical order: " + result.toString());
            return result; //200 OK PETITION
        }
        else {
            return null; //404 (Empty Table)
            //Logger.warn(" is empty");
        }

    }

    //Get number of users
    @Override
    public int getNumUsers() { return this.mapUser.size(); }

    //Add a user
    @Override
    public GameUser addUser(GameUser u) {
        logger.info("new user " + u);
        this.mapUser.put(u.getId(),u);
        logger.info("new user added");
        return u;

    }
    @Override
    public GameUser addUser(String name, String surname) {
        return this.addUser(new GameUser(name,surname));
    }

    //Get information about a user
    @Override
    public GameUser getUser(String id) {
        GameUser u = this.mapUser.get(id);
        logger.info("getUser("+id+")");
        if(u!=null) { logger.info("getUser(" + id + "): " + u); }
        else{ logger.warn("not found " + id);}
        return u;
    }


    //Add an object
    @Override
    public GameObject addGameObject(GameObject o) {
        logger.info("new object " + o);
        this.listGameObjects.add(o);
        logger.info("new object added");
        return o;
    }
    @Override
    public GameObject addGameObject(String name) {
        return this.addGameObject(new GameObject(name));

    }


    //Get information about a game object
    @Override
    public GameObject getGameObject(String id){
        logger.info("getObject("+id+")");
        for (GameObject o: this.listGameObjects) {
            if (o.getId().equals(id)) {
                logger.info("getObject("+id+"): "+o);
                return o;
            }
        }
        logger.warn("not found " + id);
        return null;
    }

    //Add an object to a user
    @Override
    public GameObject addUserGameObject(GameUser u, GameObject o) {
        logger.info("add object to user " + o);
        u.setGameObject(o);
        logger.info("new object added");
        return o;
    }

    //Get object list of a user in insertion order
    @Override
    public List<GameObject> getUserGameObjects(String id) {
        //Logger.info("getUserGameObjects("+id+")");
        GameUser u = this.mapUser.get(id);
        if(u!=null) {
            List<GameObject> o = u.getGameObjects();
            if (o != null) {
                logger.info("getUserObject(" + id + "): " + o);
                return o;
            } else {
                logger.info("is empty ");
            }
        }
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
            logger.info(u+" rebut! ");
            u.setName(name);
            u.setSurname(surname);
            logger.info(u+" updated ");
        }
        else {
            logger.warn("not found");
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
