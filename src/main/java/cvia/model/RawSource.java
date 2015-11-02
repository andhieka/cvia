package cvia.model;

import javax.persistence.*;

/**
 * Created by Michael Limantara on 11/3/2015.
 */
@Entity
@Table(name = "raw_source")
public class RawSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "raw_content")
    private byte[] rawContent;

    public RawSource() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getRawContent() {
        return rawContent;
    }

    public void setRawContent(byte[] rawContent) {
        this.rawContent = rawContent;
    }
}
