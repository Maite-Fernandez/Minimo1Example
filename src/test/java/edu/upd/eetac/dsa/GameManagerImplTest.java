package edu.upd.eetac.dsa;

import edu.upc.eetac.dsa.*;
import org.apache.log4j.Logger;
//Junit 4.13
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class GameManagerImplTest {
    // THE QUICK REMINDER: Remember to name the test class public smh
    //Log4j Logger initialization
    private static Logger logger = Logger.getLogger(GameManagerImplTest.class);
    //GameManager
    public GameManager manager = null;
    //Data structures
    GameUser user;
    String userId;
    String swordId;
    String shieldId;
    String coinId;
    List<GameObject> listGameObjects;

    //SetUp
    @Before
    public void setUp() {
        //Configuring Log4j
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.debug("Debug Test Message!");
        logger.info("Info Test Message!");
        logger.warn("Warning Test Message!");
        logger.error("Error Test Message!");

        //Instancing GameManager Implementation
        manager = GameManagerImpl.getInstance();

        //Initializing Object List
        listGameObjects =  new LinkedList<GameObject>();

        //Initializing Test User
        userId = this.manager.addUser("Maite","Fernandez");

        //Adding GameObjects
        swordId = this.manager.addGameObject(new GameObject("Sword"));
        shieldId = this.manager.addGameObject(new GameObject("Shield"));
        coinId= this.manager.addGameObject(new GameObject("Coin"));

        //Adding objects to the user
        //Sword and Shield to Maite
        this.manager.addUserGameObject(userId,swordId);
        this.manager.addUserGameObject(userId,shieldId);


    }
    //Tests
    //Test to add a user in the system and verify the number of users
    @Test
    public void addUserTest(){
        //Initial Test, initial users in game Zero!
        Assert.assertEquals(1, this.manager.getNumUsers());
        //Adding a user to the GameManager
        manager.addUser("Juan","Lopez");
        Assert.assertEquals(2, manager.getNumUsers());
        //Adding a second user to the GameManager
        manager.addUser("Toni","Oller");
        Assert.assertEquals(3, manager.getNumUsers());
    }

    @Test
    public void addObjectTest(){
        //Test for the objects the test user has equals 2 as setUp method
        Assert.assertEquals(2, manager.getNumGameObjectsUser(userId));
        //Adding an object to the User
        manager.addUserGameObject(userId, coinId);
        //Test if the number of objects inside Test User has increased to 1
        Assert.assertEquals(3, manager.getNumGameObjectsUser(userId));
    }

    //Metodo Teardown
    @After
    public void tearDown() {
        manager.clearResources();
    }
}