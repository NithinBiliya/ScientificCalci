package com.nkb.ScientificCalci;

public class CharacterStack {
	private char S[];
	private int top;
	CharacterStack(int s) {
		S=new char[s];
		top=-1;
	}
	CharacterStack(CharacterStack CS) {
		S=CS.S;
		top=CS.top;
	}
	void push(char ele) {
		if(top==S.length-1) {
			char temp[]=new char[S.length*2];
			for(int i=0;i<S.length;i++)
				temp[i]=S[i];
			S=temp;
		}
		S[++top]=ele;
	}
	char pop() {
		if(top==-1)
			return '$';
		return S[top--];
	}
	boolean empty() {
		if(top==-1)
			return true;
		return false;
	}
	char peep(int at) {
		return S[at];
	}
	int topOfStack() {
		return top;
	}
}
