package com.swagger;



import java.util.List;

import com.model.ToDoList;

import io.swagger.annotations.ApiModelProperty;

public class Data {



	
	@ApiModelProperty(position = 1, required = true, value = "brief description of the property :output ")
	private List<ToDoList> ToDoList;

	public List<ToDoList> getToDoList() {
		return ToDoList;
	}

	public void setToDoList(List<ToDoList> toDoList) {
		ToDoList = toDoList;
	}

	

	
	
}
