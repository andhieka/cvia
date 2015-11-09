package cvia.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnkevin on 8/11/15.
 */
@Entity
@Table(name = "work_requirement")
public class WorkRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "keyword", joinColumns = {@JoinColumn(name = "work_req_id")})
    private List<String> keywords;

    @Column(name = "duration")
    private int duration;

    public WorkRequirement() {
        this.keywords = new ArrayList<String>();
        this.duration = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
}
