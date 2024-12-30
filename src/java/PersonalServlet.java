import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/PersonalServlet")
public class PersonalServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Conexión a la base de datos
            String jdbcUrl = "jdbc:mysql://localhost:3306/based";
            String dbUser = "root";
            String dbPassword = "";
            
            try {
                Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
                Statement stmt = conn.createStatement();
                String sql = "SELECT Id, Nombre, Dni, Fechanac, Correo, Edad, Sueldo FROM personal";
                ResultSet rs = stmt.executeQuery(sql);
                
                out.println("<html><body>");
                out.println("<h1>Lista de Personal</h1>");
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Nombre</th><th>DNI</th><th>Fecha de Nacimiento</th><th>Correo</th><th>Edad</th><th>Sueldo</th></tr>");
                
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("Id") + "</td>");
                    out.println("<td>" + rs.getString("Nombre") + "</td>");
                    out.println("<td>" + rs.getString("Dni") + "</td>");
                    out.println("<td>" + rs.getDate("Fechanac") + "</td>");
                    out.println("<td>" + rs.getString("Correo") + "</td>");
                    out.println("<td>" + rs.getInt("Edad") + "</td>");
                    out.println("<td>" + rs.getDouble("Sueldo") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</body></html>");
                
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<p>Error al conectar con la base de datos.</p>");
            }
        }
    }
}
