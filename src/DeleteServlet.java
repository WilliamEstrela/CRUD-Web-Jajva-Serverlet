import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

  public class DeleteServlet extends HttpServlet {
    /**Process the HTTP Get request*/
    public void doGet(HttpServletRequest request, HttpServletResponse
      response) throws
      ServletException, IOException {

      int recordAffected = 0;
      try {
        String id = request.getParameter("id");
        String sql = "DELETE FROM exemplo1 WHERE Id=" + id;
        Connection con =
          DriverManager.getConnection("jdbc:postgresql://localhost:5432/vendas", "postgres", "");
        Statement s = con.createStatement();
        recordAffected = s.executeUpdate(sql);
        s.close();
        con.close();
      }
      catch (SQLException e) {
      }
      catch (Exception e) {
      }

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println("<HTML>");
      out.println("<HEAD>");
      out.println("<TITLE>Apagando Um Registro</TITLE>");
      out.println("</HEAD>");
      out.println("<BODY>");
      out.println("<CENTER>");
      if (recordAffected == 1)
        out.println("<P>Registro apagado.</P>");
      else
        out.println("<P>Erro ao apagar o registro.</P>");

      out.println("<A HREF=search>Retornar</A> página de busca");
    }
  }