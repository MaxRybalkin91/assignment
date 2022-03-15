package ru.test.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test.assignment.model.ProxyType;
import ru.test.assignment.model.rest.ProxyRq;
import ru.test.assignment.model.rest.ProxyRs;
import ru.test.assignment.model.rest.exception.AssignmentLogicException;
import ru.test.assignment.repository.ProxyRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class ProxyService {
    public static final String NO_ID_MESSAGE = "New entity requires no id";
    public static final String REQUIRED_ID_MESSAGE = "Updating entity requires id";

    @Value("${rest.pageSize}")
    private Integer pageSize;

    private final ProxyRepository proxyRepository;
    private final ProxyRqConverter proxyRqConverter;
    private final ProxyRsConverter proxyRsConverter;

    public void save(ProxyRq request) {
        if (Objects.isNull(request.getId())) {
            proxyRepository.save(requireNonNull(proxyRqConverter.convert(request)));
        } else {
            throw new AssignmentLogicException(NO_ID_MESSAGE);
        }
    }

    @Transactional
    public void update(ProxyRq request) {
        if (Objects.nonNull(request.getId())) {
            proxyRepository.save(requireNonNull(proxyRqConverter.convert(request)));
        } else {
            throw new AssignmentLogicException(REQUIRED_ID_MESSAGE);
        }
    }

    public Page<ProxyRs> findAll() {
        final Pageable pageable = Pageable.ofSize(pageSize);
        final List<ProxyRs> allProxies = proxyRepository.findAll().stream()
                .map(proxyRsConverter::convert)
                .collect(Collectors.toList());
        return new PageImpl<>(allProxies, pageable, allProxies.size());
    }

    public ProxyRs getById(Long id) {
        return proxyRepository.findById(id)
                .map(proxyRsConverter::convert)
                .orElse(null);
    }

    public List<ProxyRs> findAllByNameAndType(String name, ProxyType type) {
        return proxyRepository.findAllByNameAndType(name, type).stream()
                .map(proxyRsConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        proxyRepository.deleteById(id);
    }
}
