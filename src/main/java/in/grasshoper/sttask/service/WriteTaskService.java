package in.grasshoper.sttask.service;

import in.grasshoper.core.infra.CommandProcessingResult;
import in.grasshoper.core.infra.CommandProcessingResultBuilder;
import in.grasshoper.sttask.domain.StTask;
import in.grasshoper.sttask.domain.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriteTaskService {

	private final TaskRepository taskRepository;
	@Autowired
	private WriteTaskService(TaskRepository taskRepository) {
		super();
		this.taskRepository = taskRepository;
	}
	
	
	public void unlockTask(final Long taskId){
		StTask task = this.taskRepository.findOne(taskId);
		task.setStatus("Open");
		this.taskRepository.saveAndFlush(task);
	}
	
	public void closeTask(final Long taskId){
		StTask task = this.taskRepository.findOne(taskId);
		task.setStatus("Closed");
		this.taskRepository.saveAndFlush(task);
	}
	
	public CommandProcessingResult answerTask(final Long taskId, final String answer){	
		StTask task = this.taskRepository.findOne(taskId);
		if(task.getAnswer().equalsIgnoreCase(answer)){
			task.setStatus("Dare");
			this.taskRepository.saveAndFlush(task);
			return new CommandProcessingResultBuilder() //
			.withResourceIdAsString(taskId) //
			.withSuccessStatus()
			.build();
		}
		return new CommandProcessingResultBuilder() //
		.withResourceIdAsString(taskId) //
		.withFailureStatus()
		.build();
	}
}
