package tse.poc.timemgr.tse.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tse.poc.timemgr.tse.dao.RoleRepository;
import tse.poc.timemgr.tse.dao.UserRepository;
import tse.poc.timemgr.tse.domain.ERole;
import tse.poc.timemgr.tse.domain.Role;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.payload.request.DeleteRequest;
import tse.poc.timemgr.tse.payload.request.LoginRequest;
import tse.poc.timemgr.tse.payload.request.SignupRequest;
import tse.poc.timemgr.tse.payload.response.JwtResponse;
import tse.poc.timemgr.tse.payload.response.MessageResponse;
import tse.poc.timemgr.tse.security.jwt.JwtUtils;
import tse.poc.timemgr.tse.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getNom(),
                userDetails.getPrenom(),
                userDetails.getEmail(),
                roles));
    }

    @DeleteMapping(path ="/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteRequest deleteRequest){
        if (!userRepository.existsByUsername(deleteRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User doesnt exist!"));
        }else {
            Optional<User> user_to_delete = userRepository.findByUsername(deleteRequest.getUsername());
            user_to_delete.ifPresent(user -> {userRepository.deleteById(user.getId());});
            return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));}

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),signUpRequest.getNom(),signUpRequest.getPrenom(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "manager":
                        Role managerRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(managerRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        String manager_response = "";
        if (userRepository.existsByUsername(signUpRequest.getManager())) {
            Optional<User> manager_object = userRepository.findByUsername(signUpRequest.getManager());
            if(manager_object.isPresent()){
                user.setManager(manager_object.get());
            }else{
                /*return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Manager wasnt added"));*/
                manager_response = "Warning : Manager wasn't added";
            }
        } else{
           /* return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Manager doesn't exist!"));*/
            manager_response = "Warning : Manager wasn't added";
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully! - "+manager_response));
    }
}
