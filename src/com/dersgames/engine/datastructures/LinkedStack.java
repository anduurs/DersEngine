package com.dersgames.engine.datastructures;

/**
 * A basic stack structure implemented as a linkedlist
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
	
	public Node<E> pop(){
		Node<E> topTemp = null;
		if(top != null){
			topTemp = top.next;
			Node<E> temp = top.next;
			top = temp;
		}
		return topTemp;
	}
	
	public E peek(){
		return top.data;
	}
	
	public E pop(E elem){
		return elem;
	}

}
