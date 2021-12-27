package tse.poc.timemgr.tse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tse.poc.timemgr.tse.dao.ProjectRepository;
import tse.poc.timemgr.tse.dao.TimeRepository;
import tse.poc.timemgr.tse.dao.UserRepository;
import tse.poc.timemgr.tse.domain.Project;
import tse.poc.timemgr.tse.domain.Time;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.payload.request.SingleUserRequest;
import tse.poc.timemgr.tse.payload.request.TimeRequest;
import tse.poc.timemgr.tse.payload.request.UpdateRequest;
import tse.poc.timemgr.tse.payload.response.MessageResponse;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/time")
public class TimeController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(path = "/all", produces ="application/json")
    public List<Time> findAllTimes() {

        return timeRepository.findAll();
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping(path ="/time/{id}")
    public ResponseEntity<?> deleteTime(@PathVariable Long id){

        Optional<Time> timeToDelete = this.timeRepository.findById(id);
        if (!timeToDelete.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Time doesnt exist!"));
        }else{
            timeRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Time deleted successfully!"));
        }
    }


    @GetMapping(path=   "/usertime/{id}", produces ="application/json")
    public Optional<List<Time>>  getUserTimes(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        Optional<List<Time>> listOptional = timeRepository.findByUser(user.get());

        return listOptional;



    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(path ="/add")
    public ResponseEntity<?> addTime(@Valid @RequestBody TimeRequest timeRequest) throws ParseException {
        Optional<User> userToAttach = userRepository.findById(timeRequest.getUser_id());
        Optional<Project> projectToAttach = projectRepository.findById(timeRequest.getProject_id());

        if (!userToAttach.isPresent() || !projectToAttach.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User or Project don't exist!"));
        }else{
            String[] s = timeRequest.getNb_hours().split(", ");
            Date date_travail = new SimpleDateFormat("dd/MM/yyyy").parse(s[0]);

            Date date_saisie =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(timeRequest.getDate_saisie().replace(",", " "));

            String duration = s[1];

            //String[] values = s[1].split(":");
            // get the hours, minutes and seconds value and add it to the duration
            //Duration duration = Duration.ofHours(Integer.parseInt(values[0]));
            //duration = duration.plusMinutes(Integer.parseInt(values[1]));

            Time time = new Time(date_saisie,date_travail, duration , userToAttach.get(), projectToAttach.get());
            timeRepository.save(time);
            return ResponseEntity.ok(new MessageResponse("Time added successfully!"));}
    }
}
