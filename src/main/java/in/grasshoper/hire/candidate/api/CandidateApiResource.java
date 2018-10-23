package in.grasshoper.hire.candidate.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.grasshoper.hire.candidate.service.CandidateReadService;
import in.grasshoper.hire.candidate.service.CandidateWriteService;

@RestController  
@RequestMapping("/hire/candidate") 
public class CandidateApiResource {
  private final CandidateReadService candidateReadService;
  private final CandidateWriteService candidateWriteService;
  @Autowired
  public CandidateApiResource(CandidateReadService candidateReadService, CandidateWriteService candidateWriteService) {
    super();
    this.candidateReadService = candidateReadService;
    this.candidateWriteService = candidateWriteService;
  }
  
  
}
