package com.dao;

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
		String sql = "insert into ToDo values("+todo.getId()+",'"+todo.getName()+"','"+todo.getDate()+"','"+todo.getPriority()+"')";
		jdbcTemplateObject.update(sql);		
	}

	@Override
	public int deleteToDoList(int id) {
		String sql = "delete from ToDo where id = ?";
		int value = jdbcTemplateObject.update(sql, new Object[]{id});
		return value;
		
	}

	@Override
	public void putToDoList(ToDoList todo, int id) {
		String sql = "update ToDo set id = '"+todo.getId()+"', name = '"+todo.getName()+"', duedate = '"+todo.getDate()+"',priority='"+todo.getPriority()+"' where id = ?";
		jdbcTemplateObject.update(sql,new Object[]{id});
		
	}

}
