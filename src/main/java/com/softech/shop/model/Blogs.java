/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nguyen Tri
 */
@Entity
@Table(name = "Blogs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Blogs.findAll", query = "SELECT b FROM Blogs b")
    , @NamedQuery(name = "Blogs.findByBlogId", query = "SELECT b FROM Blogs b WHERE b.blogId = :blogId")
    , @NamedQuery(name = "Blogs.findByTitle", query = "SELECT b FROM Blogs b WHERE b.title = :title")
    , @NamedQuery(name = "Blogs.findByContent", query = "SELECT b FROM Blogs b WHERE b.content = :content")
    , @NamedQuery(name = "Blogs.findByImage", query = "SELECT b FROM Blogs b WHERE b.image = :image")
    , @NamedQuery(name = "Blogs.findByDescription", query = "SELECT b FROM Blogs b WHERE b.description = :description")
    , @NamedQuery(name = "Blogs.findByView", query = "SELECT b FROM Blogs b WHERE b.view = :view")
    , @NamedQuery(name = "Blogs.findByNew1", query = "SELECT b FROM Blogs b WHERE b.new1 = :new1")
    , @NamedQuery(name = "Blogs.findByHot", query = "SELECT b FROM Blogs b WHERE b.hot = :hot")})
public class Blogs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @NotNull
    @Column(name = "BlogId")
    private Integer blogId;
    @Size(max = 50)
    @Column(name = "Title")
    private String title;
    @Size(max = 1073741823)
    @Column(name = "Content")
    private String content;
    @Size(max = 100)
    @Column(name = "Image")
    private String image;
    @Size(max = 1073741823)
    @Column(name = "Description")
    private String description;
    @Column(name = "`View`")
    private Integer view;
    @Column(name = "New")
    @Temporal(TemporalType.TIMESTAMP)
    private Date new1;
    @Column(name = "Hot")
    private Integer hot;

    public Blogs() {
    }

    public Blogs(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Date getNew1() {
        return new1;
    }

    public void setNew1(Date new1) {
        this.new1 = new1;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blogId != null ? blogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Blogs)) {
            return false;
        }
        Blogs other = (Blogs) object;
        if ((this.blogId == null && other.blogId != null) || (this.blogId != null && !this.blogId.equals(other.blogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softech.shop.model.Blogs[ blogId=" + blogId + " ]";
    }
    
}
