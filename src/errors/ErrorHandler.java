package errors;

import resultwrapper.ResultWrapper;

public class ErrorHandler {

    public static <T> ResultWrapper<T> success(T data) {
        return new ResultWrapper<>(data, ObjectErrors.NO_ERROR);
    }

    public static<T> ResultWrapper<T> success() {
        return new ResultWrapper<>(null, ObjectErrors.NO_ERROR);
    }

    public static<T> ResultWrapper<T> failed(ObjectErrors error) {
        return new ResultWrapper<>(null, error);
    }

}
