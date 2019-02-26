package com.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.model.ToDoList;

public class ToDoMapper implements RowMapper<ToDoList> {

	@Override
	public ToDoList mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ToDoList toDoList = new ToDoList();
		toDoList.setId(rs.getInt("id"));
		toDoList.setName(rs.getString("name"));
		toDoList.setDate(rs.getString("duedate"));
		toDoList.setPriority(rs.getString("priority"));		
		
		return toDoList;
	}

}
