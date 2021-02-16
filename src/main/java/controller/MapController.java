package controller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.IntStream;

import model.ContinentModel;
import model.CoordinateModel;
import model.CountryModel;

/**
 * Controller class for handling map data.
 */
public class MapController {
    private GameEngine d_gameEngine;

    /**
     * Create a new map controller with the specified GameEngine.
     * @param p_gameEngine A GameEngine object which is populated with the map data.
     */
    public MapController(GameEngine p_gameEngine) {
        d_gameEngine = p_gameEngine;
    }

    /**
     * Load the map contents into the game.
     * @param p_fileName   The complete path of the file.
     * @throws IOException Exception while reading the map file.
     */
    public void loadMapData(String p_fileName) throws IOException {
        List<String> l_lines = readMap(p_fileName);
        for (int l_idx = 0; l_idx < l_lines.size(); l_idx++){
            String currentLine = l_lines.get(l_idx);
            // ignore the comments in .map file.
            if (currentLine.startsWith(";")){
                continue;
            }
            String beginningWord = currentLine.split(" ")[0];
            switch(beginningWord) {
                case "[continents]": {
                    l_idx = loadMapContinentsFromFile(l_idx, l_lines);
                    break;
                }
                case "[countries]": {
                    l_idx = loadMapCountriesFromFile(l_idx, l_lines);
                    break;
                }
                case "[borders]": {
                    l_idx = loadMapBordersFromFile(l_idx, l_lines);
                    break;
                }
                case "[files]":
                case "":
                default: {
                    break;
                }
            }
        }
        System.out.println("Map Loaded successfully.");
    }

    /**
     * Load the continents from map file.
     * @param p_idx   Index of the current line.
     * @param p_lines List of all the lines in the map file.
     * @return        current index.
     */
    public int loadMapContinentsFromFile(int p_idx, List<String> p_lines){
        p_idx += 1;
        int l_continentId = 1;
        while(checkSameBlock(p_idx, p_lines)){
            String[] l_segments = p_lines.get(p_idx).split(" ");

            String l_continentName = l_segments[0];
            int l_continentArmy = Integer.parseInt(l_segments[1]);
            // Default color.
            String l_color = "#00000";
            if (l_segments.length == 3){
                l_color = l_segments[2];
            }

            d_gameEngine.getMapState().getListOfContinents()
                    .add(new ContinentModel(l_continentId, l_continentName, l_color, l_continentArmy));

            p_idx++;
            l_continentId++;
        }
        System.out.println("...Loaded Continents. Total: " + d_gameEngine.getMapState().getListOfContinents().size());
        return p_idx;
    }

    /**
     * Load the countries from map file.
     * @param p_idx   Index of the current line
     * @param p_lines List of all the lines in the map file.
     * @return current index
     */
    public int loadMapCountriesFromFile(int p_idx, List<String> p_lines){
        p_idx += 1;
        while(checkSameBlock(p_idx, p_lines)){
            String[] l_segments = p_lines.get(p_idx).split(" ");

            int l_countryId = Integer.parseInt(l_segments[0]);
            String l_countryName = l_segments[1];
            int l_continentId = Integer.parseInt(l_segments[2]);
            ContinentModel l_parentContinent = getContinentById(l_continentId);
            // default coordinates
            int l_x_coordinate = -1;
            int l_y_coordinate = -1;
            if (l_segments.length == 5){
                l_x_coordinate = Integer.parseInt(l_segments[3]);
                l_y_coordinate = Integer.parseInt(l_segments[4]);
            }
            CoordinateModel l_coordinate = new CoordinateModel(l_x_coordinate, l_y_coordinate);

            CountryModel l_currentCountry = new CountryModel(l_countryId, l_countryName, l_parentContinent, l_coordinate);
            d_gameEngine.getMapState().getListOfCountries().add(l_currentCountry);
            // Add the country to the continent as well.
            getContinentById(l_continentId).getCountries().add(l_currentCountry);
            p_idx++;
        }
        System.out.println("...Loaded Countries. Total: " + d_gameEngine.getMapState().getListOfCountries().size());
        return p_idx;
    }

