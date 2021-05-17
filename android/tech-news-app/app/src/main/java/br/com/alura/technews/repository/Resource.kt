package br.com.alura.technews.repository

/**
 * @author Cristian Urbainski
 * @since 1.0 (27/09/20)
 */
class Resource<T>(
    val dado: T?,
    var erro: String? = null
)