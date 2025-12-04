package com.jasonthe_dev;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerService {

    private final SoftwareEngineerRepository softwareEngineerRepository;
    private final AiService aiService;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository,
                                   AiService aiService) {
        this.softwareEngineerRepository = softwareEngineerRepository;
        this.aiService = aiService;
    }

    public List<SoftwareEngineer> getAllSoftwareEngineers() {
        return softwareEngineerRepository.findAll();
    }

    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer) {
        String prompt = """
                Based on the programming tech stack that %s that %s has given
                Privide a full leearning path and recommendations for this person.
                """.formatted(
                        softwareEngineer.getTechStack(),
                softwareEngineer.getName()
        );
        String chatRes = aiService.chat(prompt);
        softwareEngineer.setLearningPathRecommendation(chatRes);
        softwareEngineerRepository.save(softwareEngineer);
    }

    public SoftwareEngineer getSoftwareEngineerById(Integer id) {
        return softwareEngineerRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException
                                (id + " not found")
                );
    }

    public void deleteSoftwareEngineerById(Integer id) {
        softwareEngineerRepository.deleteById(id);
    }

    public void updateSoftwareEngineerById(Integer id, SoftwareEngineer softwareEngineer) {
        SoftwareEngineer currentSoftwareEngineer = softwareEngineerRepository.findById(id)
                        .orElseThrow(() -> new IllegalStateException((
                                id + "not found"
                                )));

        // Update Software Engineer Values at Id given
        currentSoftwareEngineer.setName(softwareEngineer.getName());
        currentSoftwareEngineer.setTechStack((softwareEngineer.getTechStack()));

        softwareEngineerRepository.save(currentSoftwareEngineer);
    }
}
