package pl.coderslab.admin.service;

import pl.coderslab.admin.model.Admin;
import pl.coderslab.admin.model.dto.AdminDto;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);
}