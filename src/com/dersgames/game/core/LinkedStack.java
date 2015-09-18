package com.dersgames.game.core;

/**
 * A basic stack structure implemented as a linkedlist
 * Primarily used for state handling in this Game Engine so far
 * 
 * @author Anders
 *
 */
public class LinkedStack<E> {
	
	public Node<E> top;
	
	public LinkedStack(){
		top = null;
	}
	
	public void push(E elem){
		if(top == null){
			top = new Node<E>(elem,null);
		}else{
			Node<E> newTop = new Node<E>(elem, null);
			newTop.next = top;
			top = newTop;
		}
	}
	
	public void pop(){
		if(top != null){
			Node<E> temp = top.next;
			top = temp;
		}
	}
	
	public E peek(){
		return top.data;
	}
	
	public E pop(E elem){
		return elem;
	}

}
