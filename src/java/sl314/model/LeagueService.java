package sl314.model;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collections;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/*
 * This object performs a variety of league services, such as looking
 * up league objects and creating new ones.
 */
public class LeagueService {
    
    
    private static final List LEAGUES_CACHE = new LinkedList();
    private String dataDirectory;
    
 

    public LeagueService(String dataDirectory) {
         this.dataDirectory = dataDirectory;
        
        
        synchronized ( LEAGUES_CACHE ) {
            if ( LEAGUES_CACHE.isEmpty() ) {
                cacheLeagues();
            }
        }
    }
    
    /*
     * This method returns a complete set of leagues.
     */
    public List getAllLeagues() {
        
        return Collections.unmodifiableList(LEAGUES_CACHE);
    }
    
    /*
     * This method finds the specified League object from the
     * complete set of leagues.
     */
    public League getLeague(int year, String season)
    throws ObjectNotFoundException {
        
       
        Iterator set = LEAGUES_CACHE.iterator();
        while ( set.hasNext() ) {
            League l = (League) set.next();
            if ( season.equals(l.getSeason()) && (year == l.getYear()) ) {
                return l;
            }
        }
        
      
        throw new ObjectNotFoundException();
    }
    
    /*
     * This method adds a new League object.
     */
    public League createLeague(int year, String season, String title) {
        
        
        int nextID = LEAGUES_CACHE.size() + 1;
        
       
        League league = new League(nextID, year, season, title);
        
        
        storeLeague(league);
        
        
        LEAGUES_CACHE.add(league);
        
        return league;
    }
        
    /*
     * This method populates the cache of leagues from the data file.
     */
    private void cacheLeagues() {
        String leagueFile = dataDirectory + "leagues.txt";
        BufferedReader leagueReader = null;
        
        try {
            leagueReader = new BufferedReader(new FileReader(leagueFile));
            String record;
            
           
            while ( (record = leagueReader.readLine()) != null ) {
                String[] fields = record.split("\t");
                
                
                int objectID = Integer.parseInt(fields[0]);
                int year = Integer.parseInt(fields[1]);
                String season = fields[2];
                String title = fields[3];
                
                
                League l = new League(objectID, year, season, title);
                LEAGUES_CACHE.add(l);
                
            } 
            
        } catch (Exception e) {
            System.err.println(e);
            
       
        } finally {
            if ( leagueReader != null ) {
                try { leagueReader.close(); } catch (IOException e) { System.err.println(e); }
            }
        }
    } 
    
    /*
     * This private method stores a single league to the data file.
     */
    private void storeLeague(League league) {
        String leagueFile = dataDirectory + "leagues.txt";
        PrintWriter leagueWriter = null;
        
        try {
            
            leagueWriter = new PrintWriter(new FileWriter(leagueFile, true));
            
            leagueWriter.print(league.objectID);
            leagueWriter.print('\t');
            leagueWriter.print(league.year);
            leagueWriter.print('\t');
            leagueWriter.print(league.season);
            leagueWriter.print('\t');
            leagueWriter.print(league.title);
            leagueWriter.println();
            
        } catch (Exception e) {
            System.err.println(e);
            
            
        } finally {
            if ( leagueWriter != null ) {
                try { leagueWriter.close(); } catch (Exception e) { System.err.println(e); }
            }
        }
    } 
    
} 
