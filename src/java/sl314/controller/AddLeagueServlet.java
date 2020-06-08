package sl314.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

import sl314.model.LeagueService;
import sl314.model.League;
import java.util.List;
import java.util.LinkedList;


public class AddLeagueServlet extends HttpServlet {
    
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
      
        List errorMsgs = new LinkedList();
        
        request.setAttribute("errorMsgs", errorMsgs);
        
        try {
            
            
            String yearStr = request.getParameter("year").trim();
            String season = request.getParameter("season").trim();
            String title = request.getParameter("title").trim();
            
            
            int year = -1;
            try {
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException nfe) {
                errorMsgs.add("The 'year' field must be a positive integer.");
            }
            
          
            if ( (year != -1) && ((year < 2020) || (year > 2025)) ) {
                errorMsgs.add("The 'year' field must within 2020 to 2025.");
            }
            if ( season.equals("UNKNOWN") ) {
                errorMsgs.add("Please select a league season.");
            }
            if ( title.length() == 0 ) {
                errorMsgs.add("Please enter the title of the league.");
            }
            
            
            if ( ! errorMsgs.isEmpty() ) {
                RequestDispatcher view
                        = request.getRequestDispatcher("add_league.view");
                view.forward(request, response);
                return;
            }
            
         
            String dataDirectory = (String)getServletContext().getAttribute("sl314.model.dataDirectory");
            
            LeagueService leagueSvc = new LeagueService(dataDirectory);
            League league = leagueSvc.createLeague(year, season, title);
           
            request.setAttribute("league", league);
            
            
            RequestDispatcher view
                    = request.getRequestDispatcher("success.view");
            view.forward(request, response);
            return;
            
            
        } catch (Exception e) {
            errorMsgs.add(e.getMessage());
            RequestDispatcher view
                    = request.getRequestDispatcher("add_league.view");
            view.forward(request, response);
            
            
            e.printStackTrace(System.err);
            
        } 
        
    } 
    
} 