    /**
     * Load the borders from map file.
     * @param p_idx   Index of the current line
     * @param p_lines List of all the lines in the map file.
     * @return      current index
     */
    public int loadMapBordersFromFile(int p_idx, List<String> p_lines){
        int totalCountries = d_gameEngine.getMapState().getListOfCountries().size();
        int[][] l_graph = new int[totalCountries][totalCountries];

        p_idx += 1;
        while(checkSameBlock(p_idx, p_lines)){
            String[] l_segments = p_lines.get(p_idx).split(" ");
            // parse the string segments into integers.
            int[] l_intSegments = Arrays.stream(l_segments).mapToInt(Integer::parseInt).toArray();

            // slice the intSegments array [1:length]
            int l_start = 1;
            int l_end = l_intSegments.length;
            int[] l_neighbours = IntStream.range(l_start, l_end).map(i -> l_intSegments[i]).toArray();
            int l_countryId = l_intSegments[0];

            for (int neighbour: l_neighbours){
                // creating only one way connections at the moment.
                // Assuming, 1-way connections are possible.
                l_graph[l_countryId - 1][neighbour - 1] = 1;
            }

            p_idx++;
        }

        d_gameEngine.getMapState().setBorderGraph(l_graph);
        System.out.println("...Loaded borders.");
        return p_idx;
    }

    /**
     * Checks whether the given line is a part of an
     * existing block in the map file.
     * The check is based on the fact that the lines in a block are
     * not blank and contain at least 1 space.
     *
     * @param p_idx   Index of the current line.
     * @param p_lines List of all the lines in the map.
     * @return      Boolean indicating whether the current line is a part of the existing block.
     */
    public boolean checkSameBlock(int p_idx, List<String> p_lines){
        if (p_idx >= p_lines.size()){
            return false;
        }
        String l_currentLine = p_lines.get(p_idx);
        return !l_currentLine.equals("") && l_currentLine.contains(" ");
    }

    /**
     * Helper method for retrieving the CountryModel object from country id.
     * @param p_id Order of the country according to the map.
     * @return      Country.
     */
    public CountryModel getCountryById(int p_id){
        for(CountryModel country : d_gameEngine.getMapState().getListOfCountries()){
            if (country.getCountryId() == p_id){
                return country;
            }
        }
        return null;
    }

    /**
     * Helper method for retrieving the ContinentModel object from continent id.
     * @param p_id Order of the continent according to the map.
     * @return      Continent.
     */
    public ContinentModel getContinentById(int p_id){
        for(ContinentModel l_continent : d_gameEngine.getMapState().getListOfContinents()){
            if (l_continent.getContinentId() == p_id){
                return l_continent;
            }
        }
        return null;
    }

    /**
     * editContinent takes in relevant commandParameters and calls the associated
     * methods which are addContinent and removeContinent.
     * @param p_commandParameters List of command parameter.
     */
    public void editContinent(String[] p_commandParameters){
        int l_totalParameterSize = p_commandParameters.length;
        for (int l_i = 0; l_i < l_totalParameterSize; l_i++){
            String l_command = p_commandParameters[l_i];
            switch (l_command){
                case "-add": {
                    l_i++;
                    int l_continentId = Integer.parseInt(p_commandParameters[l_i]);
                    l_i++;
                    int l_continentValue = Integer.parseInt(p_commandParameters[l_i]);
                    addContinent(l_continentId, l_continentValue);
                    break;
                }

                case "-remove":{
                    l_i++;
                    int l_continentId = Integer.parseInt(p_commandParameters[l_i]);
                    removeContinent(l_continentId);
                    break;
                }

                default:{
                    break;
                }
            }
        }
    }

    /**
     * addContinent adds valid continents to the map.
     * A continent may not be added if it already exists in the gameEngine.
     * Default continent name is of the form: `continent-<continentId>`
     * @param p_continentId Id of the continent to be added.
     * @param p_continentValue The army value of the continent.
     */
    private void addContinent(int p_continentId, int p_continentValue){
        // If the continent is already present.
        if (getContinentById(p_continentId) != null){
            System.out.println("error: Unable to add continent with id: " + p_continentId + ". Already exists.");
            return;
        }

        // Create a new continent and add it to the list of continents.
        String l_continentName = "continent-" + p_continentId;
        String l_color = "#00000";
        ContinentModel l_newContinent = new ContinentModel(p_continentId, l_continentName, l_color, p_continentValue);
        d_gameEngine.getMapState().getListOfContinents().add(l_newContinent);

        System.out.println("Continent with id: " + p_continentId + " added successfully.");
    }

