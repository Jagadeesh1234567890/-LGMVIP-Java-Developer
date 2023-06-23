
/*----------------------------------------------------------------IMPORT ALL REQUIRED LIBRARIES----------------------------------------------------------------------------- */

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Calculator {JFrame frmCalculator;          //class function
					     String result="",expression="";
						 ArrayList<String> token=new ArrayList<String>();
						 boolean num=false;
						 boolean dot=false;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calculator window = new Calculator();
					window.frmCalculator.setVisible(true);} catch (Exception e) {
					e.printStackTrace();}}});
	}
/*------------------------------------------------------------------INITIALIZING THE CALCULATER -----------------------------------------------------------------------------*/
	Calculator() { initialize();}

	int precedence(String x)
	{
		int p=10;   //precedence
			switch(x) {
				case "+":p=1;break;
				case "-":p=2;break;
				case "x":p=3;break;
				case "/":p=4;break;
				case "^":p=5;break;
				case "!":p=6;break;
				}
	
		return p;
	}
	/*--------------------------------------------------------------OPERATOR symbol CHECKING--------------------------------------------------------------------------------- */
	private boolean isoperator(String x)
	{
		if(x.equals("+") || //ADDITION
        x.equals("-") ||    //SUBTRACTION
         x.equals("x") ||   //MULTIPLICATION
         x.equals("/") ||   //DIVISIBLE
          x.equals("sqrt")  //SQUAREROOT
          || x.equals("^")  //SQUARE
          || x.equals("!")  //PREFIX
          || x.equals("sin") //sin function
           || x.equals("cos") //cos function
           || x.equals("tan")  //tan funnction
           || x.equals("ln")  //denoted for base e
           || x.equals("log")) //logarithm
			return true;
		else 
			return false;
	}
	
	private String infixTopostfix()
	 { Stack<String> s=new Stack<String>();
		 String y;
		 int flag;
		 String p="";   
		 token.add(")");
		 s.push("(");
		  for(String i: token) {
			if(i.equals("(")){
				s.push(i);}
              else if(i.equals(")")){y=s.pop();
				while(!y.equals("(")){
					p=p+y+",";
					y=s.pop();
					}}
			  else if(isoperator(i)){
				y=s.pop();
				flag=0;
				if(isoperator(y) && precedence(y)>precedence(i)){
					p=p+y+",";
					flag=1;}
				  if(flag==0)
					s.push(y);
				    s.push(i);}
			      	else{
						p=p+i+",";}}
		             while(!s.empty()){
			           y=s.pop();
			          if(!y.equals("(") && !y.equals(")")){
				     p+=y+",";}}
		return p;
	}

