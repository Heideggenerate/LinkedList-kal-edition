package resultwrapper;

import errors.ObjectErrors;

public class ResultWrapper<T> {
    T data;
    ObjectErrors error;

    public ResultWrapper(T data, ObjectErrors error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public ObjectErrors getError() {
        return error;
    }
}
