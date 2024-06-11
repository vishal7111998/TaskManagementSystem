package Client;

import java.util.List;

import Model.Task;
import Model.User;
import SystemManager.TaskManager;

public class TaskManagementSystemDemo {

	public static void main(String[] args) {
		TaskManager taskManager = TaskManager.getTaskManagerInstance();

		Task t1 = new Task("1", "Login task", "Develope a login page");
		Task t2 = new Task("2", "SignUp task", "Develope a signup page");
		Task t3 = new Task("3", "Ui page", "Develope a UI page");
		Task t4 = new Task("4", "Fix bug", "Fix the bug in feature branch");

		taskManager.createTask(t1);
		taskManager.createTask(t2);

		User user1 = new User("U1", "abc", "abc@gmail.com");
		User user2 = new User("U2", "def", "def@gmail.com");

		taskManager.createTask(t3);
		taskManager.createTask(t4);

		List<Task> taskList = taskManager.getAllTask();
		for (Task task : taskList) {
			System.out.println(task.getTitle());
		}

		taskManager.assignTaskToUser(user1, t1);
		taskManager.assignTaskToUser(user1, t2);
		taskManager.assignTaskToUser(user1, t4);
		taskManager.assignTaskToUser(user2, t3);

		/*
		 * List<Task> userTasks = taskManager.getUserTaskList(user2); for(Task task :
		 * userTasks) { System.out.println(task.getAssignedUser().getName() +
		 * " is assigned " + task.getTitle()); }
		 */

		taskManager.unassignTaskFromUser(user1, t4);
		taskManager.assignTaskToUser(user2, t4);

		/*
		 * List<Task> userTasks = taskManager.getUserTaskList(user2); for(Task task :
		 * userTasks) { System.out.println(task.getAssignedUser().getName() +
		 * " is assigned " + task.getTitle()); }
		 * 
		 * 
		 * userTasks = taskManager.getUserTaskList(user1); for(Task task : userTasks) {
		 * System.out.println(task.getAssignedUser().getName() + " is assigned " +
		 * task.getTitle()); }
		 */

		
		 Task updatedT1Task = new Task(t1.getId(), t1.getTitle(), t1.getDescription());
		 updatedT1Task.setAssignedUser(user2);
		 
		 List<Task> userTasks = taskManager.getUserTaskList(user1);
			for (Task task : userTasks) {
				System.out.println(task.getAssignedUser().getName() + " is assigned " + task.getTitle());
			}
		 
		 taskManager.updateTask(updatedT1Task);
		 
		 System.out.println("After updating t1 task");
		userTasks = taskManager.getUserTaskList(user1);
		for (Task task : userTasks) {
			System.out.println(task.getAssignedUser().getName() + " is assigned " + task.getTitle());
		}

	}
}