/*---------------------------------------------------------------------factorial method----------------------------------------------------------------------------------*/
	private double factorial(double y) {  //enabling factorial method
		double fact=1;
		if(y==0 || y==1) {
			fact=1;}
			else {
			for(int i=2; i<=y; i++) {
				fact*=i;}}
		       return fact;
	}
	
	/*-------------------------------------------------------------for actual calculation with binary operators-----------------------------------------------------*/
	private double calculate(double x,double y,String c) //calculation funnction of binary operator
	{
		double res=0;
			switch(c){
				case "-":res= x-y;break;
				case "+":res = x+y;break;
				case "x": res = x*y;break;
				case "/": res = x/y;break;
				case "^": res = Math.pow(x,y);break;
				default :
				res= 0;
			}
		return res;
	}
	
	/*-----------------------------------------------------------------------calculation with unary operators----------------------------------------------------------*/
	private double calculate(double y,String c) {   //calculation function block for unary operator
		double res=0;
		switch(c) {
			case "log": res = Math.log10(y); break;
			case "sin": res = Math.sin(y); break;
			case "cos": res = Math.cos(y); break;
			case "tan": res = Math.tan(y); break;
			case "ln": res = Math.log(y); break;
			case "sqrt": res = Math.sqrt(y); break;
			case "!": res = factorial(y); break;
			}
		return res;
	}

	private double Eval(String p)
	{	
		String tokens[] = p.split(","); //
		ArrayList<String> token2=new ArrayList<String>();//
		for(int i=0; i<tokens.length; i++) {//
			if(! tokens[i].equals("") && ! tokens[i].equals(" ") && ! tokens[i].equals("\n") && ! tokens[i].equals("  ")) {
				token2.add(tokens[i]);  // tokens from post fix form p actual tokens for calculation..
			}
		}
		
		Stack<Double> s=new Stack<Double>();
		double x,y;
		for(String  i:token2) {
			if(isoperator(i)){
				//if it is unary operator or function..
				if(i.equals("sin") ||i.equals("cos") ||i.equals("tan") ||i.equals("log") || i.equals("ln") || i.equals("sqrt") || i.equals("!")) {
					y=s.pop();
					s.push(calculate(y,i));}
                    else {
					//for binary operators..
					y=s.pop();
					x=s.pop();
					s.push(calculate(x,y,i));}}
                    else{
				if(i.equals("pi"))
					s.push(Math.PI);
				else if(i.equals("e"))
					s.push(Math.E);
				else
					s.push(Double.valueOf(i));}}
		double res=1;
		while(!s.empty()) {
			res*=s.pop();}
		return res;  //final result
	}

	//actual combined method for calculation 
	private void calculateMain() {
		String tokens[]=expression.split(",");//
		for(int i=0; i<tokens.length; i++) {
			if(! tokens[i].equals("") && ! tokens[i].equals(" ") && ! tokens[i].equals("\n") && ! tokens[i].equals("  ")) {token.add(tokens[i]);}}
		try {
			double res = Eval(infixTopostfix());
			result= Double.toString(res);}
		catch(Exception e) {}}
	
	/*------------------------------------------DESIGNING THE OUTPUT FRAME WITH THEIR ACTION LISTNER-------------------------------------------------------------------- */
	
	
	private void initialize() {
		frmCalculator = new JFrame();
		frmCalculator.setResizable(false);
		frmCalculator.setTitle("SCIENTIFIC-CALCULATOR");
		frmCalculator.getContentPane().setBackground(new Color(250, 104, 0));
		frmCalculator.getContentPane().setFont(new Font("Times New Romon", Font.BOLD, 20));
		frmCalculator.getContentPane().setForeground(SystemColor.windowBorder);
		frmCalculator.getContentPane().setLayout(null);
		
		JPanel textPanel = new JPanel();  ////BOTH OUTPUT SCREEN BOX RESOLUTION
		textPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		textPanel.setBounds(34, 25, 316, 80);
		frmCalculator.getContentPane().add(textPanel);
		textPanel.setLayout(null);
		
        

		JLabel exprlabel = new JLabel("");   ////RESULT BOX
		exprlabel.setBackground(SystemColor.BLUE);
		exprlabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		exprlabel.setHorizontalAlignment(SwingConstants.LEFT);
		exprlabel.setForeground(UIManager.getColor("Button.disabledForeground"));
		exprlabel.setBounds(2, 2, 315, 30);
		textPanel.add(exprlabel);
		
		JTextField textField = new JTextField(); ////TYPING BOX OF  NUMBERS
		exprlabel.setLabelFor(textField);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setBackground(SystemColor.GREEN);
		textField.setEditable(false);
		textField.setText("0");
		textField.setBorder(null);
		textField.setFont(new Font("Times New Roman", textField.getFont().getStyle(), 30));
		textField.setBounds(2, 30, 312, 50);
		textPanel.add(textField);
		textField.setColumns(10);
		
		JPanel butttonPanel = new JPanel(); ///NUMBER KEYS RESOLUTION 
		butttonPanel.setBorder(new BevelBorder(BevelBorder.RAISED,null, null, null, null));
		butttonPanel.setBackground(SystemColor.inactiveCaptionBorder);
		butttonPanel.setBounds(34, 120, 315, 320);
		frmCalculator.getContentPane().add(butttonPanel);
		butttonPanel.setLayout(new GridLayout(6, 5, 5, 5));

/*-------------------------------------------------------------------#BUTTON SPACE(TOTAL 30)#-------------------------------------------------------------------------------------- */
				/*BUTTON 1 */	
		
		JButton button1 = new JButton("C");    //clear button
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("0");
				exprlabel.setText("");
				expression ="";
				token.clear();
				result="";
				num=false;
				dot=false;
			}
		});
		button1.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button1);

			/*BUTTON 2 */	
		
		JButton button2 = new JButton("DEL"); 					 //DELETE BUTTON
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=textField.getText();
				if(s != "0" && s.length() > 1) {
					String newString = s.substring(0,s.length()-1);
					textField.setText(newString);
					if(expression.charAt(expression.length()-1)=='.') {
						dot=false;}
					if(expression.charAt(expression.length()-1) == ',') {
						expression = expression.substring(0,expression.length()-2);}
					else {
						expression = expression.substring(0,expression.length()-1);}}
					else {
					textField.setText("0");
					expression="";}}});
		button2.setFont(new Font("Times New Roman", Font.BOLD, 11));
		butttonPanel.add(button2);

			/*BUTTON 3 */	
		
		
		JButton button3 = new JButton("<html><body><span>π</span></body></html>");
		button3.setFont(new Font("Times New Roman", Font.BOLD, 17)); 				// PI BUTTON
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+Character.toString((char)960));}
				else {
					textField.setText(Character.toString((char)960));}
				expression += ",pi";
				num=false;
				dot=false;}});
		butttonPanel.add(button3);

			/*BUTTON 4 */	
		
		JButton button4 = new JButton("<html><body><span>X<sup>y</sup></span></body></html>");  //BUTTON FOR SQUARE
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"^");
					expression+=",^";}
					else {
					textField.setText("0^");
					expression += ",0,^";}
				num=false;
				dot=false;}});
		button4.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button4);

			/*BUTTON 5 */

		JButton buttton5 = new JButton("x!");		//BUTTON FOR FACTORIAL
		buttton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"!");
					expression+=",!";}
					else {
					textField.setText("0!");
					expression+=",0,!";}
				num=false;
				dot=false;}});
		buttton5.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(buttton5);

			/*BUTTON 6 */
		
		JButton button6 = new JButton("sin");  //BUTTON FOR SIN FUNCTION
		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"sin(");}
					else {
					textField.setText("sin(");}
				expression+=",sin,(";
				num=false;
				dot=false;}});
		button6.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button6);

			/*BUTTON 7 */

		JButton button7 = new JButton("(");  //BUTTON FOR LEFT BRACE
		button7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"(");}
					else {
					textField.setText("(");}
				expression+=",(";
				num=false;
				dot=false;}});
		button7.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button7);

			/*BUTTON 8*/

		JButton button8 = new JButton(")");  //BUTTON FOR RIGHT BRACE
		button8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+")");}
					else {
					textField.setText(")");}
				expression+=",)";
				num=false;
				dot=false;}});
		button8.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button8);

			/*BUTTON 9 */

		JButton button9 = new JButton("e");  //BUTTON FOR EXPONENTIAL
		button9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"e");}
					else {
					textField.setText("e");}
				expression+=",e";
				num=false;
				dot=false;}});
		button9.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button9);

			/*BUTTON 10 */
		
		JButton button10 = new JButton("<html><body><span>√</span></body></html>");  //BUTTON FOR SQRT
		button10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+Character.toString((char)8730));}
					else {
					textField.setText(Character.toString((char)8730));}
				expression+=",sqrt";
				num=false;
				dot=false;}});
		button10.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button10);

			/*BUTTON 11 */

		JButton button11 = new JButton("cos");  //BUTTON FOR COSINE FUNCTION
		button11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"cos(");}
					else {
					textField.setText("cos(");}
				expression+=",cos,(";
				num=false;
				dot=false;}});
		button11.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button11);

			/*BUTTON 12 */

		JButton button12 = new JButton("7");   // BUTTON FOR 7 NUMBER
		button12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"7");}
				else {textField.setText("7");}
				if(num) {expression+="7";}
					else {expression+=",7";}
				num=true;}});
		button12.setBackground(new Color(0, 204, 255));
		button12.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button12);

			/*BUTTON 13 */

		JButton button13 = new JButton("8");  //BUTTON FOR NUMBER 8
		button13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"8");}
					else {textField.setText("8");}
				if(num) {expression+="8";}
					else {
					expression+=",8";}
				num=true;}});
		button13.setBackground(new Color(0, 204, 255));
		button13.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button13);

			/*BUTTON 14 */

		JButton button14 = new JButton("9");
		button14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"9");}
					else {
					textField.setText("9");}
				if(num) {
					expression+="9";}
					else {
					expression+=",9";}
				num=true;}});
		button14.setBackground(new Color(0, 204, 255));
		button14.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button14);
		
				/*BUTTON 15 */

		JButton button15 = new JButton("<html><body><span>÷</span></body></html>");  //BUTTON FOR DIVISIBLE OPERATION
		button15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=textField.getText();
				if(s.equals("0")) {
					expression+="0";}
				if(s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== 'x' || s.charAt(s.length()-1) == '+') {
					String newString = s.substring(0,s.length()-1);
					textField.setText(newString+Character.toString((char)247));
					expression = expression.substring(0,expression.length()-1);
					expression += "/";}
						else if(s.charAt(s.length()-1)!= (char)247) {	
					textField.setText(s+Character.toString((char)247));	
					expression+=",/";}
					else {
					textField.setText(s);}
				num=false;
				dot=false;}});
		button15.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button15);

			/*BUTTON 16 */


		JButton button16 = new JButton("tan"); 		//BUTTON FOR TAN FUNCTION
		button16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"tan(");}
					else {
					textField.setText("tan(");}
				expression+=",tan,(";
				num=false;
				dot=false;}});
		button16.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button16);

			/*BUTTON 17 */

		JButton button17 = new JButton("4");		//BUTTON FOR 4 NUMBER
		button17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"4");}
					else {
					textField.setText("4");}
					if(num) {
					expression+="4";}
					else {
					expression+=",4";}
				num=true;}});
		button17.setBackground(new Color(0, 204, 255));
		button17.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button17);

			/*BUTTON 18 */

		JButton button18 = new JButton("5");		//BUTTON FOR NUMBER 5
		button18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"5");}
					else {
					textField.setText("5");}
					if(num) {
					expression+="5";}
					else {
					expression+=",5";}
					num=true;}});
		button18.setBackground(new Color(0, 204, 255));
		button18.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button18);

			/*BUTTON 19 */

		JButton button19 = new JButton("6");      //BUTTON FOR NUMBER 6
		button19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"6");}
					else {
					textField.setText("6");}
					if(num) {
					expression+="6";}
					else {
					expression+=",6";}
				num=true;}});
		button19.setBackground(new Color(0, 204, 255));
		button19.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button19);

			/*BUTTON 20 */

		JButton button20 = new JButton("x");		//BUTTON FOR MULTIPLICATION
		button20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=textField.getText();
				if(s.equals("0")) {
					expression+="0";}
				if(s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== '+' || s.charAt(s.length()-1) == (char)(247)) {
					String newString = s.substring(0,s.length()-1);
					newString += "x";
					textField.setText(newString);
					expression = expression.substring(0,expression.length()-1);
					expression += "x";}
					else if(s.charAt(s.length()-1)!= 'x'){
					s += "x";	
					textField.setText(s);
					expression+=",x";}
					else {
					textField.setText(s);	}
					num=false;
					dot=false;}});
		button20.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button20);

			/*BUTTON 21 */

		JButton button21 = new JButton("ln");		//BUTTON FOR ln
		button21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"ln(");}
					else {
					textField.setText("ln(");}
					expression+=",ln,(";
					num=false;
					dot=false;}});
		button21.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button21);

			/*BUTTON 22 */

		JButton button22 = new JButton("1");  		//BUTTON FOR NUMBER 1
		button22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"1");}
					else {
					textField.setText("1");}
					if(num) {
					expression+="1";}
					else {
					expression+=",1";}
				num=true;}});
		button22.setBackground(new Color(0, 204, 255));
		button22.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button22);

			/*BUTTON 23 */

		JButton button23 = new JButton("2");			//BUTTON FOR NUMBER 2
		button23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"2");}
					else {
					textField.setText("2");}
					if(num) {
						expression+="2";}
					else {
					expression+=",2";}
				num=true;}});
		button23.setBackground(new Color(0, 204, 255));
		button23.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button23);

			/*BUTTON 24 */

		JButton button24 = new JButton("3");        //BUTTON FOR NUMBER 3
		button24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"3");}	
					else {
					textField.setText("3");}
				if(num) {
					expression+="3";}
				else {
					expression+=",3";
				}
				num=true;}});
		button24.setBackground(new Color(0, 204, 255));
		button24.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button24);

			/*BUTTON 25 */

		JButton button25 = new JButton("-");  		//BUTTON FOR SUBTRACTION
		button25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=textField.getText();
				if(s.equals("0")) {
					expression+="0";}
				if(s.charAt(s.length()-1)== '+') {
					String newString = s.substring(0,s.length()-1);
					newString += "-";
					expression = expression.substring(0,expression.length()-1);
					expression += "-";
					textField.setText(newString);}
					else if(s.charAt(s.length()-1)!= '-') {
					s += "-";	
					textField.setText(s);
					expression += ",-";}
					else {
					textField.setText(s);}
					num=false;
					dot=false;}});
		button25.setFont(new Font("Times New Roman", Font.BOLD, 23));
		butttonPanel.add(button25);
		
			/*BUTTON 26 */

		JButton button26 = new JButton("<html><body><span>log<sub>10</sub></span></body></html>"); 		//BUTTON FOR LOGARITHm
		button26.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! "0".equals(textField.getText())) {
					textField.setText(textField.getText()+"log(");}
					else {
					textField.setText("log(");}
				expression+=",log,(";
				num=false;
				dot=false;}});
		button26.setFont(new Font("Times New Roman", Font.BOLD,17));
		butttonPanel.add(button26);

			/*BUTTON 27 */

		JButton button27 = new JButton(".");		//BUTTON FOR DOT
		button27.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=textField.getText();
				if(s.charAt(s.length()-1)!= '.') {
					if(num && dot==false) {
						expression+=".";
						s += ".";}
						else if(num==false && dot ==false){
						expression+=",.";
						s += ".";}}
				num=true;
				dot=true;
				textField.setText(s);}});
		button27.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button27);

			/*BUTTON 28 */

		JButton button28 = new JButton("0");		//BUTTON FOR ZERO
		button28.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if("0".equals(textField.getText())) {
					textField.setText("0");}
					else {
					textField.setText(textField.getText()+"0");
					if(num) {
						expression+="0";}
						else {
						expression+=",0";}}
						num=true;}});
		button28.setBackground(new Color(0, 204, 255));
		button28.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button28);
		
			/*BUTTON 29 */
	
		JButton button29 = new JButton("=");      //BUTTON FOR RESULT EQUALS TO
		button29.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				calculateMain();
					String s="";
					token.remove(token.size()-1);
					for(String i: token) {
						if(i.equals("/")) {
							s+=Character.toString((char)247);}
							else if(i.equals("sqrt")) {
							s+=Character.toString((char)8730);}
							else if(i.equals("pi")) {
							s+=Character.toString((char)960);}
							else {
							s+=i;}}
					exprlabel.setText(s+"=");
					textField.setText(result);
					expression = result;
					dot=true;
					num=true;
					token.clear();}});
		button29.setBackground(Color.YELLOW);
		button29.setFont(new Font("Times New Roman", Font.BOLD, 22));
		butttonPanel.add(button29);

			/*BUTTON 30 */

		JButton button30 = new JButton("+");        //BUTTON FOR ADDITION OPERATION
		button30.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=textField.getText();
				if(s.equals("0")) {
					expression+="0";}
				if(s.charAt(s.length()-1)== '-' || s.charAt(s.length()-1)== 'x' || s.charAt(s.length()-1) == (char)(247)) {
					String newString = s.substring(0,s.length()-1);
					newString += "+";
					textField.setText(newString);
					expression = expression.substring(0,expression.length()-1);
					expression += "+";}
					else if(s.charAt(s.length()-1)!= '+') {
					s += "+";	
					textField.setText(s);
					expression+=",+";}
					else {
					textField.setText(s);	}
					num=false;
					dot=false;}});
		button30.setFont(new Font("Times New Roman", Font.BOLD, 17));
		butttonPanel.add(button30);
		frmCalculator.setBounds(200, 100, 400, 500);
		frmCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}