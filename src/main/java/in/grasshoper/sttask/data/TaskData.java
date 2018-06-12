package in.grasshoper.sttask.data;

public class TaskData {
	private final Long id;
	private final String url;
	private final String status;
	private final String task;
	private TaskData(Long id, String url, String status, String task) {
		super();
		this.id = id;
		this.url = url;
		this.status = status;
		this.task = task;
	}
	
	
	
	public static TaskData createNew(Long id, String url, String status, String task) {
		
		return new TaskData(id, url, status, task);
	}
	
	
}
