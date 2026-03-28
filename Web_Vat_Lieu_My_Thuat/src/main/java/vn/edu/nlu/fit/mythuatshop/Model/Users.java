package vn.edu.nlu.fit.mythuatshop.Model;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class Users {
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate dob;
    private String address;
    private String role;
    private LocalDateTime createAt;
    private int isActive;
    private int failedLogin;
    private LocalDateTime lockUser;

    ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public int getIsActive() { return isActive; }
    public void setIsActive(int isActive) { this.isActive = isActive; }

    public LocalDateTime getLockedUntil() {
        return lockUser;
    }

    public void setLockedUntil(LocalDateTime lockedUntil) {
        this.lockUser = lockedUntil;
    }

    public int getFailedLoginAttempts() {
        return failedLogin;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLogin = failedLoginAttempts;
    }
}
