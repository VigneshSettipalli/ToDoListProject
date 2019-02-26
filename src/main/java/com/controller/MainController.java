package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exception.DataNotFoundException;
import com.model.ToDoList;
import com.service.ToDoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
public class MainController {

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String displayVignesh(ModelMap model){
		model.addAttribute("message", "Hello from Vignesh!");
		return "index";
	}


	@Autowired
	ToDoService toDoService;
	

	@RequestMapping(value = "ToDo", method = RequestMethod.GET)
	public @ResponseBody List<ToDoList> getAllToDoList() throws Exception{
		List ToDoList = toDoService.getAllToDoList();
		return ToDoList;
	}


	@RequestMapping(value="ToDo/{id}", method=RequestMethod.GET)	
	public @ResponseBody ToDoList getToDoList(@PathVariable("id") int id)  {
		ToDoList toDoList = new ToDoList();		
		
			toDoList = toDoService.getToDoList(id);

		return toDoList;
	}

	@RequestMapping(value="ToDo", method = RequestMethod.POST)
	public String postToDoList(@RequestBody ToDoList todo) throws DataIntegrityViolationException
	{		

		toDoService.postToDoList(todo);	

		return null;

	}

	@RequestMapping(value="/ToDo/{id}", method = RequestMethod.DELETE)
	public String deleteToDoList(@PathVariable("id") int id) throws DataNotFoundException {
		
		int result = toDoService.deleteToDoList(id);
		if(result == 0)
				throw new DataNotFoundException();
		
		return null;
		
	}

	
	@RequestMapping(value="/ToDo/{id}", method = RequestMethod.PUT)
	public void putToDoList(@PathVariable("id") int id, @RequestBody ToDoList todo) throws Exception {
		toDoService.putToDoList(todo, id);
	}

}

