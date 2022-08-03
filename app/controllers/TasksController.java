package controllers;

import models.Tasks;
import models.TodoTaskComments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.i18n.MessagesApi;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import scala.Tuple2;
import scala.collection.immutable.Seq;
import services.TasksService;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.Set;

public class TasksController extends Controller {

    //Variable declarations
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);
    private final String className = this.getClass().getSimpleName();
    private final String  LOGGER_ENTRY = "Entry [{0}::{1}]";
    private final String  LOGGER_EXIT = "Exit [{0}::{1}]";
    private final String  LOGGER_IN = "In [{0}::{1}]";

    private TasksService tasksService = new TasksService();

    @Inject
    private FormFactory formFactory;

    @Inject
    private MessagesApi messagesApi;
    public Result getAllTasks(){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_IN, className, methodName));
        return ok(views.html.tasks.home.render(tasksService.getAllTasks()));
    }

    public Result view(long id){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        Tasks task = tasksService.getTaskById(id);
        LOGGER.debug("In Controller.view="+task.toString());
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return ok(views.html.tasks.view.render(task));
    }

    public Result getCreateForm(Http.Request request){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        Form<Tasks> tasksForm = formFactory.form(Tasks.class);
        Seq<Tuple2<String, String>> statusListSeq = getTodoStatusAsList();
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return ok(views.html.tasks.create.render(tasksForm, statusListSeq, messagesApi.preferred(request)));
    }

    public Result createTask(Http.Request request){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        Form<Tasks> tasksForm = formFactory.form(Tasks.class).bindFromRequest(request);
        Tasks task = tasksForm.get();
        LOGGER.debug("In Controller.createTask="+task.toString());
        tasksService.createTask(task);
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return redirect(routes.TasksController.getAllTasks());
    }

    public Result edit(long id, Http.Request request){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));

        Tasks task =tasksService.getTaskById(id);
        Form<Tasks> tasksForm = formFactory.form(Tasks.class).fill(task);

        Set<TodoTaskComments> todoTaskCommentsSet = task.getTodoTaskCommentsSet();
        Form<TodoTaskComments> todoTaskCommentsForm = formFactory.form(TodoTaskComments.class).bindFromRequest(request);

        Seq<Tuple2<String, String>> statusListSeq = getTodoStatusAsList();
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return ok(views.html.tasks.edit.render(tasksForm, statusListSeq, todoTaskCommentsSet,
                todoTaskCommentsForm, messagesApi.preferred(request)));
    }

    public Result updateTask(Http.Request request){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));

        Form<Tasks> tasksForm = formFactory.form(Tasks.class).bindFromRequest(request);
        Tasks task = tasksForm.get();

        Form<TodoTaskComments> todoTaskCommentsForm = formFactory.form(TodoTaskComments.class).bindFromRequest(request);
        TodoTaskComments todoTaskComments = todoTaskCommentsForm.get();

        tasksService.updateTask(task, todoTaskComments);
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return redirect(routes.TasksController.getAllTasks());
    }

    public Result deleteTask(long id){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        tasksService.deleteTask(id);
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return getAllTasks();
    }

    private Seq<Tuple2<String, String>> getTodoStatusAsList() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_IN, className, methodName));

        return tasksService.getTodoStatusAsList();
    }
}
