package dtos;

import java.util.ArrayList;
import java.util.List;

public class ClientDTO {

    private String name;
    private String email;
    private String contact;
    private String address;
    private List<ProjectDTO> projects;

    public ClientDTO() {
        this.projects = new ArrayList<>();
    }

    public ClientDTO(String name, String email, String contact, String address) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.projects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
