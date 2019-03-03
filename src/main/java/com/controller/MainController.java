package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.swagger.Data;
import com.swagger.ErrorDetails;
import com.swagger.MetaData;
import com.swagger.Response;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
public class MainController {

	@Autowired
	MetaData metaData;

	@Autowired
	Data data;

	@Autowired
	Response response;

	@Autowired
	ErrorDetails errorDetails;

	@Autowired
	ToDoService toDoService;

	@RequestMapping(value="/")
	public String displayVignesh(ModelMap model){
		model.addAttribute("message", "Hello from Vignesh!");
		return "index";
	}


	@ApiOperation(value = "retrieve all todo records using GET method", notes = "Returns the list of todo data ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Response", response = ToDoList.class),
			@ApiResponse(code = 404, message = "Invalid Information Sent", response = ToDoList.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ToDoList.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ToDoList.class)

	})

	@RequestMapping(value = "ToDo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> getAllToDoList() throws Exception{
		List ToDoList = toDoService.getAllToDoList();
		ResponseEntity<Object> responseEntity = null;

		if (ToDoList.isEmpty()) {
			saveMetaData(false, "Not found", "12345");
			errorDetails.setCode("TRA2001");
			errorDetails.setDescription("ToDo List records not found");
			saveResponse(null, metaData, errorDetails);
			responseEntity = new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
		} else {
			saveMetaData(true, "todo details", "12345");
			saveData(null, ToDoList);
			saveResponse(data, metaData, null);
			responseEntity = new ResponseEntity<Object>(response, HttpStatus.OK);
		}

		return responseEntity;		
	}


	@ApiParam(value = "objects that need to be invoked", required = true)
	@ApiOperation(value = "retrieve a todo record using GET method", notes = "Returns the todo data with todo id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Response", response = ToDoList.class),
			@ApiResponse(code = 404, message = "Invalid Information Sent", response =  ToDoList.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response =  ToDoList.class),
			@ApiResponse(code = 400, message = "Bad Request", response =  ToDoList.class) })
	@RequestMapping(value="ToDo/{id}", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})	
	public ResponseEntity<Object> getToDoList(@PathVariable("id") int id)  {
		ToDoList toDoList = new ToDoList();				
		ResponseEntity<Object> responseEntity = null;
		int flag = 0;
		List<ToDoList> list = new ArrayList<>();
		toDoList = toDoService.getToDoList(id);		
		if (id>0 && id<9999) {
			flag = 1;
		}
		if (flag == 1) {			
			if (toDoList != null) {
				toDoList = toDoService.getToDoList(id);
				list.add(toDoList);
				saveMetaData(true, "ToDo details", "12345");
				saveData(null, list);
				saveResponse(data, metaData, null);

				responseEntity = new ResponseEntity<Object>(response, HttpStatus.OK);
			} else {
				saveMetaData(false, "Not found", "12345");
				errorDetails.setCode("TRA2001");
				errorDetails.setDescription("ToDo id not found");
				saveResponse(null, metaData, errorDetails);
				responseEntity = new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
			}
		} else {
			saveMetaData(false, "Not found", "12345");
			errorDetails.setCode("TRA2005");
			errorDetails.setDescription("String and alphanumeric not allowed for todo id ");
			saveResponse(null, metaData, errorDetails);
			responseEntity = new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}		

		return responseEntity;
	}


	@ApiParam(value = "single record to be inserted", required = true)
	@ApiOperation(value = "Save a ToDo record using POST method", notes = "Create todo data ")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successful Response", response = ToDoList.class),
			@ApiResponse(code = 404, message = "Invalid Information Sent", response = ToDoList.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ToDoList.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ToDoList.class) })
	@RequestMapping(value="ToDo", method = RequestMethod.POST , consumes = {MediaType.APPLICATION_JSON_VALUE} , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> postToDoList(@RequestBody ToDoList todo) throws DataIntegrityViolationException
	{		
		ResponseEntity<Object> responseEntity = null;
		List<ToDoList> list = new ArrayList<ToDoList>();
		if(todo.getId() != 0 && todo.getDate() != null && todo.getName() != null && todo.getPriority() != null)
		{
			toDoService.postToDoList(todo);	
			list.add(todo);
			saveMetaData(true, "ToDo created", "14563");
			saveData(null,list);
			saveResponse(data, metaData, null);
			responseEntity = new ResponseEntity<Object>(response, HttpStatus.CREATED);
		}
		else
		{
			errorDetails.setCode("TRA2006");
			errorDetails.setDescription("Missing Parameters");
			saveMetaData(false, "Null values cannot be inserted", "TRA2002");
			saveResponse(null, metaData, errorDetails);
			responseEntity = new ResponseEntity<Object>(response, HttpStatus.METHOD_NOT_ALLOWED);
		}

		return responseEntity;
	}


	@ApiParam(value = "object that need to be deleted by using one id", required = true)
	@ApiOperation(value = "Delete a todo record using GET method", notes = "Delete the todo data with todo id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful Response", response = ToDoList.class),
			@ApiResponse(code = 404, message = "Invalid Information Sent", response = ToDoList.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ToDoList.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ToDoList.class) })
	@RequestMapping(value="/ToDo/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteToDoList(@PathVariable("id") int id) throws DataNotFoundException {

		ResponseEntity<Object> responseEntity = null;
		if(id > 0 && id <1000){
			List<ToDoList> list = null;
			int result = toDoService.deleteToDoList(id);
			if(result == 0)
				throw new DataNotFoundException();
			saveMetaData(true, "todo deleted details", "14563");
			saveData(null, list);
			saveResponse(data, metaData, null);
			responseEntity = new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		else
		{
			errorDetails.setCode("TRA2005");
			errorDetails.setDescription("String and alphanumeric characters not allowed for todo id");
			saveMetaData(false, "Error Occured", "TRA2005");
			saveResponse(null, metaData, errorDetails);
			responseEntity = new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);	
		}

		return responseEntity;

	}

	@ApiParam(value = "object that need to be updated by using one id", required = true)
	@ApiOperation(value = "Update a todo record using PUT method", notes = "Update the todo data with todo id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successful Response", response = ToDoList.class),
			@ApiResponse(code = 404, message = "Invalid Information Sent", response = ToDoList.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ToDoList.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ToDoList.class) })
	@RequestMapping(value="/ToDo/{id}", method = RequestMethod.PUT, consumes= { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> putToDoList(@PathVariable("id") int id, @RequestBody ToDoList todo) throws Exception {
		ResponseEntity<Object> responseEntity = null;
		List<ToDoList> list = null;
		if(id >0 && id<9999)
		{

			ToDoList toDoList = toDoService.getToDoList(id);		
			if(toDoList!= null)
			{
				toDoService.putToDoList(todo, id);
				//list.add(todo);
				saveMetaData(true, "Updated Successfully", "52152");
				saveData(null, list);
				saveResponse(data, metaData, null);
				responseEntity = new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			else
			{
				errorDetails.setCode("TRA2003");
				errorDetails.setDescription("ToDo id not found");
				saveMetaData(false, "Error Occured", "TRA2003");
				saveResponse(null, metaData, errorDetails);
				responseEntity = new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);		
			}

		}
		else
		{
			errorDetails.setCode("TRA2005");
			errorDetails.setDescription("string and alphanumeric not allowed for todo id");
			saveMetaData(false, "Error Occured", "TRA2005");
			saveResponse(null, metaData, errorDetails);
			responseEntity = new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}

	private void saveResponse(Data data, MetaData metaData, ErrorDetails errorDet) {
		response.setData(data);
		response.setMetaData(metaData);
		response.setError(errorDet);
	}

	private void saveData(ErrorDetails erroDet, List toDoList) {
		response.setError(erroDet);
		data.setToDoList(toDoList);
	}

	private void saveMetaData(boolean success, String description, String responseId) {
		metaData.setSuccess(success);
		metaData.setDescription(description);
		metaData.setResponseId(responseId);
	}

}

