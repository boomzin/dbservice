/*
 * This file is generated by jOOQ.
 */
package ru.mediatel.icc.dbservice.db.generated.tables.pojos;


import java.util.UUID;

import javax.annotation.processing.Generated;

import ru.mediatel.icc.dbservice.db.generated.tables.interfaces.IUsers;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.19.24"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Users implements IUsers {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String phone;
    private String email;
    private String tg;
    private String description;

    public Users() {}

    public Users(IUsers value) {
        this.id = value.getId();
        this.phone = value.getPhone();
        this.email = value.getEmail();
        this.tg = value.getTg();
        this.description = value.getDescription();
    }

    public Users(
        UUID id,
        String phone,
        String email,
        String tg,
        String description
    ) {
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.tg = tg;
        this.description = description;
    }

    /**
     * Getter for <code>users.id</code>.
     */
    @Override
    public UUID getId() {
        return this.id;
    }

    /**
     * Setter for <code>users.id</code>.
     */
    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Getter for <code>users.phone</code>.
     */
    @Override
    public String getPhone() {
        return this.phone;
    }

    /**
     * Setter for <code>users.phone</code>.
     */
    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for <code>users.email</code>.
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter for <code>users.email</code>.
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for <code>users.tg</code>.
     */
    @Override
    public String getTg() {
        return this.tg;
    }

    /**
     * Setter for <code>users.tg</code>.
     */
    @Override
    public void setTg(String tg) {
        this.tg = tg;
    }

    /**
     * Getter for <code>users.description</code>.
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for <code>users.description</code>.
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Users other = (Users) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.phone == null) {
            if (other.phone != null)
                return false;
        }
        else if (!this.phone.equals(other.phone))
            return false;
        if (this.email == null) {
            if (other.email != null)
                return false;
        }
        else if (!this.email.equals(other.email))
            return false;
        if (this.tg == null) {
            if (other.tg != null)
                return false;
        }
        else if (!this.tg.equals(other.tg))
            return false;
        if (this.description == null) {
            if (other.description != null)
                return false;
        }
        else if (!this.description.equals(other.description))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.phone == null) ? 0 : this.phone.hashCode());
        result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = prime * result + ((this.tg == null) ? 0 : this.tg.hashCode());
        result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Users (");

        sb.append(id);
        sb.append(", ").append(phone);
        sb.append(", ").append(email);
        sb.append(", ").append(tg);
        sb.append(", ").append(description);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IUsers from) {
        setId(from.getId());
        setPhone(from.getPhone());
        setEmail(from.getEmail());
        setTg(from.getTg());
        setDescription(from.getDescription());
    }

    @Override
    public <E extends IUsers> E into(E into) {
        into.from(this);
        return into;
    }
}
