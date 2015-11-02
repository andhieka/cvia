package cvia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;
/**
 * Created by andhieka on 10/10/15.
 */
@Embeddable
public class WorkExperience {
    @Column(name = "position")
    private String position;

    @Column(name = "company")
    private String company;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "description")
    private String description;

    // Empty Constructor for Hibernate
    public WorkExperience() { }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
