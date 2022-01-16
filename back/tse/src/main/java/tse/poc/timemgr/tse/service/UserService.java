package tse.poc.timemgr.tse.service;

import org.springframework.http.ResponseEntity;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.payload.request.CrRequest;
import tse.poc.timemgr.tse.payload.response.MessageResponse;

import java.util.Collection;

public interface UserService {

     Collection<User> findAllUser();

     User findUserById(Long id);

     Collection<User> findAllManagers();

     ResponseEntity<MessageResponse> deleteUser(Long id);

     ResponseEntity<MessageResponse> updateLatestCr(CrRequest crRequest);

}
