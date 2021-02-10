package controller;

import java.io.IOException;
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
        int l_continentOrder = 1;
        while(checkSameBlock(p_idx, p_lines)){
            String[] l_segments = p_lines.get(p_idx).split(" ");

            String l_continentName = l_segments[0];
            int l_continentArmy = Integer.parseInt(l_segments[1]);
            String l_color = l_segments[2];
            d_gameEngine.getMapState().getListOfContinents().add(new ContinentModel(l_continentOrder, l_continentName, l_color, l_continentArmy));

            p_idx++;
            l_continentOrder++;
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

            int l_countryOrder = Integer.parseInt(l_segments[0]);
            String l_countryName = l_segments[1];
            int l_continentOrder = Integer.parseInt(l_segments[2]);
            int l_x_coordinate = Integer.parseInt(l_segments[3]);
            int l_y_coordinate = Integer.parseInt(l_segments[4]);
            CoordinateModel l_coordinate = new CoordinateModel(l_x_coordinate, l_y_coordinate);

            CountryModel l_currentCountry = new CountryModel(l_countryOrder, l_countryName, l_continentOrder, l_coordinate);

            d_gameEngine.getMapState().getListOfCountries().add(l_currentCountry);

            // Add the country to the continent as well.
            for (ContinentModel continent : d_gameEngine.getMapState().getListOfContinents()){
                if (continent.getOrder() == l_continentOrder){
                    continent.getCountries().add(l_currentCountry.getOrder());
                }
            }
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

            int l_countryOrder = l_intSegments[0];
            CountryModel l_currentCountry = getCountryByOrder(l_countryOrder);

            for (int neighbour: l_neighbours){
                // creating only one way connections at the moment.
                // Assuming, 1-way connections are possible.
                l_graph[l_countryOrder - 1][neighbour - 1] = 1;

                // Add the neighbour country in the list of neighbours.
                CountryModel l_neighbourCountry = getCountryByOrder(neighbour);
                l_currentCountry.getNeighbourCountries().add(l_neighbourCountry);
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
     * The test is based on the fact that the lines in a block are
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
     * Helper method for retrieving the CountryModel object from country order.
     * @param p_order Order of the country according to the map.
     * @return      Country.
     */
    public CountryModel getCountryByOrder(int p_order){
        for(CountryModel country : d_gameEngine.getMapState().getListOfCountries()){
            if (country.getOrder() == p_order){
                return country;
            }
        }
        return null;
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