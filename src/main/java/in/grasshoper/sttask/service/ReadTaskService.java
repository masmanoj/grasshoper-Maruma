package in.grasshoper.sttask.service;

import in.grasshoper.core.security.exception.PlatformUnknownDBException;
import in.grasshoper.sttask.data.TaskData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class ReadTaskService {
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ReadTaskService(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
	}

	public Collection<TaskData> retriveAll() {
		TaskMapper taskMapper = new TaskMapper();
	        try {
	            final String sql = "select " + taskMapper.schema();

	            return this.jdbcTemplate.query(sql, taskMapper);
	        } catch (DataAccessException exception){
	        	exception.printStackTrace();
	            throw new PlatformUnknownDBException();
	        }
		
	}
	
	
	
	public TaskData retriveNextTask() {
		TaskMapper taskMapper = new TaskMapper();
	        try {
	            final String sql = "select " + taskMapper.schema() + " where status = 'Open' or status='Dare' order by status, id limit 1" ;
	            return this.jdbcTemplate.queryForObject(sql, taskMapper);
	        } catch (DataAccessException exception){
	        	exception.printStackTrace();
	            return null;
	        }
		
	}
	
	
	
	 protected static final class TaskMapper implements RowMapper<TaskData> {

	        @Override
	        public TaskData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {

	            final Long id = rs.getLong("id");
	            
	            final String status = rs.getString("status");
	            String task = null;
	            String taskImgIrl = null;
	            if (!"Locked".equals(status)){
	            	if (!"Open".equals(status)){
	            		task = rs.getString("task");
	            	}
	            	taskImgIrl = rs.getString("task_img_url");
	            }
	            return TaskData.createNew(id, taskImgIrl, status, task);
	        }

	        public String schema() {
	            return " id, task_img_url, status, task  from st_tasks ";
	        }
	    }

}
