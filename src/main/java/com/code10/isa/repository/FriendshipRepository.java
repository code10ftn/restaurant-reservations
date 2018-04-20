package com.code10.isa.repository;

import com.code10.isa.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Optional<Friendship> findById(long id);

    Optional<Friendship> findBySenderIdAndReceiverId(long senderId, long receiverId);

    List<Friendship> findBySenderIdOrReceiverId(long senderId, long receiverId);
}
