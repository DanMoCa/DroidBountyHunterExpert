package training.edu.interfaces;

/**
 * Created by Dan14z on 05/09/2017.
 */

public interface OnTaskListener {
    void OnTaskCompleted(String jsonString);

    void OnTaskError(int code, String message, String error);
}
