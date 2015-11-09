package cvia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
/**
 * Created by andhieka on 10/10/15.
 */
@Embeddable
public class Publication {
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    @Column(name = "conference")
    private String conference;
    @Column(name = "num_pages")
    private String pages;
    @Column(name = "description")
    private String description;

    // Empty Constructor for Hibernate
    public Publication() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
