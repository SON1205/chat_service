package com.yeahpeu.backend.auth.v2.repository;

import com.yeahpeu.backend.auth.v2.domain.DeviceType;
import com.yeahpeu.backend.auth.v2.domain.RefreshToken;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByMemberIdAndDeviceType(Long memberId, DeviceType deviceType);

    List<RefreshToken> findByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);

    void deleteByMemberIdAndDeviceType(Long memberId, DeviceType deviceType);
}
