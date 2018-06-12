package in.grasshoper.sttask.api;

import in.grasshoper.core.infra.ApiSerializer;
import in.grasshoper.core.infra.CommandProcessingResult;
import in.grasshoper.core.infra.CommandProcessingResultBuilder;
import in.grasshoper.core.security.service.PlatformSecurityContext;
import in.grasshoper.sttask.data.TaskData;
import in.grasshoper.sttask.service.ReadTaskService;
import in.grasshoper.sttask.service.WriteTaskService;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController  
@RequestMapping("/task")
public class TaskApiResource {

	
	private final PlatformSecurityContext context;
	private final ApiSerializer<TaskData> apiJsonSerializerService;
	private final ReadTaskService readTaskService;
	private final WriteTaskService writeTaskService;
	@Autowired
	public TaskApiResource(final ReadTaskService readTaskService,
			ApiSerializer<TaskData> apiJsonSerializerService,
			final PlatformSecurityContext context,
			final WriteTaskService writeTaskService) {
		super();
		this.readTaskService = readTaskService;
		this.apiJsonSerializerService = apiJsonSerializerService;
		this.context = context;
		this.writeTaskService = writeTaskService;
	}
	
	@RequestMapping(method = RequestMethod.GET)  
	@Transactional(readOnly = true)
    public String retrieveTasks() {
		this.context.restrictPublicUser();
        final Collection<TaskData> result = this.readTaskService.retriveAll( );
        
        return this.apiJsonSerializerService.serialize(result);
    }
	
	

	@RequestMapping(value="/nexttask", method = RequestMethod.GET)  
	@Transactional(readOnly = true)
    public String FetchNextTask() {
        TaskData result = this.readTaskService.retriveNextTask( );
        return this.apiJsonSerializerService.serialize(result);
    }
	
	
	@RequestMapping(value="/unlock/{taskId}", method = RequestMethod.PUT) 
    public CommandProcessingResult unlockTask(@PathVariable("taskId")final Long taskId) {
		this.context.restrictPublicUser();
		this.writeTaskService.unlockTask(taskId);
		return null;
    }
	
	@RequestMapping(value="/close/{taskId}", method = RequestMethod.PUT) 
    public CommandProcessingResult closeTask(@PathVariable("taskId")final Long taskId) {
		this.context.restrictPublicUser();
		this.writeTaskService.closeTask(taskId);
		return null;
    }
	
	
	@RequestMapping(value="/answer/{taskId}/{answer}", method = RequestMethod.PUT)  
    public CommandProcessingResult answerTask(@PathVariable("taskId")final Long taskId,
    		@PathVariable("answer")final String answer) {
		return this.writeTaskService.answerTask(taskId, answer);
    }
	
	
}
