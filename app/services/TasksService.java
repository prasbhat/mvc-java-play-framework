package services;

import models.Tasks;

import models.TodoTaskComments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;
import scala.collection.immutable.Seq;
import scala.jdk.javaapi.CollectionConverters;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

import java.util.stream.Stream;

public class TasksService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksService.class);
    private final String className = this.getClass().getSimpleName();
    private final String  LOGGER_ENTRY = "Entry [{0}::{1}]";
    private final String  LOGGER_EXIT = "Exit [{0}::{1}]";
    private final String  LOGGER_IN = "In [{0}::{1}]";
    enum TASK_STATUS {NOT_STARTED, IN_PROGRESS, ON_HOLD, COMPLETED, DEFERRED}

    private Map<Long, Tasks> tasksMap = new HashMap<>();

    public void createTask(Tasks task){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        /*Long id = (long) tasksMap.size();
        task.setSystemTaskId(id);*/
        task.setDueDateStr(dateFormatter(task.getDueDate()));
        task.setCreationDate(new Date());
        task.setCreationDateStr(dateFormatter(task.getCreationDate()));
//        tasksMap.put(id,task);

        task.save();
        LOGGER.debug("In Service.createTask="+task);
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
    }
    public List<Tasks> getAllTasks(){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_IN, className, methodName));
        return Tasks.find.all();
//        return new ArrayList<>(tasksMap.values());
    }

    private String dateFormatter(Date originalDate){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        String formattedDate;
        try {
            DateFormat originalFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US);
            DateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            formattedDate = targetFormat.format(originalFormat.parse(originalDate.toString()));
        } catch (ParseException e) {
            LOGGER.error(e.toString());
            throw new RuntimeException(e);
        }
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return formattedDate;
    }

    public Tasks getTaskById(long id){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_IN, className, methodName));

//        return tasksMap.get(id);
        return Tasks.find.byId(id);
    }

    public void updateTask (Tasks task, TodoTaskComments todoTaskComments) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        if(todoTaskComments.getTaskComments() != null && !todoTaskComments.getTaskComments().isEmpty()) {
            todoTaskComments.setCreationDate(new Date());
            todoTaskComments.setCreationDateStr(dateFormatter(todoTaskComments.getCreationDate()));
            todoTaskComments.setTodoTask(task);
            todoTaskComments.save();

            Set<TodoTaskComments> todoTaskCommentsSet = task.getTodoTaskCommentsSet();
            todoTaskCommentsSet.add(todoTaskComments);
            task.setTodoTaskCommentsSet(todoTaskCommentsSet);
        }
        task.update();
        LOGGER.debug("In Service.updateTask=" + task);

        /*Tasks oldTask = tasksMap.get(systemTaskId);
//        Tasks oldTask = Tasks.find.byId(task.getSystemTaskId());
        if(null != oldTask) {
            oldTask.setTitle(task.getTitle());
            oldTask.setDescription(task.getDescription());
            oldTask.setDueDate(task.getDueDate());
            oldTask.setDueDateStr(dateFormatter(task.getDueDate()));
            oldTask.setCreationDate(task.getCreationDate());
            oldTask.setCreationDateStr(dateFormatter(task.getCreationDate()));
            oldTask.setStatus(task.getStatus());

            *//*TodoTaskComments todoTaskComments = new TodoTaskComments();
            todoTaskComments.setTaskComments("Test Comments"); *//*
            if(todoTaskComments.getTaskComments() != null && !todoTaskComments.getTaskComments().isEmpty()) {
                todoTaskComments.setCreationDate(new Date());
                todoTaskComments.setCreationDateStr(dateFormatter(todoTaskComments.getCreationDate()));
                todoTaskComments.setTodoTask(oldTask);
                todoTaskComments.save();

                Set<TodoTaskComments> todoTaskCommentsSet = oldTask.getTodoTaskCommentsSet();
                todoTaskCommentsSet.add(todoTaskComments);
                oldTask.setTodoTaskCommentsSet(todoTaskCommentsSet);
            }

            oldTask.save();
            System.out.println("In Service.updateTask=" + oldTask);
        }*/
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
    }

    public void deleteTask(long systemTaskId){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        Tasks task = Tasks.find.byId(systemTaskId);
        System.out.println("In Service.deleteTask=" + task);
        if(null != task) {
            Set<TodoTaskComments> todoTaskCommentsSet = task.getTodoTaskCommentsSet();
            if(todoTaskCommentsSet.size() > 0){
                for(TodoTaskComments todoTaskComments: todoTaskCommentsSet){
                    todoTaskComments.delete();
                }
            }
            task.delete();
        }

//        tasksMap.remove(systemTaskId);
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
    }

    public Seq<Tuple2<String, String>> getTodoStatusAsList() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        List<String> statusList = Stream.of(TASK_STATUS.values()).map(TASK_STATUS::name).toList();
        Map<String, String> statusListMap = new HashMap<>();
        for (String status: statusList){
            statusListMap.put(status,status);
        }
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return CollectionConverters.asScala(statusListMap).toSeq();
    }
}
