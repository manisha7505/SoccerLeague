package sl314.model;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


public class PlayerService {
    private String dataDirectory;
    
    public PlayerService(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }
    
    /*
     * This method finds the specified Player object from the
     * complete set of players.
     */
    public Player getPlayer(String name) {
        
        Player player = readPlayer(name);
        
        if ( player == null ) {
            player = new Player(name);
        }
        
        return player;
    }
    
    /*
     * This method adds a new Player object.
     */
    public void save(Player player) {
        
        storePlayer(player);
    }
    
    /*
     * This private method retrieves a single player from the data file.
     */
    private Player readPlayer(String search_name) {
        File playerFile = new File(dataDirectory, "players.txt");
        BufferedReader playerReader = null;
        Player player = null;
        if ( ! playerFile.exists() ) {
            return null;
        }
        try {
            playerReader = new BufferedReader(new FileReader(playerFile));
            String record;
            while ( (record = playerReader.readLine()) != null ) {
                String[] fields = record.split("\t");
                String name = fields[0];
                if ( ! search_name.equals(name) ) {
                    continue;
                }
                String address = fields[1];
                String city = fields[2];
                String province = fields[3];
                String postalCode = fields[4];
                player = new Player(name, address, city, province, postalCode);
                break;
                } 
            
        } catch (Exception e) {
            System.err.println(e);
            
            
        } finally {
            if ( playerReader != null ) {
                try { playerReader.close(); } catch (IOException e) { System.err.println(e); }
            }
        }
        
        return player;
    }
    
    /*
     * This private method stores a single player to the data file.
     */
    private void storePlayer(Player player) {
        String playerFile = dataDirectory + "players.txt";
        PrintWriter playerWriter = null;
        
        try {
            
            playerWriter = new PrintWriter(new FileWriter(playerFile, true));
            
            playerWriter.print(player.name);
            playerWriter.print('\t');
            playerWriter.print(player.address);
            playerWriter.print('\t');
            playerWriter.print(player.city);
            playerWriter.print('\t');
            playerWriter.print(player.province);
            playerWriter.print('\t');
            playerWriter.print(player.postalCode);
            playerWriter.println();
            
        } catch (Exception e) {
            System.err.println(e);
            
            
        } finally {
            if ( playerWriter != null ) {
                try { playerWriter.close(); } catch (Exception e) { System.err.println(e); }
            }
        }
    }
}
