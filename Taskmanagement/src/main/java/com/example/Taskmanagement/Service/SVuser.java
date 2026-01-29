//package com.example.Taskmanagement.Service;
//
//import com.example.Taskmanagement.Model.User;
//import com.example.Taskmanagement.Validation.Vduser;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SVuser {
//    private final List<User> users = new ArrayList<>();
//
//    public void adduser(User user) {
//        Vduser.user(user);
//
//        if(user.getId() == null){
//            throw new IllegalArgumentException("User mustn't be Null!!");
//    }
//
//        users.add(user);
//    }
//
//   public User find(Long id){
//        for(User u : users){
//            if(u.getId().equals(id)){
//                return u;
//            }
//        }
//        throw new IllegalArgumentException("User not Found !!");
//   }
//}
