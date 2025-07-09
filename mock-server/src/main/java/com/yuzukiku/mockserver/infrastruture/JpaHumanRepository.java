package com.yuzukiku.mockserver.infrastruture;

import com.yuzukiku.mockserver.domain.entity.Human;
import com.yuzukiku.mockserver.domain.repository.HumanRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaHumanRepository extends HumanRepository, JpaRepository<Human, UUID> {
}
