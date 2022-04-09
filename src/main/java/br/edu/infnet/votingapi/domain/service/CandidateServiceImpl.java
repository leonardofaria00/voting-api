package br.edu.infnet.votingapi.domain.service;

import br.edu.infnet.votingapi.domain.data.model.candidate.Candidate;
import br.edu.infnet.votingapi.domain.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final Validator validator;

    public CandidateServiceImpl(final CandidateRepository candidateRepository, final Validator validator) {
        this.candidateRepository = candidateRepository;
        this.validator = validator;
    }

    @Override
    public List<Candidate> getCandidates() throws Exception {
        return candidateRepository.getCandidates();
    }

    @Override
    public Candidate createCandidate(final Candidate candidate) {
        validateMandatoryFields(candidate);
        return candidateRepository.createCandidate(candidate);
    }

    @Override
    public Candidate changeCandidate(final Candidate candidate, final String uuid) {
        validateMandatoryFields(candidate);
        return candidateRepository.changeCandidate(candidate, uuid);
    }

    private void validateMandatoryFields(final Object object) {
        final Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
