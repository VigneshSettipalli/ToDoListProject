package com.dao;

import java.util.List;

import com.model.ToDoList;

public interface ToDoDao {
	
public ToDoList getToDoList(int id);
public List getAllToDoList();
public void postToDoList(ToDoList todo);
public int deleteToDoList(int id);
public void putToDoList(ToDoList todo, int id);
}
