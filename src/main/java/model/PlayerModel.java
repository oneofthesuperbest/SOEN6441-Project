package model;
/**
 *  Represents a player
 */
public class PlayerModel {
    private String d_name;

    /**
     *  Creates a player with the specified name.
     *  @param p_name The players name.
     */
    public PlayerModel(String p_name){
        this.d_name = p_name;
    }

    /**
     * Get the player's name.
     * @return A string representing the player's name.
     */
    public String getName(){
        return this.d_name;
    }
}