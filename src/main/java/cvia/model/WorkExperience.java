package cvia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.*;
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
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getWorkDuration() {
        try {
            return (endDate.getYear() - startDate.getYear()) * 12 + endDate.getMonthValue() - startDate.getMonthValue() + 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public void setStartDate(LocalDate startDate) {
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
