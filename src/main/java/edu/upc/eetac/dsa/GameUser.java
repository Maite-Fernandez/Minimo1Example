package edu.upc.eetac.dsa;
import java.util.LinkedList;
import java.util.List;

public class GameUser {

    //Basic user values
    public String id;
    public String name;
    public String surname;

    //Game user objects
    private List<GameObject> listGameObjects=null;

    //Public constructor to initialize user
    public GameUser(String name, String surname){
        this.name = name;
        this.surname = surname;
        this.id = RandomUtils.getId();
        this.listGameObjects = new LinkedList<GameObject>();
    }

    //Empty constructor for de API REST
    public GameUser(){}

    //Getters and Setters

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getId() { return id; }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public int getSizeGameObjectsList(){
        return this.listGameObjects.size();
    }

    //Swaggers Magic
    public void setListGameObjects(List<GameObject> list){
        this.listGameObjects = list;
    }
    public List<GameObject> getListGameObjects(){
        return this.listGameObjects;
    }

    //Adds an object to the user list
    public void setGameObject(GameObject object){
            this.listGameObjects.add(object);
    }

    //Returns user's object list
    public List<GameObject> getGameObjects(){
        return this.listGameObjects;
    }

    //Returns in string format the user
    @Override
    public String toString(){
        return "User [id=" + this.getId() + ", name=" + this.getName() + ", surname=" + this.getSurname() +"]" ; }
}
