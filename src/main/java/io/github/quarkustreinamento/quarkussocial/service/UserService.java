package io.github.quarkustreinamento.quarkussocial.service;

import io.github.quarkustreinamento.quarkussocial.exceptions.UserNotFoundException;
import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;


@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository repository;

    public User save(User user){
        repository.persist(user);
        return user;
    }

    public List<User> findAll(){
        PanacheQuery<User> userPanache = repository.findAll();
        return userPanache.list();
    }

    public User findById(Long id){
        User user = repository.findById(id);

        if(Objects.isNull(user)){
            throw new UserNotFoundException(String.format(
                    "Não encontrado user com o id %s", id));
        }
        return user;
    }

    public void atualizar(User user){
        User userPersist = this.findById(user.getId());
        userPersist.setName(user.getName());
        userPersist.setAge(user.getAge());
        repository.persist(userPersist);
    }

    public void deleteUser(Long id){
        User user = repository.findById(id);

        if(Objects.isNull(user)){
            throw new UserNotFoundException(String.format(
                    "Não encontrado user com o id %s", id));
        }

        repository.delete(user);
    }
}
