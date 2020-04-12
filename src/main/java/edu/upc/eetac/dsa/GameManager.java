package edu.upc.eetac.dsa;

import java.util.List;

public interface GameManager {

    //Get alphabetically sorted list of users
    List<GameUser> getSortedUsers();

    //Add a user
    GameUser addUser(String name, String surname);
    GameUser addUser(GameUser user);

    //Update a user
    GameUser updateUser(String id, String name, String surname);

    //Get number of users
    int getNumUsers();

    //Get information about a user
    GameUser getUser(String id);

    //Add an object
    GameObject addGameObject(String name);
    GameObject addGameObject(GameObject gameObject);


    //Get a game object
    GameObject getGameObject(String id);

    //Add an object to a user
    GameObject addUserGameObject(GameUser user, GameObject gameObject);

    //Get object list of a user in insertion order
    List<GameObject> getUserGameObjects(String id);

    //Get number of objects of a user
    int getNumGameObjectsUser(String id);

    //Clear resources
    void clearResources();

}
