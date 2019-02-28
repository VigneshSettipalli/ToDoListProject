package com.dao;

import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mapper.ToDoMapper;
import com.model.ToDoList;


public class ToDoJdbcTemplate implements ToDoDao {
	
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public List getAllToDoList()
	{
		List ToDoAllList;
		String sql = "select * from ToDo order by id asc";
		ToDoAllList = jdbcTemplateObject.query(sql, new Object[]{},new ToDoMapper());
		return ToDoAllList;	
		
	}
	public ToDoList getToDoList(int id) {
		ToDoList toDoList;		
		String sql = "select * from ToDo where id = ?";
		toDoList = jdbcTemplateObject.queryForObject(sql, new Object[]{id},new ToDoMapper());		
		return toDoList;		
	}

	
	public void postToDoList(ToDoList todo) {
		String sql = "insert into ToDo values(?,?,?,?)";
		int id = todo.getId();
		String name = todo.getName();
		String date = todo.getDate();
		String priority = todo.getPriority();		
		int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };		   
		jdbcTemplateObject.update(sql, new Object[]{id,name,date,priority}, types);		
	}

	@Override
	public int deleteToDoList(int id) {
		String sql = "delete from ToDo where id = ?";
		int value = jdbcTemplateObject.update(sql, new Object[]{id});
		return value;
		
	}

	@Override
	public void putToDoList(ToDoList todo, int id) {
		String sql = "update ToDo set id = ?, name = ?, duedate = ?, priority = ? where id = ?";
		int toDoId = todo.getId();
		String name = todo.getName();
		String date = todo.getDate();
		String priority = todo.getPriority();
		int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER };		 
		jdbcTemplateObject.update(sql,new Object[]{toDoId,name,date,priority,id});
		
	}

}
