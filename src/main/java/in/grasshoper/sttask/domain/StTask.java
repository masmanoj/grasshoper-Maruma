package in.grasshoper.sttask.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "st_tasks")
public class StTask extends AbstractPersistable<Long>{
	@Column(name = "task_img_url")
	private String url;
	@Column(name = "status")
	private String status;
	@Column(name = "answer")
	private String answer;
	@Column(name = "task")
	private String task;
	
	protected StTask(){}

	public StTask(String url, String status, String answer, String task) {
		super();
		this.url = url;
		this.status = status;
		this.answer = answer;
		this.task = task;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getTask() {
		return this.task;
	}

	public void setTask(String task) {
		this.task = task;
	};
	
	
}
