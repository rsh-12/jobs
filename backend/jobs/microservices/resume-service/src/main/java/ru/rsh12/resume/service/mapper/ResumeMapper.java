package ru.rsh12.resume.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rsh12.api.core.resume.dto.ResumeLanguageDto;
import ru.rsh12.api.core.resume.dto.ResumeDto;
import ru.rsh12.api.core.resume.dto.ResumeSkillSetDto;
import ru.rsh12.resume.entity.Language;
import ru.rsh12.resume.entity.Resume;
import ru.rsh12.resume.entity.SkillSet;
import ru.rsh12.resume.entity.SpecializationResume;
import ru.rsh12.util.ServiceUtil;
import ru.rsh12.util.mapper.Mapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResumeMapper implements Mapper<Resume, ResumeDto> {

    private final CountryMapper countryMapper;
    private final EducationDetailMapper educationDetailMapper;
    private final ExperienceDetailMapper experienceDetailMapper;

    private final ServiceUtil serviceUtil;

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
        List<ResumeSkillSetDto> skills = entity.getSkills().stream()
                .map(rs -> {
                    SkillSet skill = rs.getSkill();
                    return new ResumeSkillSetDto(skill.getId(), skill.getName(), rs.getLevel());
                }).toList();

        List<ResumeLanguageDto> languages = entity.getLanguages().stream()
                .map(rl -> {
                    Language language = rl.getLanguage();
                    return new ResumeLanguageDto(language.getId(), language.getName(), rl.getLevel());
                }).toList();

        List<Integer> specializationIds = entity.getSpecializations().stream()
                .map(SpecializationResume::getSpecializationId)
                .toList();

        return new ResumeDto(
                entity.getId(),
                entity.getDescription(),
                entity.getSalary(),
                entity.getCurrency(),
                entity.getDesiredJobPosition(),
                entity.getAccountId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                countryMapper.entityListToDtoList(entity.getCitizenship()),
                skills,
                languages,
                educationDetailMapper.entityListToDtoList(entity.getEducationDetails()),
                experienceDetailMapper.entityListToDtoList(entity.getExperienceDetails()),
                specializationIds,
                serviceUtil.getServiceAddress()
        );
    }
}
