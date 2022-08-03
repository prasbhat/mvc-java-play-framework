package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TodoTaskComments extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long todoTaskCommentsId;

    private String taskComments;
    private Date creationDate;
    private String creationDateStr;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="systemTasksId", nullable=false)
    private Tasks todoTask;

    public static Finder<Long, TodoTaskComments> find = new Finder<>(TodoTaskComments.class);

    public TodoTaskComments() {
    }

    public Long getTodoTaskCommentsId() {
        return todoTaskCommentsId;
    }

    public void setTodoTaskCommentsId(Long todoTaskCommentsId) {
        this.todoTaskCommentsId = todoTaskCommentsId;
    }

    public String getTaskComments() {
        return taskComments;
    }

    public void setTaskComments(String taskComments) {
        this.taskComments = taskComments;
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

    public Tasks getTodoTask() {
        return todoTask;
    }

    public void setTodoTask(Tasks todoTask) {
        this.todoTask = todoTask;
    }

    @Override
    public String toString() {
        return "TodoTaskComments{" +
                "todoTaskCommentsId=" + todoTaskCommentsId +
                ", taskComments='" + taskComments + '\'' +
                ", creationDate=" + creationDate +
                ", creationDateStr='" + creationDateStr + '\'' +
                '}';
    }
}
