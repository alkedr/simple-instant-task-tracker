package alkedr.sitt.servlets.utils;

import com.google.appengine.api.users.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.google.appengine.api.users.UserServiceFactory.getUserService;
import static java.util.Arrays.asList;

public final class ParameterValidationUtils {


    @Nullable
    public static String getOptionalStringParameter(ServletRequest req, String name) {
        return req.getParameter(name);
    }

    @NotNull
    public static String getRequiredStringParameter(ServletRequest req, String name) throws InputValidationException {
        String value = getOptionalStringParameter(req, name);
        if (value == null) {
            throw new InputValidationException("Parameter " + name + " is missing");
        }
        return value;
    }

    @Nullable
    public static String getOptionalNonEmptyStringParameter(ServletRequest req, String name) throws InputValidationException {
        String value = getRequiredStringParameter(req, name);
        if (value.isEmpty()) {
            throw new InputValidationException("Parameter " + name + " is empty");
        }
        return value;
    }

    @NotNull
    public static String getNonEmptyStringParameter(ServletRequest req, String name) throws InputValidationException {
        String value = getRequiredStringParameter(req, name);
        if (value.isEmpty()) {
            throw new InputValidationException("Parameter " + name + " is empty");
        }
        return value;
    }


    @Nullable
    public static Long getOptionalLongParameter(ServletRequest req, String name) throws InputValidationException {
        try {
            return req.getParameter(name) == null ? null : Long.valueOf(req.getParameter(name));
        } catch (NumberFormatException e) {
            throw new InputValidationException("Parameter " + name + " is not a number: " + e.getMessage());
        }
    }

    public static long getRequiredLongParameter(ServletRequest req, String name) throws InputValidationException {
        Long value = getOptionalLongParameter(req, name);
        if (value == null) {
            throw new InputValidationException("Parameter " + name + " is missing");
        }
        return value;
    }


    @NotNull
    public static List<String> getListOfStringParameters(ServletRequest req, String name) throws InputValidationException {
        String[] values = req.getParameterValues(name);
        return values == null ? new ArrayList<String>() : asList(values);
    }


    @NotNull
    public static String getRequiredCurrentUserId() throws UnauthorizedException {
        User user = getUserService().getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException();
        }
        return user.getUserId();
    }


}
