package liar.gamemvcservice.game.repository;

import liar.gamemvcservice.game.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findTopicById(Long id);

    @Query("select t from Topic t where t.id in :ids")
    List<Topic> findTopicsByIds(@Param("ids") List<Long> ids);

    List<Topic> findByIdIn(List<Long> ids);
}
