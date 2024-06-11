package Model;

import java.util.Date;

public class Task {
	private final String id;
	private String title;
	private String description;
	private Date dueDate;
	private TaskType taskType;
	private TaskStatus taskStatus;
	private User assignedUser;
	private PriorityLevel priorityLevel;
	
	public Task(String id, String title, String des) {
		this.id = id;
		this.title = title;
		this.description = des;
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public TaskType getTaskType() {
		return taskType;
	}
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
	public TaskStatus getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}
	public User getAssignedUser() {
		return assignedUser;
	}
	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}
	public PriorityLevel getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(PriorityLevel priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	
	

}
