package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.User;
import com.parking.parkinglot.entities.UserGroup;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.List;

@Stateless
public class UsersBean {
    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    PasswordBean passwordBean;

    public List<UserDto> findAllUsers() {
        LOG.info("findAllUsers");
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyCarsToDto(users);
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    public UserDto findById(Long userId) {
        LOG.info("findById");
        try {
            User user = entityManager.find(User.class, userId);
            return new UserDto(
                    user.getId(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getPassword());
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    public List<UserDto> copyCarsToDto(List<User> users) {
        return users.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getEmail(),
                        user.getUsername(),
                        user.getPassword()))
                .toList();
    }

    public void createUser(String username, String email, String password, Collection<String> groups) {
        LOG.info("createUser");
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);
        assignGroupsToUser(username, groups);
    }

    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }
    }

    public Collection<String> findUsernamesByUserIds(Collection<Long> userIds) {
        List<String> usernames =
                entityManager.createQuery("SELECT u.username FROM User u WHERE u.id IN :userIds", String.class)
                        .setParameter("userIds", userIds)
                        .getResultList();
        return usernames;
    }

    public void updateUser(Long userId, String username, String email, String password, Collection<String> groups) {
        LOG.info("updateCar");

        User user = entityManager.find(User.class, userId);
        user.setUsername(username);
        user.setEmail(email);
        if(password != null && !password.isEmpty()) {
            user.setPassword(passwordBean.convertToSha256(password));
        }

        //Get old groups
        List<UserGroup> oldGroups = entityManager.createQuery("SELECT ug FROM UserGroup ug WHERE ug.username = :username", UserGroup.class)
                .setParameter("username", username)
                .getResultList();

        //Remove old groups if not in new groups
        for (UserGroup oldGroup : oldGroups) {
            if (!groups.contains(oldGroup.getUserGroup())) {
                entityManager.remove(oldGroup);
            }
        }

        //Remove oldGroups from groups
        groups.removeIf(group -> oldGroups.stream().anyMatch(oldGroup -> oldGroup.getUserGroup().equals(group)));

        //Assign new groups
        assignGroupsToUser(username, groups);
    }
}
