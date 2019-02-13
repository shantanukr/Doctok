package Doctor;


import java.sql.*;
public class SQL {
    private String user;
    private String password;
    private String table;
    private String db;
    PreparedStatement pstmt;
    Connection conn;
    Statement st;
    public SQL(String user ,String password,String table,String db)
    {  
        this.user=user;
        this.password=password;
        this.table=table;
        this.db=db;
        try {
                Class.forName("com.mysql.jdbc.Driver");
                String db_url="jdbc:mysql://localhost:3306/"+db;
                conn=DriverManager.getConnection(db_url,user,password);

            }
        catch(Exception e)
            {
                System.out.println(""+e);
            }
    }
    
    public String get_feverdetails(int diagnosis_id)
    {
    	String temp=null;
    	try {
    		
             	String tempqry="select prevSym_code from diagnosis where diag_id=?";
                 pstmt=conn.prepareStatement(tempqry);
                 pstmt.setInt(1, diagnosis_id);
                 ResultSet rs=pstmt.executeQuery();
                 rs.next();
   
                 temp=rs.getString("prevSym_code");
                 System.out.println(temp);

             }
             catch (Exception e)
             {
                 System.out.println(e);
             }
    	return temp;
    }
    public void insert_user(String name,String emailid,String phone,String dob,String password,String gender)
    {
    	 try
         {
             pstmt=conn.prepareStatement("insert into"+table+"(name,gender,dob,ph,email_id,password)values(?,?,?,?,?,?");///????????????????????????
             pstmt.setString(1,name);
             pstmt.setString(2,gender);
             pstmt.setString(3,dob);
             pstmt.setString(4,phone);
             pstmt.setString(5,emailid);
             pstmt.setString(6, password);
             
             pstmt.executeUpdate();
             

         }catch(Exception e)
         {
             System.out.println(e);
         }
    }
    
    
    public void view_user(String emailid)
    {
    	
            try
            {
            	String tempqry="select * from $tablename where email_id=$emailid";
            	 System.out.println("hello");
            	String qry=tempqry.replace("$tablename",this.table);
            	String qry1=qry.replace("$emailid",emailid);
            	 System.out.println("hello");
            	 System.out.println(qry);
            	Class.forName("com.mysql.jdbc.Driver");
                Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/doctok","root","");
                Statement stm=con.createStatement();
                
              
                ResultSet rs=stm.executeQuery(qry1);
                rs.next();
  
                String temp=rs.getString("name");
                System.out.println(temp);

            }
            catch (Exception e)
            {
                System.out.println(e);
            }
    }
    
