package sl314.view;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import sl314.model.LeagueService;
import sl314.model.League;
import java.util.List;
import java.util.Iterator;

public class ListLeagueServlet extends HttpServlet {
    
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        
       
        String pageTitle = "Soccer League: List Leagues";
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
        String dataDirectory = (String)getServletContext().getAttribute("sl314.model.dataDirectory");
        LeagueService leagueSvc = new LeagueService(dataDirectory);
        List leagueList = leagueSvc.getAllLeagues();
        out.println("<p>");
        out.println("The set of soccer leagues are:");
        out.println("</p>");
        
        out.println("<ul>");
        Iterator items = leagueList.iterator();
        while ( items.hasNext() ) {
            League league = (League) items.next();
            out.println("  <li>" + league.getTitle() + "</li>");
        }
        out.println("</ul>");
        out.println("End of list...");
        
        out.println("</body>");
        out.println("</html>");
    }
}
