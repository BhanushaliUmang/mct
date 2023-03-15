package com.example.musicstreamingservice.controller;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.musicstreamingservice.dao.StatusRepository;
import com.example.musicstreamingservice.dao.UserRepository;
import com.example.musicstreamingservice.model.Status;
import com.example.musicstreamingservice.model.Users;
import com.example.musicstreamingservice.service.UserService;
import com.example.musicstreamingservice.util.CommonUtils;

import io.micrometer.common.lang.Nullable;



@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    
    @Autowired
   UserRepository repository;

   @Autowired
   UserService service;


   @Autowired
   StatusRepository repo;

   @PostMapping("/create-user")
   public ResponseEntity<String> createUser(@RequestBody String userData) {

      JSONObject isvalid = validateUserRequest(userData);

      Users user = null;
      if (isvalid.isEmpty()) {
         user = setUser(userData);
         repository.save(user);
      } else {
         return new ResponseEntity<>(isvalid.toString(), HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>("saved", HttpStatus.OK);

   }
   private JSONObject validateUserRequest(String userData) {
    JSONObject json = new JSONObject(userData);
    JSONObject errorlist = new JSONObject();

    if (json.has("username")) {
       String username = json.getString("username");
       List<Users> userlist = repository.findByUsername(username);
       if (!userlist.isEmpty()) {
          errorlist.put("username", "already exist username");
       }

    } else {
       errorlist.put("username", "Missing username");
    }
    if (json.has("password")) {
       String password = json.getString("password");
       if (!CommonUtils.isValidPassword(password)) {
          errorlist.put("password", "notvalid password");
       }

    } else {
       errorlist.put("password", "Missing password");
    }
    if (json.has("firstname")) {
       String firstname = json.getString("firstname");

    } else {
       errorlist.put("firstname", "Missing firstname");
    }
    if (json.has("email")) {
       String email = json.getString("email");
       if (!CommonUtils.isValidEmail(email)) {
          errorlist.put("email", "Not valid email");
       }

    } else {
       errorlist.put("email", "Missing email");
    }
    if (json.has("phonenumber")) {
       String phonenumber = json.getString("phonenumber");
       if (!CommonUtils.isValidPhoneNumber(phonenumber)) {
          errorlist.put("phonenumber", "Not valid number");
       }

    } else {
       errorlist.put("phonenumber", "Missing number");
    }

    if (json.has("role")) {
        String role = json.getString("role");
 
     } else {
        errorlist.put("role", "role");
     }
    

    return errorlist;

 }

 private Users setUser(String userData) {
    Users user = new Users();

    JSONObject json = new JSONObject(userData);

    user.setEmail(json.getString("email"));
    user.setFirstName(json.getString("firstname"));
    user.setPassword(json.getString("password"));
    user.setPhoneNumber(json.getString("phonenumber"));
    user.setUsername(json.getString("username"));
    user.setRole(json.getString("role"));

    if (json.has("age")) {
       user.setAge(json.getInt("age"));

    }
    if (json.has("lastname")) {
       user.setLastName(json.getString("lastname"));

    }
    if (json.has("gender")) {
       user.setGender(json.getString("gender"));

    }


    Status status = repo.findById(1).get();
      user.setStatusId(status);


    return user;

 }

 @PutMapping(value = "/update-user/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody String requestData) {
        JSONObject isRequestValid = validateUserRequest(requestData);
        Users user = null;

        if(isRequestValid.isEmpty()) {
            user = setUser(requestData);
            JSONObject responseObj = service.updateUser(user, userId);
            if(responseObj.has("errorMessage")) {
                return new ResponseEntity<String>(responseObj.toString(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<String>(isRequestValid.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("user updated", HttpStatus.OK);
   }

   @GetMapping("/getusers")
   public ResponseEntity<String> getallUsers(@Nullable @RequestParam String userId) {
      JSONArray userArr = service.getUsers(userId);
      return new ResponseEntity<>(userArr.toString(), HttpStatus.OK);

   }

   @DeleteMapping("/deletebyid/{id}")
   public String deletebyid(@PathVariable int id){
      String s = service.deleteByid(id);
      return s;
   }
}


