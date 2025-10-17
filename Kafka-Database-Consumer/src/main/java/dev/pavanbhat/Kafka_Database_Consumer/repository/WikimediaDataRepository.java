package dev.pavanbhat.Kafka_Database_Consumer.repository;

import dev.pavanbhat.Kafka_Database_Consumer.entity.WikimediaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikimediaDataRepository extends JpaRepository<WikimediaData, Long> {
}

