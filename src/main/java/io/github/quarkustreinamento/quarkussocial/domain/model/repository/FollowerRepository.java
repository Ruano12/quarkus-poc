package io.github.quarkustreinamento.quarkussocial.domain.model.repository;

import io.github.quarkustreinamento.quarkussocial.domain.model.Follower;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

    public boolean isFollow(Long followerId, Long userId){
        var params = Parameters.with("followerId", followerId).and("userId", userId).map();
        PanacheQuery<Follower> query =
                find("follower.id = :followerId and user.id = :userId", params);
        return query.firstResultOptional().isPresent();

    }

    public List<Follower> findByUser(Long userId){
        PanacheQuery<Follower> query =
                find("user.id", userId);

        return query.list();
    }

    public void deleteByUserAndFollower(Long followerId, Long userId){
        var params = Parameters.with("followerId", followerId).and("userId", userId).map();
        delete("follower.id = :followerId and user.id = :userId", params);

    }
}
