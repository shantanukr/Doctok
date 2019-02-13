package Doctor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuestionServelet
 */
@WebServlet("/QuesServelet")
public class QuesServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuesServelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getServletPath();
		if(action=="/QuesServelet")
		{
			request.setAttribute("res","hello");
			request.getRequestDispatcher("Question.jsp").forward(request,response);	
		}
		Question1 q=new Question1();
		String[] s=q.ask_question(1);
		System.out.println("before");
		String[][] referindex;
		int count=0;
		
		
		//System.out.println(s[0]);
		request.setAttribute("res",s[0]);
		request.getRequestDispatcher("Question.jsp").forward(request,response);
		//q.make_Symarray();
		String[][] prev_ref=q.get_table();
		for(int i=1;i<s.length;i++)
			{
				System.out.println();
				request.setAttribute("res1",(10+i-1)+ " "+ q.get_option(10+i-1));
				request.getRequestDispatcher("Question.jsp").forward(request,response);
			}
		   String answer=request.getParameter("answer");
			referindex=q.makearray(answer,prev_ref);
			 for(int i=0;i<referindex.length;i++)
			    	referindex[i][0]="-1";
			do{

					
					int index=q.next_question_index(referindex);
					String[] question_set=q.ask_question(index);
					System.out.println("after");
					request.setAttribute("res",question_set[0]);
					request.getRequestDispatcher("Question.jsp").forward(request,response);
				for(int i=1;i<question_set.length;i++)
					{	
						
					request.setAttribute("res1",(10+i-1)+ " "+ q.get_option(10+i-1));
					request.getRequestDispatcher("Question.jsp").forward(request,response);
					
					}
				referindex=q.makearray(answer, referindex);
				//prev_ref=null;
				
				for(int i=0;i<referindex.length;i++)
				{
					referindex[i][index-1]="-1";
				}
				}while(referindex.length!=0&&referindex.length!=1&&count<26);
				
	}

}

class Question1 {
  private User user;
  private int wt[];
  private String tablearray[][];
  private String symp[][];
  public String diagnosiscode;


  public Question1() {
     // this.user = user;
      //get the whole main grpsymptoom table
      this.diagnosiscode="";
      wt=new int[25];
      calculate_wt();

  }
  public String[][] get_table()
  {
      return this.tablearray;
  }
  public void getCurrentStatus() {

  }
  public String[][] makearray(String answer,String[][] prev_ref)
  {
      int valrc=Integer.parseInt(answer);
      int col=valrc/10;
      col=col-1;
      int value=valrc%10;
      int k=0;
      //System.out.print("hello"+col);
      for(int i=0;i<prev_ref.length;i++)
      {
          if(Integer.parseInt(prev_ref[i][col])==value)
          {
             k++;
          }
      }
      int m=0;
      String[][] arr_ret=new String[k][prev_ref[0].length];
      for(int i=0;i<prev_ref.length;i++)
      {
          if(Integer.parseInt(prev_ref[i][col])==value)
          {
              for(int j=0;j<prev_ref[0].length;j++)
              {
                  arr_ret[m][j]=prev_ref[i][j];
              }
              m++;
          }
      }
  return arr_ret;

  }
  public void calculate_wt() {
	  for(int i=0;i<wt.length;i++)
	  {
		  this.wt[i]=0;
	  }
      SQL sql = new SQL("root","", "exactcon", "doctok");
      this.tablearray = sql.retrieve_exact();
      for (int i = 0; i < tablearray[0].length; i++) {
          for (int j = 0; j < tablearray.length; j++) {
              String str = tablearray[j][i];
              if (str.equals("0"))
                  this.wt[i]=this.wt[i]+1;
              //System.out.println(str);
          }
      }
  }

  public void make_Symarray() {
      SQL sql = new SQL("root","", "exactcon", "doctok");
      this.symp= sql.retrieve_Sym();
 
  }


  public String[] ask_question(int index)
  {  SQL sql = new SQL("root", "", "questions", "doctok");
 
      String[] ques_set=sql.retrieve_ques_set(index);
      if(index>0)
      this.wt[index-1]=-1;
      //System.out.println(ques_set[0]);
      return ques_set;

  }
  public String get_conclusion(String diagnosiscode)
  {
return null;
  }
public int next_question_index(String[][] referindex )
{

 int index=0;
 int[] wt=new int[referindex[0].length];
  for (int i = 0; i < referindex[0].length; i++) {
      for (int j = 0; j < referindex.length; j++) {
          String str = referindex[j][i];
          if (str.equals("0"))
              wt[i]++;
      }
  }
  
  for(int i=0;i<wt.length;i++)
  {System.out.print(" "+wt[i]);
  
      if(wt[i]>wt[index])
          index=i;
  }
  System.out.println("");
  return index+1;


}
public void print_wt()
{
	for(int i=0;i<wt.length;i++)
{
		System.out.println(""+wt[i]);
		}
}
public String get_option(int index)
{ 	String s=null;
	 SQL sql = new SQL("root","", "question", "doctok");
	 s=sql.get_optstring(index);
	 return s;
}

public void updateString(String answer)
{
  this.diagnosiscode=this.diagnosiscode+answer;
}
}


