package Doctor;




import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignupServelet
 */
@WebServlet("/SignupServelet")
public class SignupServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<String> al=new ArrayList<>();
		al.add("a");
		al.add("b");
		al.add("C");
		
		for(int i=0;i<al.size();i++)
		{
			String s=al.get(i);
			System.out.println(s);
			request.setAttribute("item",s);
			getServletConfig().getServletContext().getRequestDispatcher("/Signup.jsp").forward(request, response);
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Signup obj=new Signup();
		
		obj.name=request.getParameter("fname");
		obj.emailid=request.getParameter("email");
		obj.dob=request.getParameter("dof");
		obj.phone=request.getParameter("phone");
		obj.password=request.getParameter("password");
		obj.cPassword=request.getParameter("cpassword");
		obj.gender=request.getParameter("gender");
		System.out.println(obj.password);
		System.out.println(obj.cPassword);
		if(!(obj.password.equals(obj.cPassword)))
		{
			request.setAttribute("res","not same");
			request.getRequestDispatcher("Signup.jsp").forward(request,response);
		}
		
		obj.getDetails();
		
		boolean exist=obj.check_exist(request.getParameter("email"));
		if(exist==true)
		{
			request.setAttribute("exist","user already exist");
			request.getRequestDispatcher("Signup.jsp").forward(request,response);
		}
		
		
		
	}

}

