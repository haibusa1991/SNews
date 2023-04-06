package com.snews.server.controllers;

import com.snews.server.dto.ServerConfigurationModelDto;
import com.snews.server.dto.UpdateSettingDto;
import com.snews.server.exceptions.MalformedDataException;
import com.snews.server.services.configuration.ConfigurationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/configuration")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping(path = "/modify-setting")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<String> modifySetting(@RequestBody UpdateSettingDto dto) throws MalformedDataException {
        this.configurationService.modifySetting(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping(path = "/set-state")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<String> modifyState(@RequestBody @Valid ServerConfigurationModelDto dto,
                                              BindingResult bindingResult) throws MalformedDataException {
        if (bindingResult.hasErrors()) {
            throw new MalformedDataException("Configuration is invalid.");
        }

        this.configurationService.setState(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/get-state")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR')")
    public ResponseEntity<ServerConfigurationModelDto> getState() {
        return new ResponseEntity<>(this.configurationService.getState(), HttpStatus.OK);
    }

    @GetMapping(path = "/can-register")
    public ResponseEntity<Boolean> canRegister() {
        return new ResponseEntity<>(this.configurationService.isRegistrationEnabled(), HttpStatus.OK);
    }
}
