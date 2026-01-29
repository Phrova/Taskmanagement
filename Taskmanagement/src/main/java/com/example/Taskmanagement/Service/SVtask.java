//package com.example.Taskmanagement.Service;
//
//import com.example.Taskmanagement.Model.Task;
//import com.example.Taskmanagement.Model.User;
//import com.example.Taskmanagement.Validation.Vdtask;
//import java.util.ArrayList;
//
//import java.util.List;
//
//public class SVtask {
//
//    private final List<Task> tasks = new ArrayList<>();
//
//
//    public void add(Task task) {
//        Vdtask.tasks(task);
//
//        if(existsById(task.getId())){
//         throw new IllegalArgumentException("Duplicate Task ID !!");
//        }
//
//
//tasks.add(task);
//    }
//
//    public void update(Task uptask) {
//        Vdtask.tasks(uptask);
//
//Task task = findById(uptask.getId());
//task.setUser(uptask.getUsers());
//task.setProject(uptask.getProjects());
//task.setTitle(uptask.getTitle());
//        task.setDescription(uptask.getDescription());
//task.setStatus(uptask.getStatus());
//task.setCreateDate(uptask.getCreateDate());
//task.setDueDate(uptask.getDueDate());
//
//
//
//    }
//
//    public void delete(Long id){
// Task task = findById(id);
// tasks.remove(task);
//    }
//
//    public List<Task> getAllTasks() {
//        return tasks;
//    }
//
//    public void assignTask(Long id, User user) {
//        if (user == null) {
//            throw new IllegalArgumentException("User must not be Null !!");
//        }
//
//        Task task = findById(id);
//        task.setUser(user);
//
//        }
//
//
//    private Task findById(Long id) {
//        return tasks.stream()
//                .filter(t -> t.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() ->
//                        new IllegalStateException("Task not Found !!"));
//    }
//
//
//
//    public boolean existsById(Long id){
//        return tasks.stream().anyMatch(t -> t.getId().equals(id));
//    }
//}
