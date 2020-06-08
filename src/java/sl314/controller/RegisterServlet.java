package sl314.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

import sl314.model.RegisterService;
import sl314.model.League;
import sl314.model.Player;
import sl314.model.ObjectNotFoundException;
import java.util.List;
import java.util.LinkedList;


public class RegisterServlet extends HttpServlet {
    
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
    
        List errorMsgs = new LinkedList();
        
        request.setAttribute("errorMsgs", errorMsgs);
        
        try {
            
        
            String season = request.getParameter("season").trim();
            String yearStr = request.getParameter("year").trim();
            String name = request.getParameter("name").trim();
            String address = request.getParameter("address").trim();
            String city = request.getParameter("city").trim();
            String province = request.getParameter("province").trim();
            String postalCode = request.getParameter("postalCode").trim();
            String division = request.getParameter("division").trim();
            
            
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
            
        
            if ( name.length() == 0 ) {
                errorMsgs.add("You must enter your full name.");
            }
            if (   (address.length() == 0)  || (city.length() == 0)
            || (province.length() == 0) || (postalCode.length() == 0) ) {
                errorMsgs.add("You must enter your full address.");
            }
            
            
            if ( division.equals("UNKNOWN") ) {
                errorMsgs.add("You must select a division.");
            }
            
           
            if ( ! errorMsgs.isEmpty() ) {
                RequestDispatcher view
                        = request.getRequestDispatcher("form.view");
                view.forward(request, response);
                return;
            }
            
            
            String dataDirectory = (String)getServletContext().getAttribute("sl314.model.dataDirectory");
            RegisterService registerSvc = new RegisterService(dataDirectory);
            
            League league = registerSvc.getLeague(year, season);
            
            Player player = registerSvc.getPlayer(name);
            player.setAddress(address);
            player.setCity(city);
            player.setProvince(province);
            player.setPostalCode(postalCode);
         
            registerSvc.register(league, player, division);
            
            
            request.setAttribute("league", league);
            request.setAttribute("player", player);
            
        
            RequestDispatcher view
                    = request.getRequestDispatcher("thank_you.view");
            view.forward(request, response);
            return;
            
            
        } catch (ObjectNotFoundException onfe) {
            errorMsgs.add("The league you selected does not yet exist."
                    + " Please select another.");
            RequestDispatcher view
                    = request.getRequestDispatcher("form.view");
            view.forward(request, response);
            
            
        } catch (RuntimeException e) {
            errorMsgs.add(e.getMessage());
            RequestDispatcher view
                    = request.getRequestDispatcher("form.view");
            view.forward(request, response);
            
            
            e.printStackTrace(System.err);
            
        } 
        
    } 
    
}
