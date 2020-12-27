package com.motoo.server.domain.auth.dto;

import com.motoo.server.domain.model.SnsProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class SnsProfileInfo implements Serializable {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String snsId;
    private String email;
    private String gender;
    private String birthday;
    private String profileImagePath;
    private SnsProvider snsProvider;
}
