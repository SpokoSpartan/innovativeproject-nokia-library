package com.nokia.library.nokiainnovativeproject.services;


import com.nokia.library.nokiainnovativeproject.DTOs.UserDTO;
import com.nokia.library.nokiainnovativeproject.entities.Address;
import com.nokia.library.nokiainnovativeproject.entities.Role;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.exceptions.ValidationException;
import com.nokia.library.nokiainnovativeproject.repositories.AddressRepository;
import com.nokia.library.nokiainnovativeproject.repositories.RoleRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("Sorry we can't find user with email: " + email);
        }
        Hibernate.initialize(user.getRoles());
        return buildUserForAuthentication(user, buildUserAuthority(user.getRoles()));
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Role role: roles){
            authorities.add( new SimpleGrantedAuthority(role.getRole()));
        }
        return new ArrayList<>(authorities);
    }

    public User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User loggedInUser =
                    (org.springframework.security.core.userdetails.User) principal;
            return userRepository.findUserByEmail(loggedInUser.getUsername());
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<User> securedUsers = new ArrayList<>();
        for(User user : users) {
            Hibernate.initialize(user.getBooks());
            Hibernate.initialize(user.getAddress());
            Hibernate.initialize(user.getRoles());
            securedUsers.add(detachAndSecure(user));
        }
        return securedUsers;
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user"));
        Hibernate.initialize(user.getBooks());
        Hibernate.initialize(user.getAddress());
        Hibernate.initialize(user.getRoles());
        return detachAndSecure(user);
    }

    public User createUser(UserDTO userDTO) {
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByRole("ROLE_EMPLOYEE")));
        user = userRepository.saveAndFlush(persistingRequiredEntities(user, userDTO));
        user = detachAndSecure(user);
        user.setRoles(new ArrayList<>());
        return user;
    }

    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user"));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user = userRepository.saveAndFlush(persistingRequiredEntities(user, userDTO));
        user = detachAndSecure(user);
        user.setRoles(new ArrayList<>());
        return user;
    }

    public User assignAdminRoleToUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("user"));
        if(user.getAddress() == null) {
            throw new ValidationException("User has no address. First, assign the address to the user.");
        }
        List<Role> userRoles = user.getRoles();
        for(Role role : userRoles) {
            if(role.getRole().equals("ROLE_ADMIN"))
                return user;
        }
        Role role = roleRepository.findByRole("ROLE_ADMIN");
        userRoles.add(role);
        user.setRoles(userRoles);
        user = userRepository.saveAndFlush(user);
        return detachAndSecure(user);
    }

    public User takeAdminRoleFromUser(Long id) {
        if(userRepository.countUserByRole("ROLE_ADMIN") <= 1) {
            throw new ValidationException("You can't delete the last admin from the database");
        }
        User user = userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("user"));
        Hibernate.initialize(user.getBooks());
        Hibernate.initialize(user.getRoles());
        Hibernate.initialize(user.getAddress());
        List<Role> userRoles = user.getRoles();
        entityManager.detach(user);
        List<Role> actualRoles = new ArrayList<>();
        for(Role role : userRoles) {
            if(!(role.getRole().equals("ROLE_ADMIN")))
                actualRoles.add(role);
        }
        user.setRoles(actualRoles);
        user = userRepository.saveAndFlush(user);
        return detachAndSecure(user);
    }

    public void deleteUser(Long id)
            throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user"));
        userRepository.delete(user);
    }

    private User detachAndSecure(User user) {
        if (user != null) {
            entityManager.detach(user);
            user.setBooks(new ArrayList<>());
            user.setPassword("[UNAUTHORIZED]");
        }
        return user;
    }

    private User persistingRequiredEntities(User user, UserDTO userDTO) {
        Address address = userDTO.getAddress();
        if(address.getId() != null) {
            address = addressRepository.findById(address.getId()).orElseThrow(()-> new ResourceNotFoundException("address"));
            user.setAddress(address);
            return user;
        } else {
            user.setAddress(userDTO.getAddress());
        }
        return user;
    }
}