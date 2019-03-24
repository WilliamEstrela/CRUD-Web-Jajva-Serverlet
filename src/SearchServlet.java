import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;



public class SearchServlet extends HttpServlet {

  private String keyword = "";

  public void init() {
    try {
      Class.forName("org.postgresql.Driver");
      System.out.println("JDBC driver carregado.");
    }
    catch (ClassNotFoundException e) {
      System.out.println(e.toString());
    }
  }

  /**Process the HTTP Get request*/

  public void doGet(HttpServletRequest request, HttpServletResponse
response) throws
    ServletException, IOException {
      sendPageHeader(response);
      sendSearchForm(response);
      sendPageFooter(response);
  }

  /**Process the HTTP Post request*/

  public void doPost(HttpServletRequest request, HttpServletResponse
response) throws
    ServletException, IOException {
      keyword = request.getParameter("keyword");
      sendPageHeader(response);
      sendSearchForm(response);
      sendSearchResult(response);
      sendPageFooter(response);
    }

  void sendSearchResult(HttpServletResponse response)
    throws IOException {
      PrintWriter out = response.getWriter();
      try {
        Connection con = 
          DriverManager.getConnection("jdbc:postgresql://localhost:5432/vendas", "postgres", "");
        
        Statement s = con.createStatement();
        out.println("<TABLE>");
        out.println("<TR>");
        out.println("<TH>Nome</TH>");
        out.println("<TH>Email</TH>");
        out.println("<TH></TH>");
        out.println("<TH></TH>");
        out.println("</TR>");
        String sql =
          "SELECT Id, Nome, Email" +
          " FROM exemplo1" +
          " WHERE Nome LIKE '%" + keyword + "%'" +
          " OR Email LIKE '%" + keyword + "%'";
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
          String id = rs.getString(1);
          out.println("<TR>");
          out.println("<TD>" + rs.getString(2) + "</TD>");
          out.println("<TD>     " + rs.getString(3) + "</TD>");
          out.println("<TD><A HREF=delete?id=" + id +
            ">Apagar</A></TD>");
          out.println("<TD><A HREF=update?id=" + id +
            ">Atualizar</A></TD>");
          out.println("</TR>");
        }  
        s.close();
        con.close();
      }
      catch (SQLException e) {
      }
      catch (Exception e) {
      }
      out.println("</TABLE>");
  }


  /**
  * Send the HTML page header, including the title 
  * and the <BODY> tag
  */

  private void sendPageHeader(HttpServletResponse response)
    throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<HTML>");
    out.println("<HEAD>");
    out.println("<TITLE>Apresentando registros selecionados</TITLE>");
    out.println("</HEAD>");
    out.println("<BODY>");
    out.println("<CENTER>");
  }

  /**
  * Send the HTML page footer, i.e. the </BODY>
  * and the </HTML>
  */

  private void sendPageFooter(HttpServletResponse response)
    throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    out.println("</CENTER>");
    out.println("</BODY>");
    out.println("</HTML>");
  }

  /**Send the form where the user can type in
  * the details for a new user
  */

  private void sendSearchForm(HttpServletResponse response)
    throws IOException {
    PrintWriter out = response.getWriter();
    out.println("<BR><H2>Formul�rio de Busca</H2>");
    out.println("<BR>Por favor, forne�a Nome ou Email(ou parte de qualquer um).");
    out.println("<BR>** chave de busca vazia busca todos os registros **");
    out.println("<BR>");
    out.println("<BR><FORM METHOD=POST>");
    out.print("Chave de Busca: <INPUT TYPE=TEXT Name=keyword");
    out.print(" VALUE=\"" + keyword + "\"");
    out.println(">");
    out.println("<INPUT TYPE=SUBMIT>");
    out.println("</FORM>");
    out.println("<BR>");
    out.println("<BR>");
  }
}
