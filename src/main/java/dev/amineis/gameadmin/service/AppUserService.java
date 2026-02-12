package dev.amineis.gameadmin.service;

import dev.amineis.gameadmin.dto.request.AppUserCreateRequest;
import dev.amineis.gameadmin.dto.request.AppUserUpdateRequest;
import dev.amineis.gameadmin.dto.response.AppUserResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.entity.AppUser;
import dev.amineis.gameadmin.enums.Role;
import dev.amineis.gameadmin.exception.BusinessRuleException;
import dev.amineis.gameadmin.exception.ResourceNotFoundException;
import dev.amineis.gameadmin.mapper.AppUserMapper;
import dev.amineis.gameadmin.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    @Transactional(readOnly = true)
    public PagedResponse<AppUserResponse> getAllAppUsers(Pageable pageable) {
        return toPagedResponse(appUserRepository.findAll(pageable));
    }

    @Transactional(readOnly = true)
    public AppUserResponse getAppUserById(Long id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AppUser", id));
        return appUserMapper.toResponse(appUser);
    }

    @Transactional(readOnly = true)
    public PagedResponse<AppUserResponse> getAppUsersByRole(Role role, Pageable pageable) {
        return toPagedResponse(appUserRepository.findByRole(role, pageable));
    }

    @Transactional
    public AppUserResponse createAppUser(AppUserCreateRequest request) {
        if (appUserRepository.existsByUsername(request.getUsername())) {
            throw new BusinessRuleException("Username is already in use");
        }
        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Email is already in use");
        }

        AppUser appUser = AppUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(request.getPasswordHash())
                .role(request.getRole())
                .build();
        AppUser saved = appUserRepository.save(appUser);
        return appUserMapper.toResponse(saved);
    }

    @Transactional
    public AppUserResponse updateAppUser(Long id, AppUserUpdateRequest request) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AppUser", id));

        if (request.getUsername() != null && !request.getUsername().equals(appUser.getUsername())) {
            if (appUserRepository.existsByUsername(request.getUsername())) {
                throw new BusinessRuleException("Username is already in use");
            }
            appUser.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().equals(appUser.getEmail())) {
            if (appUserRepository.existsByEmail(request.getEmail())) {
                throw new BusinessRuleException("Email is already in use");
            }
            appUser.setEmail(request.getEmail());
        }

        if (request.getPasswordHash() != null) {
            appUser.setPasswordHash(request.getPasswordHash());
        }
        if (request.getRole() != null) {
            appUser.setRole(request.getRole());
        }

        AppUser updated = appUserRepository.save(appUser);
        return appUserMapper.toResponse(updated);
    }

    @Transactional
    public void deleteAppUser(Long id) {
        if (!appUserRepository.existsById(id)) {
            throw new ResourceNotFoundException("AppUser", id);
        }
        appUserRepository.deleteById(id);
    }

    private PagedResponse<AppUserResponse> toPagedResponse(Page<AppUser> page) {
        return PagedResponse.<AppUserResponse>builder()
                .content(page.getContent().stream().map(appUserMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
