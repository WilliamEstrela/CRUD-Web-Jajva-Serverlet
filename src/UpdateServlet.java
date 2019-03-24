import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

  public class UpdateServlet extends HttpServlet {

    private String dbUrl = "jdbc:postgresql://localhost:5432/vendas";

      /**Process the HTTP Get request*/

    public void doGet(HttpServletRequest request, HttpServletResponse
      response) throws ServletException, IOException {

      sendPageHeader(response);
      sendUpdateForm(request, response);
      sendPageFooter(response);
    }

     /**Process the HTTP Post request*/

    public void doPost(HttpServletRequest request, HttpServletResponse
      response) throws ServletException, IOException {

      sendPageHeader(response);
      updateRecord(request, response);
      sendPageFooter(response);
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
      out.println("<TITLE>Atualizando Registro</TITLE>");
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

    private void sendUpdateForm(HttpServletRequest request,
      HttpServletResponse response) throws IOException {

      String id = request.getParameter("id");
      PrintWriter out = response.getWriter();
      out.println("<BR><H2>Formulário de Atualização</H2>");
      out.println("<BR>Por favor, altere o Email.");
      out.println("<BR>");
      try {
        String sql = "SELECT nome, email " +
          " FROM exemplo1" +
          " WHERE id=" + id;

        Connection con = DriverManager.getConnection(dbUrl, "postgres", "");
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(sql);
        if (rs.next()) {
          String nome = rs.getString(1);
          String email = rs.getString(2);
          out.println("<BR><FORM METHOD=POST>");
          out.print("<INPUT TYPE=HIDDEN Name=id VALUE=" + id + ">");
          out.println("<TABLE>");
          out.println("<TR>");
          out.println("<TD>Nome</TD>");
          out.print("<TD>" + nome + "</TD>");
          out.println("</TR>");
          out.println("<TR>");
          out.println("<TD>Email</TD>");
          out.print("<TD><INPUT TYPE=TEXT Name=email");
          out.print(" VALUE=\"" + email + "\"");
          out.println("></TD>");
          out.println("</TR>");
          out.println("<TR>");
          out.println("<TD><INPUT TYPE=RESET></TD>");
          out.println("<TD><INPUT TYPE=SUBMIT></TD>");
          out.println("</TR>");
          out.println("</TABLE>");
          out.println("</FORM>");
        }
 
        s.close();
        con.close();
      }
      catch (SQLException e) {
        out.println(e.toString());
      }
      catch (Exception e) {
        out.println(e.toString());
      }
    }

    void updateRecord(HttpServletRequest request, HttpServletResponse
      response) throws IOException {

      String id = request.getParameter("id");
      String email = request.getParameter("email");
      PrintWriter out = response.getWriter();
      try {
        String sql = "UPDATE exemplo1" +
          " SET email='" + email +
          "' WHERE id=" + id;
        Connection con = DriverManager.getConnection(dbUrl, "postgres", "");
        Statement s = con.createStatement();
        int i = s.executeUpdate(sql);
        if (i == 1)
          out.println("Registro Atualizado");
        else
          out.println("Erro ao Atualizar o Registro");

        s.close();
        con.close();
      }
      catch (SQLException e) {
        out.println(e.toString());
      }
      catch (Exception e) {
        out.println(e.toString());
      }

      out.println("<A HREF=search>Retornar</A> Página de Busca");
    }
  }