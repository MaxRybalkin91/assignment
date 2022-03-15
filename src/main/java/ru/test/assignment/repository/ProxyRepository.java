package ru.test.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.assignment.model.ProxyType;
import ru.test.assignment.model.entity.Proxy;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProxyRepository extends JpaRepository<Proxy, Long> {

    Proxy save(Proxy item);

    Optional<Proxy> findById(Long id);

    List<Proxy> findAllByNameAndType(String name, ProxyType type);

    void deleteById(Long id);

}
