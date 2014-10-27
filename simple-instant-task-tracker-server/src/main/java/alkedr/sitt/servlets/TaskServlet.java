package alkedr.sitt.servlets;

import alkedr.sitt.servlets.utils.InputValidationException;
import alkedr.sitt.servlets.utils.UnauthorizedException;
import alkedr.sitt.storage.DatabaseConnection;
import alkedr.sitt.storage.things.Task;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static alkedr.sitt.servlets.utils.ParameterValidationUtils.*;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class TaskServlet extends HttpServlet {
    /**
     * Добавление таски. Параметры:
     *   - id: id таски, если указан, то изменяется существующая таска, если нет - то создаётся новая
     *   - name: название, обязательно при создании таски
     *   - tags: список тегов
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = getOptionalLongParameter(req, "id");
            String name = getNonEmptyStringParameter(req, "name");
            List<String> tags = getListOfStringParameters(req, "tags");
            String userId = getRequiredCurrentUserId();
            try (DatabaseConnection db = new DatabaseConnection()) {
                if (id == null) {
                    db.insertTask(userId, name);
                } else {
                    Task task = db.getTask(id, userId);
                    if (task == null) {
                        throw new InputValidationException("task not found");
                    }
                    task.setName(name);
                    task.setTags(tags);
                }
            }
        } catch (InputValidationException e) {
            resp.sendError(SC_BAD_REQUEST, e.getMessage());
        } catch (UnauthorizedException ignored) {
            resp.sendError(SC_FORBIDDEN);
        }
    }


    /**
     * Удаление таски. Параметры:
     *   - id: id таски (обязательно)
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = getRequiredLongParameter(req, "id");
            try (DatabaseConnection db = new DatabaseConnection()) {
                db.deleteTask(id);
            }
        } catch (InputValidationException e) {
            resp.sendError(SC_BAD_REQUEST, e.getMessage());
        }
    }
}
