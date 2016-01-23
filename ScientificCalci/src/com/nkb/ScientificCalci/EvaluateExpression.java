package com.nkb.ScientificCalci;

public class EvaluateExpression {
	private String infix,postfix;
	boolean valid;
	double ans;
	EvaluateExpression() {
		infix=postfix=null;
		valid=false;
		ans=0;
	}
	EvaluateExpression(String in) {
		infix=in;
		postfix="";
		valid=false;
		ans=0;
	}
	EvaluateExpression(EvaluateExpression ee) {
		infix=ee.infix;
		postfix=ee.postfix;
		valid=ee.valid;
		ans=ee.ans;
	}
	boolean validateExpression() {
		int flag=0;
		
		//	check for operations in the begining or end of expression
		if(infix.charAt(0)=='+' || infix.charAt(0)=='-' || infix.charAt(0)=='*' || infix.charAt(0)=='/' || 
				infix.charAt(0)=='^' || infix.charAt(0)=='.' || infix.charAt(infix.length()-1)=='+' || 
				infix.charAt(infix.length()-1)=='-' || infix.charAt(infix.length()-1)=='*' || infix.charAt(infix.length()-1)=='/' || 
				infix.charAt(infix.length()-1)=='^' || infix.charAt(infix.length()-1)=='.' ) {
			valid=false;
			return valid;
		}
		
		//	check for consecutive operators
		for(int i=0;i<infix.length();i++) {
			if(infix.charAt(i)=='+' || infix.charAt(i)=='-' || infix.charAt(i)=='*' || infix.charAt(i)=='/' || 
					infix.charAt(i)=='^' || infix.charAt(i)=='.') {
				if(flag==1) {
					valid=false;
					return valid;
				}
				flag=1;
			}
			else
				flag=0;
		}
		//	check for more than one decimal points in a number
		int s=0,e=-1,c;
		flag=0;
		String str;
		for(int i=0;i<infix.length();i++) {
			if(infix.charAt(i)=='+' || infix.charAt(i)=='-' || infix.charAt(i)=='*' || infix.charAt(i)=='/' || 
					infix.charAt(i)=='^') {
				flag=1;
				e=i;
				str=infix.substring(s,e);
				c=0;
				for(int j=0;j<str.length();j++)
					if(str.charAt(j)=='.') c++;
				if(c>1) {
					valid=false;
					return valid;
				}
				i++;
				s=i;
			}
		}
		if(flag==0){
			s=0;
			e=infix.length()-1;
			str=infix.substring(s,e);
			c=0;
			for(int j=0;j<str.length();j++)
				if(str.charAt(j)=='.') c++;
			if(c>1) {
				valid=false;
				return valid;
			}
		}
		
		//	check for proper brackets used
		valid=true;
		CharacterStack stack=new CharacterStack(10);
		char ch;
		for(int i=0;i<infix.length();i++) {
	        if(infix.charAt(i)=='(' || infix.charAt(i)=='[' || infix.charAt(i)=='{')
	           stack.push(infix.charAt(i));
	        else if(infix.charAt(i)==')' || infix.charAt(i)==']' || infix.charAt(i)=='}') {
	            if(stack.empty())
	                valid=false;
	            else {
	                ch=stack.pop();
	                if(!(infix.charAt(i)==')'&&ch=='(' ||
	                     infix.charAt(i)==']'&&ch=='[' ||
	                     infix.charAt(i)=='}'&&ch=='{'))
	                    valid=false;
	            }
	        }
	    }
	    if(!stack.empty())
	        valid=false;
		return valid;
	}
	void convertInfixToPostfix() {
		CharacterStack stack=new CharacterStack(10);
		int s,e,k;
		String str;
	    for(int i=0;i<infix.length();i++) {
	    	if((infix.charAt(i)>='0' && infix.charAt(i)<='9') || infix.charAt(i)=='.') {
	    		s=i;
	    		for(k=i;k<infix.length() && ((infix.charAt(k)>='0' && infix.charAt(k)<='9') || infix.charAt(k)=='.');k++);
	    		e=k;
	    		str=infix.substring(s,e);
	    		postfix=postfix.concat(str);
	    		postfix=postfix.concat(" ");
	    		i=k-1;
	    		continue;
	    	}
	    	else {
	            while(!stack.empty() && prcd(stack.peep(stack.topOfStack()),infix.charAt(i))) {
	            	postfix=postfix.concat(Character.toString(stack.pop()));
	            	postfix=postfix.concat(" ");
	            }
	            if(stack.empty() || infix.charAt(i)!=')')
	                stack.push(infix.charAt(i));
	            else
	                stack.pop();
	        }
	    }
	    while(!stack.empty()) {
	    	postfix=postfix.concat(Character.toString(stack.pop()));
	    	postfix=postfix.concat(" ");
	    }
	    postfix=postfix.substring(0,postfix.length()-1);
	}
	boolean prcd(char op1,char op2) {
	    if(op1=='(')
	       return false;
	    else if(op1!=')' && op2=='(')
	        return false;
	    else if(op1!='(' && op2==')')
	        return true;
	    else if(op1==')') {
	        System.exit(0);
	        return false;
	    }
	    else if(assignprcd(op1)>=assignprcd(op2))
	        return true;
	    else
	        return false;
	}

	int assignprcd(char op) {
	    switch(op) {
	        case '^':   return 3;
	        case '*':
	        case '/':   return 2;
	        case '+':
	        case '-':   return 1;
	        case '(':   return 0;
	    }
	    return 0;
	}
	String postfixReturn() {
		return postfix;
	}
	double EvaluatePostfixExpression() {
		NumberStack S=new NumberStack(10);
	    double opnd1,opnd2;
	    int s=0,e=-1,k;
	    for(int i=0;i<postfix.length();i++) {
	        if(postfix.charAt(i)>='0' && postfix.charAt(i)<='9') {
	    		s=i;
	    		for(k=i;k<postfix.length() && postfix.charAt(k)!=' ';k++);
	    		e=k;
	    		S.push(Double.parseDouble(postfix.substring(s,e)));
	    		i=k;//-1;
	    		continue;
	    	}
	        else {
	            opnd2=S.pop();
	            opnd1=S.pop();
	            S.push(perform(opnd1,opnd2,postfix.charAt(i)));
	            i++;
	        }
	    }
	    ans=S.pop();
	    return ans;
	}
	double perform(double opnd1,double opnd2,char op) {
	    switch(op) {
	        case '*': return opnd1*opnd2;
	        case '/': return opnd1/opnd2;
	        case '+': return opnd1+opnd2;
	        case '-': return opnd1-opnd2;
	        case '^': return Math.pow(opnd1,opnd2);
	    }
	    return 0;
	}
	
}
