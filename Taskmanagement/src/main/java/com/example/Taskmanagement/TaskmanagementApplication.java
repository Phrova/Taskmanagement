package com.example.Taskmanagement;

//import com.example.Taskmanagement.Enum.TaskStatus;
//import com.example.Taskmanagement.Enum.UserRole;
//import com.example.Taskmanagement.Model.Task;
//import com.example.Taskmanagement.Model.User;
//import java.time.LocalDate;
//import com.example.Taskmanagement.Service.SVtask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class TaskmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagementApplication.class, args);

//		SVtask taskService = new SVtask();
//
//		User user1 = new User(1L, "hai", "123456", "hai@gmail.com", UserRole.USER);
//
//		Task task1 = new Task(
//				1L,
//				"Java",
//				"Service logic",
//				TaskStatus.TODO,
//				LocalDate.now(),
//				LocalDate.now().plusDays(7),
//				null,
//				null
//		);
//
//		System.out.println("--- ADD TASK ---");
//		taskService.add(task1);
//		System.out.println("Task added: " + task1.getTitle());
//
//		System.out.println("\n--- ASSIGN TASK ---");
//		taskService.assignTask(1L, user1);
//		System.out.println("Task assigned to user: " + task1.getUser().getUsername());
//
//		System.out.println("\n--- UPDATE TASK ---");
//		task1.setStatus(TaskStatus.DONE);
//		taskService.update(task1);
//		System.out.println("Task updated, status: " + task1.getStatus());
//
//		System.out.println("\n--- DELETE TASK ---");
//		taskService.delete(1L);
//		System.out.println("Task deleted");
//
//		System.out.println("\n--- TEST LOGIC ERROR (NULL) ---");
//		try {
//			taskService.delete(1L);
//		} catch (Exception e) {
//			System.out.println("ERROR: " + e.getMessage());
//		}
//
//        System.out.println("\n--- TEST LOGIC ERROR (DUPLICATE) ---");
//
//        Task t1 = new Task(1L, "Task 1", "...", TaskStatus.TODO, LocalDate.now(), LocalDate.now().plusDays(7), null, null);
//        Task t2 = new Task(1L, "Task 2", "...", TaskStatus.TODO, LocalDate.now(), LocalDate.now().plusDays(7), null, null);
//
//        taskService.add(t1);
//
//        try {
//            taskService.add(t2);
//        } catch (Exception e) {
//            System.out.println("ERROR: " + e.getMessage());
//        }


	}

}
