package sl314.view;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Iterator;

public class RegisterFormServlet extends HttpServlet {
    private static final String DEFAULT_SEASONS = "Spring+1,Summer+2,Fall+3,Winter+4";
    private String[] SEASONS;
    private static final String DEFAULT_DIVISIONS
            = "Amateur+1,Semi-Pro+2,Professional+3";
    private String[] DIVISIONS;
    public void init() {
        String seasons_list = getInitParameter("seasons-list");
        if ( seasons_list != null ) {
            SEASONS = seasons_list.split(",");
        } else {
            SEASONS = DEFAULT_SEASONS.split(",");
        }
        String divisions_list = getInitParameter("divisions-list");
        if ( divisions_list != null ) {
            DIVISIONS = divisions_list.split(",");
        } else {
            DIVISIONS = DEFAULT_DIVISIONS.split(",");
        }
    }
    
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        generateView(request, response);
    }
    
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        generateView(request, response);
    }
    
    public void generateView(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        
  
        String pageTitle = "Soccer League: Registration";
        
        
        List errorMsgs = (List) request.getAttribute("errorMsgs");
        
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
       
        out.println("<html>");
        out.println("<head>");
        out.println("  <title>" + pageTitle + "</title>");
        out.println("</head>");
        out.println("<body bgcolor='white'>");
        
 
        out.println("<!-- Page Heading -->");
        out.println("<table border='1' cellpadding='5' cellspacing='0' width='400'>");
        out.println("<tr bgcolor='#CCCCFF' align='center' valign='center' height='20'>");
        out.println("  <td><h3>" + pageTitle + "</h3></td>");
        out.println("</tr>");
        out.println("</table>");
        
        
        if ( errorMsgs != null ) {
            out.println("<p>");
            out.println("<font color='red'>Please correct the following errors:");
            out.println("<ul>");
            Iterator items = errorMsgs.iterator();
            while ( items.hasNext() ) {
                String message = (String) items.next();
                out.println("  <li>" + message + "</li>");
            }
            out.println("</ul>");
            out.println("</font>");
            out.println("</p>");
        }
        
        
        out.println("<p>");
        out.println("This form allows you to register for a soccer league.");
        out.println("</p>");
        out.println("<form action='form.do' method='POST'>");
        
        
        out.println("<h4>Select a League</h4>");
        String year = request.getParameter("year");
        if ( year == null ) {
            year = "";
        }
        out.println("Year: <input type='text' name='year' value='"
                + year + "' /> <br/><br/>");
        out.println("Season: <select name='season'>");
        String season = request.getParameter("season");
        if ( (season == null) || season.equals("UNKNOWN") ) {
            out.println("          <option value='UNKNOWN'>select...</option>");
        }
        for ( int i = 0; i < SEASONS.length; i++ ) {
            out.print("          <option value='" + SEASONS[i] + "'");
            if ( SEASONS[i].equals(season) ) {
                out.print(" selected");
            }
            out.println(">" + SEASONS[i] + "</option>");
        }
        out.println("        </select> <br/><br/>");
        
        
        out.println("<h4>Enter Player Information</h4>");
        String name = request.getParameter("name");
        if ( name == null ) {
            name = "";
        }
        out.println("Name: <input type='text' name='name' value='"
                + name + "' /> <br/><br/>");
   
        String address = request.getParameter("address");
        if ( address == null ) {
            address = "";
        }
        out.println("Address: <input type='text' name='address' value='"
                + address + "' /> <br/><br/>");
        
        String city = request.getParameter("city");
        if ( city == null ) {
            city = "";
        }
        out.println("City: <input type='text' name='city' value='"
                + city + "' /> <br/><br/>");
       
        String province = request.getParameter("province");
        if ( province == null ) {
            province = "";
        }
        out.println("Province: <input type='text' name='province' value='"
                + province + "' /> <br/><br/>");
        
        String postalCode = request.getParameter("postalCode");
        if ( postalCode == null ) {
            postalCode = "";
        }
        out.println("Postal code: <input type='text' name='postalCode' value='"
                + postalCode + "' /> <br/><br/>");
        
       
        // "Select a division" field
        
        out.println("<h4>Select a Division</h4>");
      
        out.println("Division: <select name='division'>");
        String division = request.getParameter("division");
        if ( (division == null) || division.equals("UNKNOWN") ) {
            out.println("          <option value='UNKNOWN'>select...</option>");
        }
        for ( int i = 0; i < DIVISIONS.length; i++ ) {
            out.print("          <option value='" + DIVISIONS[i] + "'");
            if ( DIVISIONS[i].equals(division) ) {
                out.print(" selected");
            }
            out.println(">" + DIVISIONS[i] + "</option>");
        }
        out.println("        </select> <br/><br/>");
        
        out.println("<input type='Submit' value='Register' />");
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");
    }
}
