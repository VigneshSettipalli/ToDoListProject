package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ToDoDao;
import com.model.ToDoList;


@Service
public class ToDoServiceImpl implements ToDoService {

	@Autowired
	ToDoDao toDoDao;
	
	@Override
	public ToDoList getToDoList(int id) {
		return toDoDao.getToDoList(id);		
	}

	@Override
	public List getAllToDoList() {
		return toDoDao.getAllToDoList();
	}

	@Override
	public void postToDoList(ToDoList todo) {
		toDoDao.postToDoList(todo);
	}

	@Override
	public int deleteToDoList(int id) {
		return toDoDao.deleteToDoList(id);
		
	}

	@Override
	public void putToDoList(ToDoList todo,int id) {
		toDoDao.putToDoList(todo,id);
		
	}

}