    /**
     * removerContinent removes a continent from the map.
     * @param p_continentId Id of the continent to be removed.
     */
    private void removeContinent(int p_continentId){
        // If the continent doesn't exist.
        ContinentModel l_continentToRemove = getContinentById(p_continentId);
        if(l_continentToRemove == null){
            System.out.println("error: Unable to remove continent with id: " + p_continentId +
                    ". Does not exists.");
            return;
        }

        // delete all the countries within this continent.
        // delete the continent from mapState.
        ArrayList<Integer> l_childCountries = new ArrayList<>();
        for(CountryModel l_country: l_continentToRemove.getCountries()){
            l_childCountries.add(l_country.getCountryId());
        }

        for(int l_countryId : l_childCountries){
            removeCountry(l_countryId);
        }

        d_gameEngine.getMapState().getListOfContinents().remove(l_continentToRemove);
        System.out.println("Continent with id: " + p_continentId + "removed successfully.");
    }

    /**
     * Remove a country from the map using the country id.
     * @param p_countryId Id of the country to be removed.
     */
    private void removeCountry(int p_countryId){
        CountryModel l_country = getCountryById(p_countryId);
        if (l_country == null){
            System.out.println("error: Unable to remove continent with id: " + p_countryId +
                    ". Does not exists.");
            return;
        }
        //remove country from the continent, the list of countries, borders.
        int l_countryOrder = d_gameEngine.getMapState().getListOfCountries().indexOf(l_country);
        l_country.getContinent().getCountries().remove(l_country);
        d_gameEngine.getMapState().getListOfCountries().remove(l_country);
        removeBorder(l_countryOrder);
    }

    /**
     * Remove the border associated with the given position.
     * A new border matrix is created which does not include
     * the country for which the border is to be removed.
     * @param p_countryPosition position of the country
     *                        (in the country list) for which
     *                        the border is to be removed.
     */
    public void removeBorder(int p_countryPosition){
        int[][] l_currentBorderGraph = d_gameEngine.getMapState().getBorderGraph();
        int l_newSize = l_currentBorderGraph[0].length - 1;

        int[][] l_newBorderGraph = new int[l_newSize][l_newSize];

        // Copy the new graph while avoiding the position of the country to be removed.

        for (int row = 0; row < p_countryPosition; row++) {
            for (int col = 0; col < p_countryPosition; col++) {
                l_newBorderGraph[row][col] = l_currentBorderGraph[row][col];
            }
        }

        for (int row = l_newSize - 1; row >= p_countryPosition; row--) {
            for (int col = l_newSize - 1; col >= p_countryPosition; col--) {
                l_newBorderGraph[row][col] = l_currentBorderGraph[row + 1][col + 1];
            }
        }

        d_gameEngine.getMapState().setBorderGraph(l_newBorderGraph);
    }

    /**
     * HELPER: : : REMOVE BEFORE MERGE!!!!!
     */
    public void showMap(){
        for (ContinentModel l_continent: d_gameEngine.getMapState().getListOfContinents()){
            int l_index = l_continent.getContinentId();
            String l_name = l_continent.getName();
            String l_color = l_continent.getColor();
            int l_army = l_continent.getArmy();
            System.out.println(l_index + " " + l_name + " " + l_army + " " + l_color);
        }
    }

    /**
     * A utility method to read the contents from file.
     * @param l_fileName The complete path of the file.
     * @return A List of all the lines in the file.
     *
     * @throws IOException Exception while reading the map file.
     */
    public List<String> readMap(String l_fileName) throws IOException{
        List<String> l_lines;
        Path l_path = Paths.get(l_fileName);

        l_lines = Files.readAllLines(l_path, StandardCharsets.UTF_8);
        return l_lines;
    }
}