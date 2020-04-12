package edu.upc.eetac.dsa;


public class GameObject {

    //Basic object values
    private  String name;
    private  String id;

   //Public constructor to initialize object
    public GameObject(String name){
        this.name = name;
        this.id = RandomUtils.getId();
    }

    //Empty constructor for de API REST
    public GameObject(){
    }

    //Getters and setters
    public void setName(String name){this.name =name;}
    public void setId(String id){this.id =id;}
    public String getName() {
        return this.name;
    }
    public String getId() {return id;}

    //Returns in string format the object
    @Override
    public String toString(){
        return "Object [id=" + this.getId() + ", name=" + this.getName() + "]" ;
    }
}
