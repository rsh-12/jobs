package ru.rsh12.resume.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rsh12.api.core.resume.dto.LanguageDto;
import ru.rsh12.api.core.resume.dto.ResumeDto;
import ru.rsh12.api.core.resume.dto.SkillSetDto;
import ru.rsh12.resume.entity.Language;
import ru.rsh12.resume.entity.Resume;
import ru.rsh12.resume.entity.SkillSet;
import ru.rsh12.resume.entity.SpecializationResume;
import ru.rsh12.util.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResumeMapper implements Mapper<Resume, ResumeDto> {

    private final CountryMapper countryMapper;
    private final EducationDetailMapper educationDetailMapper;
    private final ExperienceDetailMapper experienceDetailMapper;

    @Override
    public Resume dtoToEntity(ResumeDto dto) {
        return new Resume(
                dto.desiredJobPosition(),
                dto.description(),
                dto.salary(),
                dto.currency(),
                dto.accountId()
        );
    }

    @Override
    public ResumeDto entityToDto(Resume entity) {
        Set<SkillSetDto> skills = entity.getSkills().stream()
                .map(rs -> {
                    SkillSet skill = rs.getSkill();
                    return new SkillSetDto(skill.getId(), skill.getName(), rs.getLevel());
                })
                .collect(Collectors.toSet());

        Set<LanguageDto> languages = entity.getLanguages().stream()
                .map(rl -> {
                    Language language = rl.getLanguage();
                    return new LanguageDto(language.getId(), language.getName(), rl.getLevel());
                })
                .collect(Collectors.toSet());

        Set<Integer> specializationIds = entity.getSpecializations().stream()
                .map(SpecializationResume::getSpecializationId)
                .collect(Collectors.toSet());

        return new ResumeDto(
                entity.getId(),
                entity.getDescription(),
                entity.getSalary(),
                entity.getCurrency(),
                entity.getDesiredJobPosition(),
                entity.getAccountId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                countryMapper.entitySetToDtoSet(entity.getCitizenship()),
                skills,
                languages,
                educationDetailMapper.entitySetToDtoSet(entity.getEducationDetails()),
                experienceDetailMapper.entitySetToDtoSet(entity.getExperienceDetails()),
                specializationIds
        );
    }
}
