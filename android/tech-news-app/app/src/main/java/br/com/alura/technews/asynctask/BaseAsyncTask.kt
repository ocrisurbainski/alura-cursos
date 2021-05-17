package br.com.alura.technews.asynctask

import android.os.AsyncTask

class BaseAsyncTask<T>(
    private val quandoExecuta: () -> T,
    private val quandoFinaliza: ((resultado: T) -> Unit)? = null
) : AsyncTask<Void, Void, T>() {

    override fun doInBackground(vararg params: Void?) = quandoExecuta()

    override fun onPostExecute(resultado: T) {
        super.onPostExecute(resultado)

        if (quandoFinaliza != null) {
            quandoFinaliza?.invoke(resultado)
        }
    }

}