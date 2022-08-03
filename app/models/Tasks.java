package models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Tasks extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long systemTaskId;
    private String title;
    private String description;
    private String status;

    private Date dueDate;
    private String dueDateStr;

    private Date creationDate;
    private String creationDateStr;

    @JsonManagedReference
    @OneToMany(mappedBy = "todoTask", fetch = FetchType.EAGER)
    private Set<TodoTaskComments> todoTaskCommentsSet;

    public static Finder<Long, Tasks> find = new Finder<>(Tasks.class);

    public Tasks() {
    }

    public Long getSystemTaskId() {
        return systemTaskId;
    }

    public void setSystemTaskId(Long systemTaskId) {
        this.systemTaskId = systemTaskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDateStr() {
        return dueDateStr;
    }

    public void setDueDateStr(String dueDateStr) {
        this.dueDateStr = dueDateStr;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationDateStr() {
        return creationDateStr;
    }

    public void setCreationDateStr(String creationDateStr) {
        this.creationDateStr = creationDateStr;
    }

    public Set<TodoTaskComments> getTodoTaskCommentsSet() {
        return todoTaskCommentsSet;
    }

    public void setTodoTaskCommentsSet(Set<TodoTaskComments> todoTaskCommentsSet) {
        this.todoTaskCommentsSet = todoTaskCommentsSet;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "systemTaskId=" + systemTaskId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", dueDate=" + dueDate +
                ", dueDateStr='" + dueDateStr + '\'' +
                ", creationDate=" + creationDate +
                ", creationDateStr='" + creationDateStr + '\'' +
                ", todoTaskCommentsSet=" + todoTaskCommentsSet +
                '}';
    }
}
