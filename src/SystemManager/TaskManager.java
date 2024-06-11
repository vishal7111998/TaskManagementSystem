package SystemManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Exception.TaskNotFoundException;
import Exception.UserTaskNotFoundException;
import Model.PriorityLevel;
import Model.Task;
import Model.TaskStatus;
import Model.User;

public class TaskManager {
	private static TaskManager taskManager = null;
	Map<String, Task> tasks;
	Map<String, List<Task>> userTasks;
	
	private TaskManager() {
		tasks = new ConcurrentHashMap<>();
		userTasks = new ConcurrentHashMap<>();
	}
	
	public static TaskManager getTaskManagerInstance() {
		if(taskManager == null) {
			synchronized(TaskManager.class) {
				if(taskManager == null) {
					taskManager = new TaskManager();
				}
			}
		}
		return taskManager;
	}
	
	public void createTask(Task task) {
		tasks.put(task.getId(), task);
	}
	
	public void updateTask(Task updatedTask) {
		Task existingTask = tasks.get(updatedTask.getId());
		if(existingTask != null) {
			synchronized(existingTask) {
				existingTask.setDescription(updatedTask.getDescription());
				existingTask.setDueDate(updatedTask.getDueDate());
				existingTask.setPriorityLevel(updatedTask.getPriorityLevel());
				existingTask.setTaskStatus(updatedTask.getTaskStatus());
				existingTask.setTaskType(updatedTask.getTaskType());
				existingTask.setTitle(updatedTask.getTitle());
				
				User prevoiusUser = existingTask.getAssignedUser();
				User newUser = updatedTask.getAssignedUser();
				if(prevoiusUser == null) {
					assignTaskToUser(newUser, existingTask);
				}
				else if(prevoiusUser != newUser) {
					unassignTaskFromUser(prevoiusUser, existingTask);
					assignTaskToUser(newUser, existingTask);	
				}
			}
		}
	}
	
	public void assignTaskToUser(User user, Task task) {
		if(user != null) {
			List<Task> taskList = userTasks.get(user.getId());
			if(taskList == null) {
				taskList = new ArrayList<>();
				taskList.add(task);
				
				userTasks.put(user.getId(), taskList);
			}
			else {
				taskList.add(task);
			}
			task.setAssignedUser(user);
			tasks.put(task.getId(), task);
		}
	}
	
	public void unassignTaskFromUser(User user, Task task) {
		List<Task> taskList = userTasks.get(user.getId());
		if(taskList.contains(task)) {
			taskList.remove(task);
		}
		userTasks.put(user.getId(), taskList);
	}
	
	public void deleteTask(Task task) {
		if(tasks.get(task.getId()) == null) {
			throw new TaskNotFoundException();
		}
		
		tasks.remove(task.getId());
		if(task.getAssignedUser() != null) {
			unassignTaskFromUser(task.getAssignedUser(), task);
		}
	}
	
	public Task searchTask(String id) {
		if(tasks.get(id) == null) {
			throw new TaskNotFoundException();
		}
		
		return tasks.get(id);
	}
	
	public List<Task> searchTaskByKeyword(String keyWord) {
		List<Task> list = new ArrayList<>();
		for(Task task : tasks.values()) {
			if(task.getTitle().contains(keyWord) || task.getDescription().contains(keyWord)) {
				list.add(task);
			}
		}
		
		return list;
	}
	
	
	public List<Task> filterTask(TaskStatus taskStatus, Date startDate, Date dueDate, PriorityLevel priorityLevel){
		List<Task> list = new ArrayList<>();
		for(Task task : tasks.values()){
			if(task.getTaskStatus().equals(TaskStatus.IN_PROGRESS) && 
					(task.getDueDate().compareTo(startDate) >= 0) &&
					(task.getDueDate().compareTo(dueDate) <= 0) &&
					task.getPriorityLevel().equals(PriorityLevel.HIGH)){
							list.add(task);
					}
		}
		return list;
	}
	
	public List<Task> getUserTaskList(User user){
		if(userTasks.get(user.getId()) == null) {
			throw new UserTaskNotFoundException();
		}
		
		return userTasks.get(user.getId());
	}
	
	public List<Task> getAllTask(){
		List<Task> list = new ArrayList<>();
		for(Task task : tasks.values()) {
			list.add(task);
		}
		
		return list;
	}
}
