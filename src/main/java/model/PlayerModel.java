package model;
/**
 *  Represents a player
 */
public class PlayerModel {
    private String name;

    /**
     *  Creates a player with the specified name.
     *  @param name The players name.
     */
    public PlayerModel(String name){
        this.name = name;
    }

    /**
     * Get the player's name.
     * @return A string representing the player's name.
     */
    public String getName(){
        return this.name;
    }
}