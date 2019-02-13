package Doctor;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
//import java.util.Arrays;

public class Question 
{
  private User user;
  private int wt[];
  private String tablearray[][];
  private String symp[][];
  public String diagnosiscode;


  public Question() {
     // this.user = user;
      //get the whole main grpsymptoom table
      this.diagnosiscode="";
      wt=new int[27];
      calculate_wt();

  }
  
  //get table
  
  
  public String[][] get_table()
  {
      return this.tablearray;
  }
  public void getCurrentStatus() {

  }
  
  //make the array
  
  
  public String[][] makearray(String answer,String[][] prev_ref)
  {
      int valrc=Integer.parseInt(answer);
      int col=valrc/10;
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
  
  
  //calculate weight array
  
  public void calculate_wt() {
	  for(int i=0;i<wt.length;i++)
	  {
		  this.wt[i]=0;
	  }
      SQL sql = new SQL("root","", "exactcon", "doctok");
      this.tablearray = sql.retrieve_exact();
      for (int i = 1; i < tablearray[0].length; i++) {
          for (int j = 0; j < tablearray.length; j++) {
              String str = tablearray[j][i];
              if (str.equals("0"))
                  this.wt[i]=this.wt[i]+1;
              //System.out.println(str);
          }
      }
  }
  
  
  //make the symptom array

  public void make_Symarray() {
      SQL sql = new SQL("root","", "exactcon", "doctok");
      this.symp= sql.retrieve_Sym();
 
  }


  //ask the question
  
  
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
  
  
  //next question index
  
public int next_question_index(String[][] referindex )
{

 int index=0;
 int[] wt=new int[referindex[0].length];
 int j=0;
 for (int i = 0; i < referindex[0].length; i++) {
     while(j<referindex.length&&referindex[j][i].equals("0"))
     {
    	 j++;
    	 //System.out.println("while write");
    	 
    	 }
   
     if(j==referindex.length)
     {
    	 for(int k=0;k<referindex.length;k++)
    	 {
    		 referindex[k][i]="-1";
    	 }
     }
    // System.out.println("all right  "+j);
     j=0;
     
 }
 System.out.println("referindex  in  next_question for weight calculation");
	for(int i=0;i<referindex.length;i++)
		{
			for(int p=0;p<referindex[0].length;p++)
				{
					System.out.print("  "+referindex[i][p]);
				}
			System.out.println("");
		}
 //System.out.println("while and all right");
  System.out.println("Referindex.length "+referindex.length);
  for (int i = 0; i < referindex[0].length; i++) {
      for ( j = 0; j < referindex.length; j++) {
          String str = referindex[j][i];
          
          if (str.equals("0"))
          { //System.out.println("Weight hai!!");
        	  wt[i]++;
          }
          }
      }
  
  System.out.println("---------------------Weight Mtrix---------------------------");
  for(int i=0;i<wt.length;i++)
  {System.out.print(" "+wt[i]);
  
      if(wt[i]>wt[index])
          index=i;
  }
  System.out.println("");
  //return index+1;/////as con_id is added at index 0
  return index;

}

//print_wt


public void print_wt()
{System.out.println("Weight matrix:");
	for(int i=0;i<wt.length;i++)
{
		System.out.println("  "+wt[i]);
		}
}


//get the option

public String get_option(int index)
{ 	String s=null;
	 SQL sql = new SQL("root","", "question", "doctok");
	 s=sql.get_optstring(index);
	 return s;
}


//neural


public void nueral(String[] ansarr)
{
	

	String answer;
	Question q=new Question();
	int ansindex=0;
	int index1=1;
	while(index1<26)
	{
		if(ansarr[index1++]=="-2")
		{   System.out.println("Referindex not required!! ");
			String[] question_set=q.ask_question(index1);
			System.out.println("Neural  Index:"+index1);
			System.out.println("--------Neural Question:---------- "+question_set[0]);
			for(int i=1;i<question_set.length;i++)
				{		
					System.out.println((10*(index1)+i-1)+" "+q.get_option(10*(index1)+i-1));
				}
			System.out.println("Enter option");
			Scanner sc=new Scanner(System.in);
			answer=sc.next();
			ansindex=Integer.parseInt(answer);
			ansarr[ansindex/10]=Integer.toString(ansindex%10);
		
		
	

		}
		System.out.print("Answer array :");
		for(int i=0;i<ansarr.length;i++)
			System.out.print(" "+ansarr[i]);
		System.out.println("Neural work! "+index1);
		
			//neural network
			//return conclusion id
			//map and return conclusion from sql
	}
}

public void updateString(String answer)
{
  this.diagnosiscode=this.diagnosiscode+answer;
}
@SuppressWarnings("null")



//main




public static void main(String[] args)
{ //int index = 1;
	String []ansarr=new String[27];
	for(int i=0;i<ansarr.length;i++)
		ansarr[i]="-2";
	Question q=new Question();
	SQL sql1=new SQL("root","","fever","doctok");
	String[][] prev_ref=q.get_table();
	int e=1;
	if(sql1.check_feverdata())
	{ e=3;
	String valrc=sql1.get_feverdetails(1);
	ansarr[1]=valrc.substring(1,2);
	ansarr[2]=valrc.substring(3,4);
	System.out.println("ansarr: for sensor data---------------");
	for(int i=0;i<ansarr.length;i++)
	{	
		System.out.print("  "+ansarr[i]);
	}
	for(int l=1;l<=2;l++)
	{
		for(int s=0;s<prev_ref.length;s++)
		{
			prev_ref[s][l]="-1";
		}
	}
	}
		
		
	
		String[] s=q.ask_question(e);
		String[][] referindex = null;
		String [] diagnosis;
	
		int count=0,ansindex=0;
		String answer;
		System.out.println(s[0]);
	//q.make_Symarray();
	
		
	

		for(int i=1;i<s.length;i++)
			{
				System.out.println((10*e+(i-1))+ " "+ q.get_option(10*e+(i-1)));
			}
		System.out.println("Enter option");
		try(BufferedReader bf=new BufferedReader(new InputStreamReader(System.in)))
			{ 
				answer=bf.readLine();
				ansindex=Integer.parseInt(answer);
				ansarr[ansindex/10]=Integer.toString(ansindex%10);  
				referindex=q.makearray(answer,prev_ref);
				for(int i=0;i<referindex.length;i++)
					referindex[i][1]="-1";
				System.out.println("referindex  for next question");
				for(int i=0;i<referindex.length;i++)
					{
						for(int j=0;j<referindex[0].length;j++)
							{
								System.out.print("  "+referindex[i][j]);
							}
						System.out.println("");
					}

				do{
					if(referindex.length!=1)
						{       System.out.println("Prev_ref if referindex.length!=1");
							for(int i=0;i<prev_ref.length;i++)
								{
									for(int j=0;j<prev_ref[0].length;j++)
										{
											System.out.print(" "+prev_ref[i][j]);
										}
									System.out.println("");
								}
			
							System.out.println("referindex if referindex.length!=1");
							for(int i=0;i<referindex.length;i++)
								{	
									for(int j=0;j<referindex[0].length;j++)
										{
											System.out.print("  "+referindex[i][j]);
										}
									System.out.println("");
								}
				
							int index=q.next_question_index(referindex);
							System.out.println("Index: Next Question referindex!=1 ->"+index);
							if(index!=0)
							{
							String[] question_set=q.ask_question(index);
							//System.out.println(""+index);
							System.out.println("Question: "+question_set[0]);
							for(int i=1;i<question_set.length;i++)
								{	
					
									System.out.println((10*(index)+i-1)+" "+q.get_option(10*(index)+i-1));
								//conclusion=q.get_conclusion();
				
								}
							System.out.println("Enter option");
							answer=bf.readLine();
							ansindex=Integer.parseInt(answer);
							ansarr[ansindex/10]=Integer.toString(ansindex%10);
							referindex=q.makearray(answer, referindex);
						//prev_ref=null;
							System.out.println("Prev_Ref Again: referindex.length!=1");
							for(int i=0;i<referindex.length;i++)
								{
									for(int j=0;j<referindex[0].length;j++)
										{
											System.out.print(" "+referindex[i][j]);
										}
									System.out.println("");
								}
							System.out.println("ReferIndex Again: referindex.length!=1");
							for(int i=0;i<referindex.length;i++)
								{	
									for(int j=0;j<referindex[0].length;j++)
										{
											System.out.print(" "+referindex[i][j]);
										}
									System.out.println("");
								}	
							for(int i=0;i<referindex.length;i++)
								{
									referindex[i][index]="-1";//////////////
								}
							System.out.println("Answer Array: referindex.length!=1");
							for(int i=0;i<ansarr.length;i++)
								{	
									System.out.print("  "+ansarr[i]);
								}	
							}
							else
							{
								q.nueral(ansarr);
							}
						}	
						else
						{	
							break;
						}
				}while(referindex.length!=0&&referindex.length!=1&&count<26);
		
			
	
		//q.print_wt();
				if(referindex.length==1)
					{  //check for remaining symptoms
						int index1=1;
						//System.out.println("index==0");
						while(index1<26)
							{ //  System.out.print(" "+index1);
								if(Integer.parseInt(referindex[0][index1])>0)
									{
										String[] question_set=q.ask_question(index1);
										System.out.println("Question Index referindex.length=1 ->"+index1);
										System.out.println("Question "+question_set[0]);
										for(int i=1;i<question_set.length;i++)
											{		
												System.out.println((10*(index1)+i-1)+" "+q.get_option(10*(index1)+i-1));
											}	
										System.out.println("Enter option");
									//Scanner sc=new Scanner(System.in);
				
										answer=bf.readLine();
										ansindex=Integer.parseInt(answer);
										if((ansindex%10)==Integer.parseInt(referindex[0][index1]))
											{
												ansarr[ansindex/10]=Integer.toString(ansindex%10);////////////???????
			
			
											}
										else
											{
											//q.nueral(ansarr);
												ansarr[ansindex/10]=Integer.toString(ansindex%10);
												break;
											}
									}
								System.out.print("referindex.length=1  index1  ="+index1);
								index1++;
							}
					
					if(index1==26)
						{
							SQL sql = new SQL("root","", "conclusion", "doctok");
							System.out.println("ReferIndex Again: all questions asked");
							for(int i=0;i<referindex.length;i++)
								{	
								for(int j=0;j<referindex[0].length;j++)
									System.out.print(referindex[i][j]);
								}
			
							String id=referindex[0][0];
							String con=sql.get_conclusion(id);
							System.out.println("Conclusion "+con);
						}
					else
						{
							q.nueral(ansarr);
						}
					}
			
	
					if(referindex.length==0)
						{
							q.nueral(ansarr);
						}
					if(count>26)
						{
							System.out.println("we can't predict the conclusion");
						}
			}
			catch(Exception e1)
			{
				System.out.println(""+e1);
			}

	
	}
}