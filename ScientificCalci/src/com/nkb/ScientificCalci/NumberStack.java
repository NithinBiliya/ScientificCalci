package com.nkb.ScientificCalci;

public class NumberStack {
	double S[];
	int top;
	NumberStack(int s) {
		S=new double[s];
		top=-1;
	}
	NumberStack(NumberStack NS) {
		S=NS.S;
		top=NS.top;
	}
	void push(double ele) {
		if(top==S.length-1) {
			double temp[]=new double[S.length*2];
			for(int i=0;i<S.length;i++)
				temp[i]=S[i];
			S=temp;
		}
		S[++top]=ele;
	}
	double pop() {
		if(top==-1)
			return 0;
		return S[top--];
	}
	boolean empty() {
		if(top==-1)
			return true;
		return false;
	}
	double peep(int at) {
		return S[at];
	}
	int topOfStack() {
		return top;
	}
}