    public void delete_user(String emailid)
    {
    	try
        {
        	String tempqry="delete $tablename where ";
        	String qry=tempqry.replace("$tablename",table);
            pstmt=conn.prepareStatement(qry);
            
            pstmt.setString(1,emailid);

            ResultSet rs=pstmt.executeQuery();
            rs.next();
            String temp=rs.getString("name");
            System.out.println(temp);

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void update_user(String emailid)
    {
    	try
        {
        	String tempqry="select * from $tablename where symp_name=?";
        	String qry=tempqry.replace("$tablename",table);
            pstmt=conn.prepareStatement(qry);
            
            pstmt.setString(1,emailid);

            ResultSet rs=pstmt.executeQuery();
            rs.next();
            String temp=rs.getString("name");
            System.out.println(temp);

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public void generate_dataset()
    {
    	
    }
    public void update_symptom()
    {

    }
    public void update_grpsymptom()
    {

    }
    public void delete_symptom()
    {

    }
    public void grp_symptom()
    {

    }
    public void generate_grpsymptom()
    {
        //frm dataset generated
    }
    public String[] retrieve_ques_set(int index) {
       String[] str = null;
       int count = 0;
        try {

        		
            pstmt = conn.prepareStatement("select * from questions where id=?");
           // System.out.println("Hi");
            pstmt.setInt(1,index);
            ResultSet rs=pstmt.executeQuery();
            if(rs.last())
            	count=rs.getRow();
            rs.beforeFirst();
            rs.next();
            str=new String[count+1];
            str[0]=rs.getString("qname");
            str[1]=rs.getString("option");
            int i=2;
            while(rs.next())
            {
                str[i++]=rs.getString("option");
            }
            System.out.println("hi");
        }
        catch(Exception e)
        {
         System.out.println(""+e);
        }
        return str;
    }
    public String get_optstring(int index)
    {
    	String s=null;
    	try {
    		pstmt=conn.prepareStatement("Select option from questions where option_code=?");
    		pstmt.setInt(1,index);
    		ResultSet rs=pstmt.executeQuery();
    		rs.next();
    		s=rs.getString("option");
    	}catch(Exception e)
    	{
    		System.out.println(""+e);
    	}
    	
    	return s;
    }
    public String[][] retrieve_exact()//retrieve main table in tablearray
    {
    	String[] str={ "con_id","temp","fever_type", "rashes", "persistent_tachicardia", "running_nose", "chills","vomiting", "urine", "ache", "SoreEyes", "Appetite_loss", "neck_stiffness", "Loose_Motion", "Wt_Loss", "ear_pain", "sneeze", "abdominal", "Breathlessness", "cough", "focal_tenderness", "pain", "Blurred_Vision", "Erruption", "Hoarseness_Voice", "Swelled_Foot"};
      String[][] tablearray=new String[25][str.length];
     try {
        st=conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from exactcon");
       int i=0;
        while(rs.next())
       {
           for(int j=0;j<str.length;j++)
           {
               tablearray[i][j]=rs.getString(str[j]);
           }
           i++;
       }
        //System.out.println("hello");
        
        }
    catch(Exception e)
    {
    System.out.println("hELL YAY!"+e);
    }
        return tablearray;
    }
    

public String[][] retrieve_Sym()
{
	    	String[] str={"symp_id","symp_name"};
	      String[][] tablearray=new String[77][str.length];
	     try {
	        st=conn.createStatement();
	        ResultSet rs = st.executeQuery("Select * from symptom");
	       int i=0;
	       System.out.println("Hello");
	        while(rs.next())
	       {
	           for(int j=0;j<str.length;j++)
	           {
	               tablearray[i][j]=rs.getString(str[j]);
	           }
	           i++;
	       }
	        for(int k=0;i<tablearray.length;i++)
	        {
	        	for(int j=0;j<tablearray[0].length;j++)
	        		System.out.println(tablearray[k][j]);
	        }
	        //System.out.println("hello");
	        }
	    catch(Exception e)
	    {
	    System.out.println("hELL YAY!"+e);
	    }
	        return tablearray;
}

		public String get_conclusion(String conid)
		{
			String con=null;
			try {
				System.out.println("Conclusion ID"+conid);
				pstmt=conn.prepareStatement("select * from conclusion where conclusion_id=?");
				pstmt.setString(1,conid);
				
				ResultSet rs=pstmt.executeQuery();
				while(rs.next())
				{
					con=rs.getString("conclusion_name");
					System.out.println(con);
				}
				
				
			}catch(Exception e)
			{
				System.out.println(e);
			}
			
			return con;
		}
		public boolean check_feverdata()
		{
			SQL sql=new SQL("root","","fever","doctok");
			int code[] = new int[100];
			int count=0;
			int i=0;
			try {
		 st=conn.createStatement();  
			ResultSet rs=st.executeQuery("select distinct type from fever");
			
			while(rs.next())  
			{
			    code[i++] = rs.getInt(1);
			 //   System.out.println(code[i-1]);  
			    	count++;
			}
			if(count==0)
				return false;
		

			//con.close();  
			i=0;
			Statement stmt1=conn.createStatement();  
			ResultSet rs1=stmt1.executeQuery("select pid from diagnosis");  
			while(rs1.next())  
			{
			    int pid=rs1.getInt(1);
			    System.out.println("Pid:"+rs1.getInt(1));  
			      PreparedStatement Stmt2 = conn.prepareStatement("update diagnosis set prevSym_code = ? where pid=?");
			      Stmt2.setInt(1, code[i++]);
			      Stmt2.setInt(2, pid);
			      Stmt2.executeUpdate();
			     // con.close();
			    //System.out.println(rs.getInt(1));  

			}
			
			}catch(Exception e){ System.out.println(e);}  

			  
			  
			return true;
		}
		public static void main(String args[])
		{
			SQL sql=new SQL("root","","diagnosis","doctok");
			sql.get_feverdetails(1);
			System.out.println(sql.check_feverdata());
		}
		
	}


