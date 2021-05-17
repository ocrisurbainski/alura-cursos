package br.com.alura.leilao.asynctask;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * @author Cristian Urbainski
 * @since 1.0 (20/09/20)
 */
public class BaseAsyncTask<T> extends AsyncTask<Void, Void, T> {

    private final ActionListener<T> actionListener;
    private ResultListener<T> resultListener;

    public BaseAsyncTask(@NonNull ActionListener<T> actionListener, ResultListener<T> resultListener) {

        this.actionListener = actionListener;
        this.resultListener = resultListener;
    }

    @Override
    protected T doInBackground(Void... voids) {

        return actionListener.execute();
    }

    @Override
    protected void onPostExecute(T t) {

        super.onPostExecute(t);

        if (resultListener != null) {

            resultListener.result(t);
        }
    }

    public interface ActionListener<T> {

        T execute();

    }

    public interface ResultListener<T> {

        void result(T result);

    }
}
