package kr.co.kimd.financeappstore.vo

import kr.co.kimd.financeappstore.vo.Status.SUCCESS
import kr.co.kimd.financeappstore.vo.Status.ERROR
import kr.co.kimd.financeappstore.vo.Status.LOADING

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}