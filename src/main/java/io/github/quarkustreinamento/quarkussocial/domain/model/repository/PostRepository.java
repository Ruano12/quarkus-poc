package io.github.quarkustreinamento.quarkussocial.domain.model.repository;

import io.github.quarkustreinamento.quarkussocial.domain.model.PostUser;
import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;

import java.util.List;

@ApplicationScoped
public class PostRepository implements PanacheRepository<PostUser>  {

    public List<PostUser> findByUserId(Long userId){
        PanacheQuery panache =  this.find("user.id", userId);
        return panache.list();
    }
}
