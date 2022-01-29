package com.telran.dao.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserOrderEntity {
    private final UUID id;
    private final LocalDateTime date;
    private final List<MenuItemEntity> menuItems;
    private UserOrderStatus status; //keyword final deleted

    public UserOrderEntity(UUID id, LocalDateTime date, List<MenuItemEntity> menuItems, UserOrderStatus status) {
        this.id = id;
        this.date = date;
        this.menuItems = menuItems;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<MenuItemEntity> getMenuItems() {
        return menuItems;
    }

    public UserOrderStatus getStatus() {
        return status;
    }

    public void changeStatus(UserOrderStatus status) { //new method added
        if (Objects.requireNonNull(status).ordinal() < this.getStatus().ordinal())
            throw new UnsupportedOperationException("Status cannot be rolled back.");
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOrderEntity that = (UserOrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserOrderEntity{" +
                "id=" + id +
                ", date=" + date +
                ", menuItems=" + menuItems +
                ", status=" + status +
                '}';
    }
}
